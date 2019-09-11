package test.java.rabbitmq.springboot;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "test")
public class Consumer {
    
    @RabbitHandler
    public void consume(String msg) {
        System.out.println("received message: " + new String(msg));
    }
}