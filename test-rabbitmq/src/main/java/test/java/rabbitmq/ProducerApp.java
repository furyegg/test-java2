package test.java.rabbitmq;

import com.rabbitmq.client.AMQP;
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
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("defy");
        factory.setPassword("defy#123");
    
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(200);
    
        channel.queueDeclare("test", true, false, false, null);
    
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