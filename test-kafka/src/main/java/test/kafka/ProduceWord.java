package test.kafka;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.vavr.collection.Stream;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import test.kafka.model.Line;

import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ProduceWord {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);
    
    public static void main(String[] args) {
        KafkaProducer<String, String> producer = createProducer();
        String topic = "wordCount";
        
        Future<Object> f1 = EXECUTOR.submit(() -> sendLine(producer, topic, 1000));
        Future<Object> f2 = EXECUTOR.submit(() -> sendLine(producer, topic, 2000));
        Future<Object> f3 = EXECUTOR.submit(() -> sendLine(producer, topic, 3000));
        
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        f1.cancel(true);
        f2.cancel(true);
        f3.cancel(true);
        EXECUTOR.shutdownNow();
    }
    
    private static Object sendLine(KafkaProducer<String, String> producer, String topic, int sleepMilli) {
        while (true) {
            String line = createLine();
            try {
                Thread.sleep(sleepMilli);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
            System.out.println(String.format("sending data: %s, sleep: %d ", line, sleepMilli));
            sendData(producer, topic, line);
        }
    }
    
    private static String createLine() {
        String chars = Stream.rangeClosed('a', 'g')
                .map(c -> String.valueOf(c))
                .toJavaList()
                .stream()
                .collect(Collectors.joining());
        List<String> randomChars = Lists.newArrayList();
        for (char c : RandomStringUtils.random(3, chars.toCharArray()).toCharArray()) {
            randomChars.add(String.valueOf(c));
        }
        String content = StreamEx.of(randomChars).joining(" ");
    
        long now = Instant.now().toEpochMilli();
    
        Line line = Line.builder()
                .timestamp(now)
                .content(content)
                .build();
        return JSON.toJSONString(line);
    }
    
    private static KafkaProducer<String, String> createProducer() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "zad-test-cluster-data2:9092,zad-test-cluster-data5:9092,zad-test-cluster-data6:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        return new KafkaProducer<>(kafkaProps);
    }
    
    private static void sendData(KafkaProducer<String, String> producer, String topic, String data) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
        producer.send(record);
    }
}