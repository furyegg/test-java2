package test.flink.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RtLog {
    private long requestTime;
    private String domain;
    private String cdnProvider;
}