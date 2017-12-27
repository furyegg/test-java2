package test.hbase;

import com.google.protobuf.ServiceException;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.compress.Compression;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

public class SaveData {
	private static final String TABLE_NAME = "MV_DSFR20_DSP1_RUIXIN";
	private static final String CF_DEFAULT = "0";

	public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
		if (admin.tableExists(table.getTableName())) {
			admin.disableTable(table.getTableName());
			admin.deleteTable(table.getTableName());
		}
		admin.createTable(table);
	}

	public static void createSchemaTables(Configuration config) throws IOException {
		try (Connection connection = ConnectionFactory.createConnection(config);
			 Admin admin = connection.getAdmin()) {

			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
			table.addFamily(new HColumnDescriptor(CF_DEFAULT).setCompressionType(Compression.Algorithm.NONE));

			System.out.print("Creating table. ");
			createOrOverwrite(admin, table);
			System.out.println(" Done.");
		}
	}

	public static void modifySchema(Configuration config) throws IOException {
		try (Connection connection = ConnectionFactory.createConnection(config);
			 Admin admin = connection.getAdmin()) {

			TableName tableName = TableName.valueOf(TABLE_NAME);
			if (!admin.tableExists(tableName)) {
				System.out.println("Table does not exist.");
				System.exit(-1);
			}

			HTableDescriptor table = admin.getTableDescriptor(tableName);

			// Update existing table
			HColumnDescriptor newColumn = new HColumnDescriptor("NEWCF");
			newColumn.setCompactionCompressionType(Compression.Algorithm.GZ);
			newColumn.setMaxVersions(HConstants.ALL_VERSIONS);
			admin.addColumn(tableName, newColumn);

			// Update existing column family
			HColumnDescriptor existingColumn = new HColumnDescriptor(CF_DEFAULT);
			existingColumn.setCompactionCompressionType(Compression.Algorithm.GZ);
			existingColumn.setMaxVersions(HConstants.ALL_VERSIONS);
			table.modifyFamily(existingColumn);
			admin.modifyTable(tableName, table);

			// Disable an existing table
			admin.disableTable(tableName);

			// Delete an existing column family
			admin.deleteColumn(tableName, CF_DEFAULT.getBytes("UTF-8"));

			// Delete a table (Need to be disabled first)
			admin.deleteTable(tableName);
		}
	}

	public static void main(String... args) throws Exception {
		SaveData app = new SaveData();
		app.run();
	}

	private void run() throws IOException, ServiceException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "ignis-VirtualBox");
//		conf.set("hbase.zookeeper.quorum", "node-a.lombardrisk.com");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("zookeeper.znode.parent", "/hbase");

		HBaseAdmin.checkHBaseAvailable(conf);

		HTable table = new HTable(conf, TABLE_NAME);
		
		HColumnDescriptor columnDescriptor = table.getTableDescriptor().getColumnFamilies()[0];
		byte[] cf = columnDescriptor.getName();
		System.out.println("cf: " + new String(cf));

		StopWatch stopWatch = StopWatch.createStarted();

		Scanner scanner = new Scanner(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("MV_DSFR20_DSP1_RUIXIN_0002_2017-01-01_100K.csv")));
		boolean isHead = true;
		List<String> head = null;
		int seq = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			CsvLineParser parser = new CsvLineParser(line);
			List<String> fields = parser.parse();

			if (isHead) {
				head = fields;
				isHead = false;
				continue;
			}

			++seq;
			Put put = new Put(toBytes(seq));
			for (int i = 0; i < fields.size(); ++i) {
				String value = fields.get(i);
				if (value != null) {
					put.addColumn(cf, toBytes(head.get(i)), toBytes(value));
				}
			}
			table.put(put);
		}
		table.flushCommits();
		table.close();

		stopWatch.stop();
		System.out.println("total lines: " + seq);
		System.out.println("cost: " + stopWatch.getTime(TimeUnit.SECONDS));

		scanner.close();
	}
}