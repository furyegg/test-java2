package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Del1 {

	public static void main(String[] args) throws IOException {
		File file = new File("C:/xbrl/test.txt");

		// OutputStream out = new FileOutputStream(file);

		if (file.exists()) {
			System.out.println(file.delete());
		}
	}

}