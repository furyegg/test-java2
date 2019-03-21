package test.kafka;

import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MergeTextFile {
    public static void main(String[] args) throws Exception {
        List<String> sources = Lists.newArrayList(
                "src/main/resources/message-processor.2019-03-11.3.log",
                "src/main/resources/message-processor.2019-03-17.0.log",
                "src/main/resources/message-processor.2019-03-17.1.log"
        );
        String target = "src/main/resources/message-processor.2019-03.log";
        merge(sources, target);
    }
    
    private static void merge(List<String> sources, String target) throws Exception {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(target));
        
        StreamEx.of(sources).forEach(src -> {
            try {
                InputStream in = new BufferedInputStream(new FileInputStream(src));
                byte[] buffer = new byte[10240];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        out.flush();
        out.close();
    }
}