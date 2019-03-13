package test.springboot.jar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import test.springboot.jar.config.TaobaoMessageConfig;

@SpringBootApplication
public class Application implements CommandLineRunner {
    
    @Autowired
    private TaobaoMessageConfig config;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println(config.getTopics());
        System.out.println(config.getTopicMap().size());
    }
}