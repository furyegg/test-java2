package compress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import util.Timer;

public class ZipExample {
	
	private static final int BUFFER = 1024;

	public static void main(String[] args) {
		String file = "c:/xbrl/ecr.xbrl";
		String zipFile = "c:/xbrl/ecr.zip";
		String newFile = "c:/xbrl/ecr3_new.xbrl";

		Timer t = new Timer();
		t.begin();
		compressZipFile(file, zipFile);
		t.end();

//		t.begin();
//		decompressGzipFile(zipFile, newFile);
//		t.end();

//		t.begin();
//		FileUtils.copyFile(new File(file), new File("D:/temp/ecr_copy.xbrl"));
//		t.end();
	}
	
	private static void compressZipFile(String file, String zipFile) {
		try {
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zipOS = new ZipOutputStream(fos);
			
			ZipEntry zipEntry = new ZipEntry("ecr.xbrl");
			zipOS.putNextEntry(zipEntry);

			byte[] buffer = new byte[BUFFER];
			int len;
			while ((len = fis.read(buffer)) != -1) {
				zipOS.write(buffer, 0, len);
			}
			
			// zipOS.closeEntry();
			
			zipOS.close();
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void decompressGzipFile(String zipFile, String newFile) {
		try {
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zin = new ZipInputStream(fis);
			FileOutputStream fos = new FileOutputStream(newFile);
			
			ZipEntry zipEntry = zin.getNextEntry();
			
			byte[] buffer = new byte[BUFFER];
			int len;
			while ((len = zin.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			
			fos.close();
			zin.closeEntry();
			zin.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}