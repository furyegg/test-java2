package test.java.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class ConsumerAppSimple {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare("test", true, false, false, null);
    
        consume(channel);
//        pull(channel);
    }
    
    private static void pull(Channel channel) throws IOException {
        while (true) {
            GetResponse response = channel.basicGet("test", false);
            String message = new String(response.getBody());
            System.out.println("pulled message: " + message);
        }
    }
    
    private static void consume(Channel channel) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        
        es.submit(() -> {
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("received message: " + message + ", consumer tag: " + consumerTag);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
    
            try {
                channel.basicConsume("test", false, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}