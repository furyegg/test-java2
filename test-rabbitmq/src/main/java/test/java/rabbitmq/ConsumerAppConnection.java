package test.java.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import io.vavr.Function0;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

@Slf4j
public class ConsumerAppConnection {
    
    @Getter
    private static class MsgConsumer {
        private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
        private ExecutorService es;
        private Future<?> consumerFuture;
        
        private ConnectionFactory factory;
        private Connection connection;
        private Channel channel;
        private String consumerTag;
        private Function0<?> restartListener;
    
        public MsgConsumer(ConnectionFactory factory, ExecutorService es, Function0<?> restartListener) {
            this.factory = factory;
            this.es = es;
            this.restartListener = restartListener;
        }
        
        public void start() {
            Runnable action = () -> {
                try {
                    connection = factory.newConnection();
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
                                stop();
                                restartListener.apply();
                            } else {
                                if (channel.isOpen()) {
                                    channel.basicAck(envelope.getDeliveryTag(), false);
                                }
                            }
                        }
                    };
        
                    consumerTag = channel.basicConsume("test", false, consumer);
                    log.info("started consumer: " + consumerTag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
    
            consumerFuture = es.submit(action);
        }
        
        public void stop() {
            try {
                log.info("canceling consumer: " + consumerTag);
                if (channel.isOpen()) {
                    channel.basicCancel(consumerTag);
                    channel.close();
                }
                if (connection.isOpen()) {
                    connection.close();
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
        
        ExecutorService es = Executors.newFixedThreadPool(4);
        
        Consumer<MsgConsumer> restartListener = consumer -> {
            consumer.stop();
            
        };
        
        createConsumer(factory, es);
    }
    
    private static void createConsumer(ConnectionFactory factory, ExecutorService es) {
        MsgConsumer msgConsumer = new MsgConsumer(factory, es, createRestartListener(factory, es));
        msgConsumer.start();
    }
    
    private static Function0<?> createRestartListener(ConnectionFactory factory, ExecutorService es) {
        return () -> {
            createConsumer(factory, es);
            return null;
        };
    }
    
}