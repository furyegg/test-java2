package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {

	public static void main(String[] args) throws Exception {
		removeEmptyLine("d:/xbrl/ocelot-xsd.txt", "d:/xbrl/ocelot-xsd-target.txt");
	}
	
	public static void removeEmptyLine(String srcFile, String tarFile) throws Exception {
		FileInputStream fis = new FileInputStream(srcFile);
		PrintWriter writer = new PrintWriter(tarFile);
		
		Scanner scanner = new Scanner(fis);
		String line = "";
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (!StringUtils.isBlank(line)) {
				writer.write(line);
				writer.write("\n");
			}
		}
		
		scanner.close();
		fis.close();
		writer.close();
	}
	
}