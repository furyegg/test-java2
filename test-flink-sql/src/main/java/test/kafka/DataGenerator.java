package test.kafka;

import org.apache.commons.compress.utils.Lists;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class DataGenerator {
    public static List<String> load(String fileName) throws FileNotFoundException {
        String file = "src/main/resources/" + fileName;
        List<String> lines = Lists.newArrayList();
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
        }
        return lines;
    }
    
}
