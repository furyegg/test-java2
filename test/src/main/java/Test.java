import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Test {
    
    public static void main(String[] args) throws InterruptedException, IOException {
        FileInputStream in = new FileInputStream("file:/Users/lyh/rtlog/working/d19de4ccb6fc4555b66c51392fe980af");
        System.out.println(in.available());
        in.close();
    }
    
}