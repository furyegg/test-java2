package test.flink.model;

import lombok.Data;

@Data
public class Line {
    private long timestamp;
    private String content;
}