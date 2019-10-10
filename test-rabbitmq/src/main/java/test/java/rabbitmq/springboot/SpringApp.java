package test.java.rabbitmq.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringApp implements CommandLineRunner {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class);
    }
    
    @Autowired
    private Consumer consumer;
    
    @Override
    public void run(String... args) throws Exception {
    
    }
}