package compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Test {
	
	public static void main(String[] args) throws IOException {
		String s = "c:/a/b/c/c.txt";
		File file = createFile(s);
		
	}
	
	public static File createFile(String destination) throws IOException {
		if (StringUtils.isBlank(destination)) {
			return null;
		}

		File destFile = new File(destination);
		
		int lastSeparatorIndex = destFile.getCanonicalPath().lastIndexOf(File.separator);
		String path = destination.substring(0, lastSeparatorIndex);
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			FileUtils.forceMkdir(pathFile);
		}

		return new File(destination);
	}

	public static void main2(String[] args) throws Exception {
		FileInputStream fin = new FileInputStream("c:/xbrl/ecr.xbrl");

		FileOutputStream fout = new FileOutputStream("c:/xbrl/ecr.gz");
		GZIPOutputStream zout = new GZIPOutputStream(fout);

		byte[] buffer = new byte[1024];
		int len;
		while ((len = fin.read(buffer)) != -1) {
			zout.write(buffer, 0, len);
		}
		
		zout.close();
		fout.close();
		fin.close();
	}

}