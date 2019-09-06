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
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@Slf4j
public class ConsumerAppChannel2 {
    
    private static class Consumer {
        private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        private ExecutorService es;
        private Future<?> consumerFuture;
        
        private Connection connection;
        private Channel channel;
        private String consumerTag;
        
        public Consumer(Connection connection, ExecutorService es) {
            this.connection = connection;
            this.es = es;
        }
        
        public void start() {
            Runnable action = () -> {
                try {
                    channel = connection.createChannel(200);
                    
                    DefaultConsumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                                throws IOException {
                            String message = new String(body);
                            log.info("received message: " + message + ", consumer tag: " + consumerTag);
                            
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            
                            if (needRestart(message)) {
                                stop(getChannel(), consumerTag);
                                start();
                            } else {
                                if (getChannel().isOpen()) {
                                    getChannel().basicAck(envelope.getDeliveryTag(), false);
                                }
                            }
                        }
                    };
                    
                    consumerTag = channel.basicConsume("test", false, consumer);
                    log.info("started consumer: " + consumerTag);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            
            consumerFuture = es.submit(action);
        }
        
        public void stop(Channel channel, String consumerTag) {
            try {
                if (channel.isOpen()) {
                    log.info("canceling consumer: " + consumerTag);
                    channel.basicCancel(consumerTag);
                    channel.close();
                }
                if (!consumerFuture.isCancelled()) {
                    consumerFuture.cancel(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private boolean needRestart(String message) {
            LocalDateTime time = LocalDateTime.parse(message, formatter);
            if (time.getSecond() % 10 == RandomUtils.nextInt(0, 10)) {
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
        ExecutorService es = Executors.newFixedThreadPool(16);
        
        Consumer consumer = new Consumer(connection, es);
        consumer.start();
        
        // connection.close();
    }
}