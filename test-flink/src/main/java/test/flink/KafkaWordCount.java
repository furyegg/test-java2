package test.flink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import test.flink.model.Line;
import test.flink.schema.LineSchema;

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
        properties.setProperty("bootstrap.servers", "zad-test-cluster-data2:9092,zad-test-cluster-data5:9092,zad-test-cluster-data6:9092");
//        properties.setProperty("zookeeper.connect", "zad-test-cluster-data2:2181,zad-test-cluster-data5:2181,zad-test-cluster-data6:2181/kafka");
        properties.setProperty("group.id", "test");
        
        FlinkKafkaConsumerBase<Line> source = new FlinkKafkaConsumer011<>("wordCount", new LineSchema(), properties)
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Line>() {
                    @Override
                    public long extractAscendingTimestamp(Line line) {
                        return line.getTimestamp();
                    }
                });
        
        DataStream<Line> line = env.addSource(source);
        
        DataStream<Tuple2<String, Integer>> counts =
                // split up the lines in pairs (2-tuples) containing: (word,1)
                line.flatMap(new LineTokenizer())
                        // group by the tuple field "0" and sum up tuple field "1"
                        .keyBy(0)
                        .timeWindow(Time.milliseconds(2500))
                        .allowedLateness(Time.milliseconds(1500))
                        .sum(1);
        
        counts.print();
        
        // execute program
        env.execute("Kafka WordCount");
    }
    
}