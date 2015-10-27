package jar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class TestJar {

	public static void main(String[] args) throws Exception {
		JarInputStream jarIn = new JarInputStream(new FileInputStream("tava-common-12.9.0.jar"));
		JarOutputStream jarOut = new JarOutputStream(new FileOutputStream("tava-common.jar"));

		byte[] buffer = new byte[1024];
		ByteArrayInputStream jpaBin = null;
		ByteArrayInputStream sqlBin = null;

		String jpaTargetFileName = "jpa-persistence.xml";
		String selectedJpaFileName = "oracle_" + jpaTargetFileName;
		String newJpaJarFullName = "";
		
		String sqlTargetFileName = "sqlmap-config.xml";
		String selectedSqlFileName = "oracle_" + sqlTargetFileName;
		String newSqlJarFullName = "";

		JarEntry jarEntry = null;
		while ((jarEntry = jarIn.getNextJarEntry()) != null) {
			String jarEntryName = jarEntry.getName();
			String fileShortName = getFileShortName(jarEntryName);

			if (fileShortName.endsWith(jpaTargetFileName)) {
				if (fileShortName.equals(selectedJpaFileName)) {
					String path = jarEntryName.substring(0, jarEntryName.length() - fileShortName.length());
					newJpaJarFullName = path + jpaTargetFileName;

					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					int read;
					while ((read = jarIn.read(buffer, 0, buffer.length)) != -1) {
						bout.write(buffer, 0, read);
					}
					jpaBin = new ByteArrayInputStream(bout.toByteArray());
				}
				continue;
			}
			
			if (fileShortName.endsWith(sqlTargetFileName)) {
				if (fileShortName.equals(selectedSqlFileName)) {
					String path = jarEntryName.substring(0, jarEntryName.length() - fileShortName.length());
					newSqlJarFullName = path + sqlTargetFileName;

					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					int read;
					while ((read = jarIn.read(buffer, 0, buffer.length)) != -1) {
						bout.write(buffer, 0, read);
					}
					sqlBin = new ByteArrayInputStream(bout.toByteArray());
				}
				continue;
			}

			jarOut.putNextEntry(jarEntry);
			int read;
			while ((read = jarIn.read(buffer, 0, buffer.length)) != -1) {
				jarOut.write(buffer, 0, read);
			}
		}

		if (jpaBin != null) {
			JarEntry newEntry = new JarEntry(newJpaJarFullName);
			jarOut.putNextEntry(newEntry);
			int read;
			while ((read = jpaBin.read(buffer, 0, buffer.length)) != -1) {
				jarOut.write(buffer, 0, read);
			}
		}
		
		if (sqlBin != null) {
			JarEntry newEntry = new JarEntry(newSqlJarFullName);
			jarOut.putNextEntry(newEntry);
			int read;
			while ((read = sqlBin.read(buffer, 0, buffer.length)) != -1) {
				jarOut.write(buffer, 0, read);
			}
		}

		jarIn.close();
		jarOut.close();
	}

	private static String getFileShortName(String fileFullName) {
		if (fileFullName.lastIndexOf("/") > -1) {
			return fileFullName.substring(fileFullName.lastIndexOf("/") + 1, fileFullName.length());
		} else {
			return fileFullName;
		}
	}

}
