package test.springboot.jar.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ActuatorConfig {
    @Autowired
    private MeterRegistry meterRegistry;
    
    private Counter counter;
    private FileInfo fileInfo = new FileInfo();
    
    @PostConstruct
    private void init() {
        log.info("ActuatorConfig post construct");
        counter = meterRegistry.counter("schedule.count", "type", "count");
        fileInfo = meterRegistry.gauge("schedule.time", fileInfo, f -> f.getSize().doubleValue());
    }
    
    @Scheduled(fixedRate = 3000)
    private void update() {
        log.info("scheduled...");
        counter.increment();
        fileInfo.setSize(RandomUtils.nextLong(100, 1000));
    }
}