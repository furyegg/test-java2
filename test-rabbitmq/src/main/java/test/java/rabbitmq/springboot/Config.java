package test.java.rabbitmq.springboot;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Queue helloQueue() {
        return new Queue("test");
    }
}