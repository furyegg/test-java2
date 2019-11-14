package test.flink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import test.flink.model.RtLog;
import test.flink.schema.RtLogSchema;

import java.util.Properties;

public class KafkaWordCount {
    public static void main(String[] args) throws Exception {
        
        // Checking input parameters
        final ParameterTool params = ParameterTool.fromArgs(args);
        
        // set up the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        
        // get input data
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "jjh645:19092");
        properties.setProperty("group.id", "rtlog-flink-test");
        
        FlinkKafkaConsumerBase<RtLog> source = new FlinkKafkaConsumer<>("fusion-rtlog-std-prod-new", new RtLogSchema(), properties)
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<RtLog>() {
                    @Override
                    public long extractAscendingTimestamp(RtLog log) {
                        return log.getRequestTime();
                    }
                });
        
        DataStream<RtLog> line = env.addSource(source);
        
        DataStream<Tuple2<String, Integer>> counts =
                // split up the lines in pairs (2-tuples) containing: (word,1)
                line.flatMap(new RtLogTokenizer())
                        // group by the tuple field "0" and sum up tuple field "1"
                        .keyBy(0)
                        .timeWindow(Time.milliseconds(5000))
                        .allowedLateness(Time.milliseconds(1500))
                        .sum(1);
        
        counts.print();
        
        // execute program
        env.execute("Kafka WordCount");
    }
    
}