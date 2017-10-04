package test.controller;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Initializer2 {
	@EventListener
	public void contextStarted(ContextStartedEvent e) {
		System.out.println("ApplicationStartingEvent .......................");
	}
}
