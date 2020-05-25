package test.java.eventbus;

import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    public static void main(String[] args) {
        List<Runner> runners = Stream.range(0, 3)
                .map(n -> new Runner())
                .toJavaList();
    
        runners.forEach(r -> r.start());
        
        executor.scheduleAtFixedRate(
                () -> {
                    int rnd = RandomUtils.nextInt(0, 3);
                    Runner runner = runners.get(rnd);
                    runner.getEventBus().post(new Event(rnd));
                    log.info("sent event: {}", rnd);
                },
                0,
                1,
                TimeUnit.SECONDS
        );
    }
}