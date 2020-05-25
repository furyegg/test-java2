package compress;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.RandomStringUtils;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

public class SnappyTest {
    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch1 = Stopwatch.createStarted();
        SnappyOutputStream out1 = new SnappyOutputStream(new BufferedOutputStream(new FileOutputStream("d:/tmp/out.snappy")));
        output(out1);
        System.out.println("cost: " + stopwatch1.elapsed(TimeUnit.MILLISECONDS));
        
        Stopwatch stopwatch2 = Stopwatch.createStarted();
        GZIPOutputStream out2 = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream("d:/tmp/out.gz")));
        output(out2);
        System.out.println("cost: " + stopwatch2.elapsed(TimeUnit.MILLISECONDS));
        
//        uncompressSnappy("d:/tmp/out.snappy");
    }
    
    private static void uncompressSnappy(String file) throws IOException {
        SnappyInputStream in = new SnappyInputStream(new FileInputStream(file));
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
             System.out.println(scanner.nextLine());
        }
        in.close();
    }
    
    private static void output(OutputStream out) throws IOException {
        for (int i = 0; i < 10000; ++i) {
            String content = RandomStringUtils.randomAscii(10000);
//            System.out.println(content);
            out.write(content.getBytes());
            out.write("\n".getBytes());
        }
        out.close();
    }
}