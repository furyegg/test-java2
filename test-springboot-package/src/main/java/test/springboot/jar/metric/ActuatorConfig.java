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
    
    private FileInfo fileCount = FileInfo.empty();
    private FileInfo fileSize = FileInfo.empty();
    
    @PostConstruct
    private void init() {
        log.info("ActuatorConfig post construct");
        fileSize = meterRegistry.gauge("file.count", fileCount, f -> f.getCount());
        fileSize = meterRegistry.gauge("file.size", fileSize, f -> f.getSize());
    }
    
    @Scheduled(fixedRate = 3000)
    private void update() {
        FileInfo info = LocalFileStatistic.getFileInfo("/Users/lyh/Downloads/logs_kodo");
//        fileCount.refresh(info);
        fileSize.refresh(info);
        System.out.println("refreshed file info");
    }
}