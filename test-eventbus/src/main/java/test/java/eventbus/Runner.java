package test.java.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Slf4j
public class Runner extends Thread {
    private static final AtomicInteger id = new AtomicInteger(0);
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);
    
    private EventBus eventBus;
    private int runnerId;
    
    public Runner() {
        runnerId = id.incrementAndGet();
        setName("runner" + runnerId);
    }
    
    @Override
    public void run() {
        if (eventBus == null) {
            eventBus = new EventBus();
            eventBus.register(this);
        }
        
        executor.scheduleAtFixedRate(
                () -> log.info(runnerId + " is running"),
                0,
                3,
                TimeUnit.SECONDS
        );
    }
    
    @Subscribe
    private void subscribe(Event e) {
        log.info("runner: {}, received event: {}", runnerId, e.getValue());
    }
}