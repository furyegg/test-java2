package compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;

import util.Timer;

public class GZIPExample {
	
	private static final int BUFFER = 1024;

	public static void main(String[] args) throws Exception {
		String file = "c:/xbrl/ecr_bak.xbrl";
		String gzipFile = "c:/xbrl/ecr.gz";
		String newFile = "c:/xbrl/ecr_new.xbrl";

		Timer t = new Timer();
		t.begin();
		compressGzipFile(file, gzipFile);
		t.end();

		t.begin();
		decompressGzipFile(gzipFile, newFile);
		t.end();

		t.begin();
		FileUtils.copyFile(new File(file), new File("D:/temp/ecr_copy.xbrl"));
		t.end();
	}

	private static void compressGzipFile(String file, String gzipFile) {
		try {
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(gzipFile);
			GZIPOutputStream gzipOS = new GZIPOutputStream(fos);

			byte[] buffer = new byte[BUFFER];
			int len;
			while ((len = fis.read(buffer)) != -1) {
				gzipOS.write(buffer, 0, len);
			}
			// close resources
			gzipOS.close();
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void decompressGzipFile(String gzipFile, String newFile) {
		try {
			FileInputStream fis = new FileInputStream(gzipFile);
			GZIPInputStream gis = new GZIPInputStream(fis);
			// FileOutputStream fos = new FileOutputStream(newFile);
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER];
			int len;
			while ((len = gis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			
			ByteArrayInputStream bis = new ByteArrayInputStream(fos.toByteArray());
			System.out.println(bis.available());
			
			bis.close();
			
			// close resources
			fos.close();
			gis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
