import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


public class DownloadTest {

	private static void list(FTPClient ftp) throws IOException {
		FTPFile[] dirs = ftp.listDirectories();
		printFiles(dirs);
		
		FTPFile[] files = ftp.listFiles("temp");
		printFiles(files);
		
		FTPFile[] fs = ftp.listFiles("1.txt");
		printFiles(fs);
		
		// InitialContext context = new InitialContext();    
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

	public static void main(String[] args) {
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		// config.set
		ftp.configure(config);
		boolean error = false;

		try {
			int reply;

			String server = "127.0.0.1";
			ftp.connect(server);
			ftp.login("kyle", "123");
			System.out.println("Connected to " + server + ".");
			System.out.print(ftp.getReplyString());

			// After connection attempt, you should check the reply code to verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			System.out.println("Connect successfully");

			// upload(ftp);
			list(ftp);

			ftp.logout();

		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
			System.exit(error ? 1 : 0);
		}
	}
}
