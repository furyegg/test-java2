package test.java.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@Slf4j
public class ConsumerAppTag {
    
    private static class Consumer {
        private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        private Channel channel;
        private ExecutorService es;
        private String consumerTag;
    
        public Consumer(Channel channel, ExecutorService es) {
            this.channel = channel;
            this.es = es;
        }
    
        public void start() {
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    log.info("received message: " + message + ", consumer tag: " + consumerTag);
    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
    
                    if (needRestart(message)) {
                        stop();
                        start();
                    } else {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
    
            try {
                consumerTag = channel.basicConsume("test", false, consumer);
                log.info("started consumer: " + consumerTag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void stop() {
            try {
                log.info("canceling consumer: " + consumerTag);
                channel.basicCancel(consumerTag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        private boolean needRestart(String message) {
            LocalDateTime time = LocalDateTime.parse(message, formatter);
            if (time.getSecond() % 10 == RandomUtils.nextInt(1, 4)) {
                log.info("restart trigger message: " + message);
                return true;
            } else {
                return false;
            }
        }
    }
    
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(200);
        
        channel.queueDeclarePassive("test");
    
        ExecutorService es = Executors.newFixedThreadPool(4);
        
        es.submit(() -> {
            Consumer consumer = new Consumer(channel, es);
            consumer.start();
        });
        
        // connection.close();
    }
}