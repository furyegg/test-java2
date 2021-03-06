import one.util.streamex.StreamEx;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Test {
    
    public static void main(String[] args) throws InterruptedException, IOException {
        double src = 100000;
    
        System.out.println("year: " + (src + src * 0.0375));
    
        System.out.println("month: " + BigDecimal.valueOf(calc(src, 0.036, 1)).toPlainString());
    }
    
    private static double calc(double src, double r, int month) {
        if (month > 6) {
            return src;
        }
        return calc(src + src * r / 6, r, month + 1);
    }
    
}