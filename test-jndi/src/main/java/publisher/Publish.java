package publisher;

import javax.naming.InitialContext;

public class Publish {

	public static void main(String[] args) {
		//
		// This example creates a subcontext in a namespace
		//
		try {
			InitialContext ic = new InitialContext();
			ic.createSubcontext("Test");
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
