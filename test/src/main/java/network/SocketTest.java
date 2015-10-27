package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {

	public static void main(String[] args) {
		try {
			Socket s = null;
			try {
				s = new Socket("www.163.com", 80);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			s.setSoTimeout(10000);

			InputStream inputStream = null;
			try {
				inputStream = s.getInputStream();
			} catch (InterruptedIOException e) {
				e.printStackTrace();
			}
			Scanner in = new Scanner(inputStream);
			while (in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
