package logfile;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RemoveTime {

	private static String PATH = "d:/tava/";
	private static String LOG_FILE1 = "rac_failed.log";
	private static String LOG_FILE2 = "server.log";

	public static void main(String[] args) throws Exception {
		String logFile1 = PATH + LOG_FILE1;
		String logFile2 = PATH + LOG_FILE2;

		String newLog1 = PATH + "new_" + LOG_FILE1;
		String newLog2 = PATH + "new_" + LOG_FILE2;

		removeTime(logFile1, newLog1);
		removeTime(logFile2, newLog2);
	}

	public static void removeTime(String logFile, String outFile) throws Exception {
		Scanner scan = new Scanner(new File(logFile));
		FileWriter out = new FileWriter(outFile);

		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			// System.out.println(logFile + ": " + line);
			int startIndex = line.indexOf("[");
			if (Pattern.matches("^\\d{2}:\\d{2}:\\d{2},\\d{3}.*", line)) {
				line = line.substring(startIndex, line.length());
			}
			out.write(line + "\n");
		}

		out.close();
	}

}
