package test.controller;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationRunner {
	public void run(ApplicationArguments applicationArguments) throws Exception {
		System.out.println("ApplicationArguments .......................");
	}
}
