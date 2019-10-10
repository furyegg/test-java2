package test.hadoop.test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ProcessSampleSrc {
	@Test
	@Ignore
	public void generateSample() throws FileNotFoundException {
		InputStream in = new FileInputStream("src/test/resources/sample-src.txt");
		Scanner scanner = new Scanner(in);
		List<String> target = Lists.newArrayList();
		List<String> newLine = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.length() == 4) {
				if (newLine != null) {
					target.add(Joiner.on(",").join(newLine));
				}
				newLine = Lists.newArrayList();
			}
			newLine.add(line);
		}
		target.add(Joiner.on(",").join(newLine));
		
		PrintWriter out = new PrintWriter(new FileOutputStream("src/main/resources/sample.txt"));
		for (String line : target) {
			out.println(line);
		}
		out.close();
	}
}