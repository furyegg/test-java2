package test.kafka;

import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Properties;

public class ConsumerTest {
    public static void main(String[] args) {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("group.id", "nums1");
        kafkaProps.put("auto.offset.reset", "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProps);
    
        consumer.subscribe(Lists.newArrayList("nums1"));
        
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            System.out.println("polled: " + records.count());
            StreamEx.of(records.iterator()).forEach(r -> System.out.println(r.value()));
    
            TopicPartition tp = new TopicPartition("nums1", 0);
            consumer.seek(tp, 100);
        }
    }
}