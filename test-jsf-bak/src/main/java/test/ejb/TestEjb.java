package test.ejb;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Startup
public class TestEjb {
	
	private static final Logger log = LoggerFactory.getLogger(TestEjb.class);

	private InitialContext ic;

	@PostConstruct
	private void init() throws NamingException {
		ic = new InitialContext();
	}

	public void test() throws NamingException {
		Context compEnv = (Context) ic.lookup("java:comp/env");
		Hashtable<?, ?> hashtable = compEnv.getEnvironment();
		log.info("size: {}", hashtable.size());
	}

}