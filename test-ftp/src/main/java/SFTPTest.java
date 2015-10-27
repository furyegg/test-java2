import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;

public class SFTPTest {

	private static void list(FTPClient ftp) throws IOException {
		FTPFile[] dirs = ftp.listDirectories();
		printFiles(dirs);

		FTPFile[] files = ftp.listFiles("temp");
		printFiles(files);

		FTPFile[] fs = ftp.listFiles("1.txt");
		printFiles(fs);
	}

	private static void upload(FTPClient ftp) throws IOException {
		String file = "d:/filezilla/4.txt";
		FileInputStream fis = new FileInputStream(file);
		ftp.storeFile("upload.txt", fis);
		System.out.println("Upload file: " + file);
	}

	private static void printFiles(FTPFile[] files) {
		for (FTPFile file : files) {
			if (file.isDirectory()) {
				System.out.println("Directory: " + file.getName());
			} else if (file.isFile()) {
				System.out.println("File: " + file.getName());
			}
		}
	}

	public static void main(String[] args) throws FileSystemException {
		StandardFileSystemManager manager = new StandardFileSystemManager();
		manager.init();
		FileSystemOptions opts = new FileSystemOptions();
		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);

		FileObject fo = manager.resolveFile("sftp://reform:reform@127.0.0.1:22/", opts);
		FileObject[] children = fo.getChildren();

		System.out.println(children.length);

		for (FileObject file : children) {
			print(file.getName().getBaseName(), file.getURL());
		}
		
		manager.close();
	}

	private static void print(Object... args) {
		for (Object info : args) {
			System.out.print(info);
			System.out.print(", ");
		}
		System.out.print("\n");
	}
}
