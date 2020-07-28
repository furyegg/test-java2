package test.springboot.jar.metric;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FileInfo {
    private int count;
    private long size;
    private double sizeInG;
    
    public FileInfo(int count, long size) {
        this.count = count;
        this.size = size;
        sizeInG = BigDecimal.valueOf(size * 1.0 / 1024 / 1024 / 1024)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
    
    public static FileInfo empty() {
        return new FileInfo(0, 0);
    }
    
    public static FileInfo ofSize(long size) {
        return new FileInfo(1, size);
    }
    
    public FileInfo add(FileInfo other) {
        return new FileInfo(count + other.count, size + other.size);
    }
    
    public void refresh(FileInfo fileInfo) {
        count = fileInfo.count;
        size = fileInfo.size;
        sizeInG = fileInfo.sizeInG;
    }
}