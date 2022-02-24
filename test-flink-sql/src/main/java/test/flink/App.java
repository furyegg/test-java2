package test.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;

@Slf4j
public class App {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        
        tableEnv.executeSql("create table src(domain STRING, role STRING, ext_fields ARRAY<STRING>)" +
                " with ('connector'='kafka','topic'='forward-charge-jsonx','properties.bootstrap.servers'='localhost:9092'," +
                "'properties.group.id'='testGroup','format'='jsonx_ext','scan.startup.mode'='earliest-offset')");
        Table table = tableEnv.sqlQuery("select domain, role, ext_fields[1] as ext1, ext_fields[2] as ext2 from src");
        TableResult result = table.execute();
        CloseableIterator<Row> itr = result.collect();
        int count = 0;
        while (itr.hasNext()) {
            Row row = itr.next();
            Object domain = row.getField("domain");
            Object role = row.getField("role");
            Object ext1 = row.getField("ext1");
            Object ext2 = row.getField("ext2");
            log.info("domain: {}, role: {}, ext1: {}, ext2: {}", domain, role, ext1, ext2);
            ++count;
        }
        
        System.out.println("records count: " + count);
        
        env.execute();
    }
}