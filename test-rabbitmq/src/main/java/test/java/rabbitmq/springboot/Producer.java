package test.java.rabbitmq.springboot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Scheduled(fixedRate = 1000)
    private void send() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String message = formatter.format(LocalDateTime.now());
        rabbitTemplate.convertAndSend("test", message);
    }
}