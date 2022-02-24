package test.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

//@Slf4j
public class KafkaDataConsumer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "forward-charge-json4");
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);
        
        List<String> topics = Arrays.asList("forward-charge-jsonx");
        consumer.subscribe(topics);
        
        int count = 0;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(500);
//            if (records.isEmpty()) {
//                break;
//            }
            for (ConsumerRecord<String, String> record : records) {
                ++count;
                System.out.println("consumed: " + record.value());
            }
        }
//        System.out.println("consumed records count: " + count);
    }
}
