package test.java.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

public class ProducerApp {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("cs22:5672,cs50:5672,cs59:5672");
//        factory.setPort(5672);
        factory.setUsername("defy");
        factory.setPassword("defy#123");
    
        Address[] addresses = new Address[]{
                new Address("cs59", 5672),
                new Address("cs22", 5672),
                new Address("cs50", 5672)
        };
    
        Connection connection = factory.newConnection(addresses, "time producer");
        Channel channel = connection.createChannel();
    
        channel.queueDeclare("test3", true, false, false, null);
    
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .build();
    
        while (true) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String message = formatter.format(LocalDateTime.now());
            System.out.println("sending message: " + message);
            channel.basicPublish("", "test", properties, message.getBytes());
            Thread.sleep(1000);
        }
//        System.out.println("closing...");
//        connection.close();
    }
}