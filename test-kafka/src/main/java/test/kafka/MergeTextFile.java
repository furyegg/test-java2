package test.kafka;

import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class MergeTextFile {
    public static void main(String[] args) throws Exception {
        List<String> sources = Lists.newArrayList(
                "src/main/resources/message-processor.2019-03-11/message-processor.2019-03-11.0.log",
                "src/main/resources/message-processor.2019-03-11/message-processor.2019-03-11.1.log"
        );
        String target = "src/main/resources/message-processor.2019-03-11.3.log";
        merge(sources, target);
    }
    
    private static void merge(List<String> sources, String target) throws Exception {
        PrintWriter targetOut = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)));
        
        StreamEx.of(sources).forEach(src -> {
            try {
                Scanner srcIn = new Scanner(new BufferedInputStream(new FileInputStream(src)));
                while (srcIn.hasNextLine()) {
                    targetOut.write(srcIn.nextLine());
                }
                srcIn.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    
        targetOut.flush();
        targetOut.close();
    }
}