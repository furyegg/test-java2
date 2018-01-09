package test.hbase;

import com.google.common.collect.Lists;
import com.sematext.hbase.wd.AbstractRowKeyDistributor;
import com.sematext.hbase.wd.DistributedScanner;
import com.sematext.hbase.wd.RowKeyDistributorByOneBytePrefix;
import lombok.AllArgsConstructor;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.SerializationUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@PropertySource("file:./config.properties")
public class PerformanceTest implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PerformanceTest.class);
		app.run(args);
	}

	private static final String DATASET_NAME = "IGNIS_DATASET";
	private static final String CF = "0";
	private static final int MAX_SEQ = 100;
	private static final byte REGIONS_SIZE = 3;
	private static final int KEY_BYTE_SIZE = Long.BYTES / Byte.BYTES + 1;

	private ExecutorService executor;

	@Value("file:./${dataset.file}")
	private Resource tablesFile;

	@Value("${dataset.columns}")
	private String columnString;

	@Value("${commit.batch.size}")
	private int batchSize;

	@Value("${task.parallel}")
	private int taskParallel;

	@Value("${client.write.buffer}")
	private int writeBuffer;

	@Value("${write.cache.blooms}")
	private boolean cacheBloomsOnWrite;

	@Value("${write.cache.data}")
	private boolean cacheDataOnWrite;

	@Value("${write.cache.indexes}")
	private boolean cacheIndexesOnWrite;

	@Value("${cf.compress}")
	private String cfCompress;

	@Value("${data.block.encoding}")
	private String dataBlockEncoding;

	@Value("${scan.rows}")
	private int scanRows;

	@Value("${read.columns}")
	private int readColumns;

	private AbstractRowKeyDistributor keyDistributor;

	private String[] columns;
	private byte[][] columnsBytes;

	@PostConstruct
	private void init() {
		executor = Executors.newWorkStealingPool(taskParallel);

		keyDistributor = new RowKeyDistributorByOneBytePrefix(REGIONS_SIZE);

		columns = columnString.split(",");
		columnsBytes = StreamEx.of(Arrays.asList(columns)).map(c -> Bytes.toBytes(c)).toArray(new byte[][]{});
	}

	@Override
	public void run(String... args) throws Exception {
		boolean recreateTable = false;
		boolean readData = false;
		int readDataHead = 0;
		if (args.length == 1) {
			recreateTable = args[0].equalsIgnoreCase("true");
		}
		if (args.length == 2) {
			String arg2 = args[1];
			readData = arg2.startsWith("read:");
			readDataHead = Integer.valueOf(arg2.substring(arg2.lastIndexOf(":") + 1, arg2.length()));
		}

		Configuration conf = createConfig();

		if (recreateTable) {
			recreateTable(conf);
		}

		if (readData) {
			System.out.println(" >>> reading data");
			StopWatch stopWatch = StopWatch.createStarted();
			read(conf, readDataHead);
			System.out.println(" >>> reading data cost: " + stopWatch.getTime(TimeUnit.SECONDS));
		} else {
			System.out.println(" >>> writing data");
			write(conf);
		}
	}

	private void read(Configuration conf, int head) throws IOException, NoSuchAlgorithmException {
		long start = head << 32;
		long end = start | 0xFFFFFFFF;
		System.out.println(" >>> query data between: " + start + " -- " + end);
		Scan scan = new Scan(Bytes.toBytes(start), Bytes.toBytes(end));
		scan.setCaching(scanRows);

		try (HConnection connection = (HConnection) ConnectionFactory.createConnection(conf);
			 HTableInterface table = connection.getTable(DATASET_NAME);
			 ResultScanner scanner = DistributedScanner.create(table, scan, keyDistributor)) {

			byte[] cf = Bytes.toBytes(CF);
			MessageDigest md = MessageDigest.getInstance("MD5");
			boolean printOneLine = true;
			StringBuffer line = new StringBuffer();
			Integer[] columnsIndexes = getColumnIndexes();
			int count = 0;
			for (Result rs : scanner) {
				for (int index : columnsIndexes) {
					byte[] column = columnsBytes[index];
					byte[] value = rs.getValue(cf, column);
					if (printOneLine) {
						line.append(Bytes.toString(column))
								.append(" = (")
								.append(Bytes.toString(value))
								.append(")")
								.append("\n");
					}
					md.update(value);
				}
				printOneLine = false;
				++count;
			}
			System.out.println(" >>> first line:");
			System.out.print(line);
			System.out.println(" >>> values MD5 digest: " + Bytes.toHex(md.digest()));
			System.out.println(" >>> retrieved rows: " + count);
		}
	}

	private Integer[] getColumnIndexes() {
		Random rnd = new Random();
		List<Integer> cols = Stream.generate(() -> 0).limit(readColumns).collect(Collectors.toList());
		return StreamEx.of(cols)
				.map(i -> rnd.nextInt(readColumns))
				.toArray(Integer.class);
	}

	private void write(Configuration conf) throws IOException {
		Stream<String> fileStream = Files.lines(tablesFile.getFile().toPath());
		List<String> lines = fileStream.collect(Collectors.toList());

		EntryStream<Integer, String> lineStream = EntryStream.of(lines);
		TableName tableName = TableName.valueOf(DATASET_NAME);

		Random rnd = new Random();
		long keyHead = (long) rnd.nextInt(MAX_SEQ);
		System.out.println(String.format(" >>> key head: %d", keyHead));

		long keyStart = keyHead << 32;

		try (Connection hconn = ConnectionFactory.createConnection(conf);
			 Table table = hconn.getTable(tableName)) {
			HColumnDescriptor columnDescriptor = table.getTableDescriptor().getColumnFamilies()[0];
			byte[] cf = columnDescriptor.getName();
			insertRowsPar(lineStream, keyHead, keyStart, table, cf);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private Configuration createConfig() {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "node-b.lombardrisk.com");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("zookeeper.znode.parent", "/hbase");
		conf.set("hbase.client.write.buffer", String.valueOf(writeBuffer * 1014 * 1024));
		return conf;
	}

	private void recreateTable(Configuration conf) throws IOException {
		try (Connection connection = ConnectionFactory.createConnection(conf);
			 Admin admin = connection.getAdmin()) {
			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(DATASET_NAME));

			Compression.Algorithm compress = getCompression();
			DataBlockEncoding dataBlockEncoding = getDataBlockEncoding();

			HColumnDescriptor cf = new HColumnDescriptor(CF)
					.setCacheBloomsOnWrite(cacheBloomsOnWrite)
					.setCacheDataOnWrite(cacheDataOnWrite)
					.setCacheIndexesOnWrite(cacheIndexesOnWrite)
					.setDataBlockEncoding(dataBlockEncoding)
					.setCompressionType(compress);
			table.addFamily(cf);
			createOrOverwrite(admin, table);
		}
	}

	private DataBlockEncoding getDataBlockEncoding() {
		DataBlockEncoding encoding = DataBlockEncoding.NONE;
		if (StringUtils.isNotBlank(dataBlockEncoding)) {
			if (dataBlockEncoding.equalsIgnoreCase(DataBlockEncoding.FAST_DIFF.name())) {
				encoding = DataBlockEncoding.FAST_DIFF;
			} else if (dataBlockEncoding.equalsIgnoreCase(DataBlockEncoding.PREFIX_TREE.name())) {
				encoding = DataBlockEncoding.PREFIX_TREE;
			} else if (dataBlockEncoding.equalsIgnoreCase(DataBlockEncoding.PREFIX.name())) {
				encoding = DataBlockEncoding.PREFIX;
			}
		}
		return encoding;
	}

	private Compression.Algorithm getCompression() {
		Compression.Algorithm compress = Compression.Algorithm.NONE;
		if (StringUtils.isNotBlank(cfCompress)) {
			if (cfCompress.equalsIgnoreCase("GZ")) {
				compress = Compression.Algorithm.GZ;
			} else if (cfCompress.equalsIgnoreCase("LZ4")) {
				compress = Compression.Algorithm.LZ4;
			} else if (cfCompress.equalsIgnoreCase("LZO")) {
				compress = Compression.Algorithm.LZO;
			} else if (cfCompress.equalsIgnoreCase("SNAPPY")) {
				compress = Compression.Algorithm.SNAPPY;
			} else if (cfCompress.equalsIgnoreCase("BZIP2")) {
				compress = Compression.Algorithm.BZIP2;
			} else if (cfCompress.equalsIgnoreCase("ZSTD")) {
				compress = Compression.Algorithm.ZSTD;
			}
		}
		return compress;
	}

	public void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
		if (admin.tableExists(table.getTableName())) {
			System.out.println(" >>> deleting table");
			admin.disableTable(table.getTableName());
			admin.deleteTable(table.getTableName());
		}
		System.out.println(" >>> creating table");
		byte[][] splitKeys = createSplitKeys();
		admin.createTable(table, splitKeys);
	}

	private byte[][] createSplitKeys() {
		final byte[] startKey = Bytes.toBytes(0L);
		int splitKeySize = REGIONS_SIZE - 1;
		byte[][] splitKeys = new byte[splitKeySize][KEY_BYTE_SIZE];
		for (byte i = 1; i <= splitKeySize; ++i) {
			byte[] splitKey = Bytes.add(new byte[]{i}, startKey);
			splitKeys[i - 1] = splitKey;
			System.out.println(" >>> created split key: " + Bytes.toHex(splitKey));
		}
		return splitKeys;
	}

	private void insertRowsPar(EntryStream<Integer, String> lines, long keyHead, Long rowKeyStart, Table table, byte[] cf) throws IOException {
		List<List<Put>> jobs = Lists.newArrayList();
		List<Put> puts = Lists.newArrayListWithCapacity(batchSize);

		StopWatch stopWatch = StopWatch.createStarted();
		for (Map.Entry<Integer, String> e : lines) {
			if (e.getKey() == 0) {
				continue;
			}
			byte[] rowKey = Bytes.toBytes(rowKeyStart | e.getKey());
			Put put = createPut(rowKey, keyHead, e.getValue(), cf);
			puts.add(put);

			if (puts.size() % batchSize == 0) {
				jobs.add(puts);
				puts = Lists.newArrayListWithCapacity(batchSize);
			}
		}
		if (!puts.isEmpty()) {
			jobs.add(puts);
		}
		long time = stopWatch.getTime(TimeUnit.SECONDS);
		System.out.println(" >>> jobs size: " + jobs.size() + ", cost: " + time);

		stopWatch.reset();
		stopWatch.start();
		StreamEx.of(jobs)
				.map(b -> new Action(table, b))
				.map(act -> executor.submit(act))
				.forEach(f -> {
					try {
						f.get();
					} catch (InterruptedException | ExecutionException e) {
						throw new IllegalStateException(e);
					}
				});
		long time2 = stopWatch.getTime(TimeUnit.SECONDS);
		System.out.println(" >>> save to hbase cost: " + time2);
	}

	private Put createPut(byte[] rowKey, long keyHead, String line, byte[] cf) {
		Put put = new Put(keyDistributor.getDistributedKey(rowKey));
		String[] row = line.split(",");
		for (int i = 0; i < columns.length; ++i) {
			String column = columns[i];
			String value = row[i] + " - " + keyHead;
			put.addColumn(cf, Bytes.toBytes(column), SerializationUtils.serialize(value));
		}
		put.setDurability(Durability.ASYNC_WAL);
		return put;
	}

	@AllArgsConstructor
	private class Action implements Runnable {
		private Table table;
		private List<Put> puts;

		@Override
		public void run() {
			try {
				table.put(puts);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}