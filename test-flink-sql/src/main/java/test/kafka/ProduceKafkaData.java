package test.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class ProduceKafkaData {
    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
    
        String topic = "forward-charge-jsonx";
        List<String> lines = DataGenerator.load("forward-charge-jsonx.log");
        System.out.println("loaded records count: " + lines.size());
        for (String line : lines) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, line);
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();
            log.info("sent, topic: {}, partition: {}, offset: {}", metadata.topic(), metadata.partition(), metadata.offset());
        }
        System.out.println("sent records count: " + lines.size());
    }
}
