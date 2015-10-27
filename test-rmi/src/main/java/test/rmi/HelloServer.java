package test.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;


public class HelloServer {
	public static void main(String[] args) {
		try {
			// Step 1: Instantiate the Hello servant
			HelloImpl helloRef = new HelloImpl();

			// Step 2: Publish the reference in the Naming Service
			// using JNDI API
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.cosnaming.CNCtxFactory");
			env.put(Context. PROVIDER_URL, "iiop://localhost:1050");

			Context initialNamingContext = new InitialContext(env);
			initialNamingContext.rebind("HelloService", helloRef);

			System.out.println("Hello Server: Ready...");

		} catch (Exception e) {
			System.out.println("Trouble: " + e);
			e.printStackTrace();
		}
	}
}
