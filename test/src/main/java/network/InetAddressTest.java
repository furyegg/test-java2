package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

	public static void main(String[] args) throws UnknownHostException {
		if (args.length == 0) {
			// String host = args[0];
			InetAddress[] addresses = InetAddress.getAllByName("www.163.com");
			for (InetAddress a : addresses) {
				System.out.println(a);
			}
		} else {
			InetAddress local = InetAddress.getLocalHost();
			System.out.println(local);
		}
	}

}
