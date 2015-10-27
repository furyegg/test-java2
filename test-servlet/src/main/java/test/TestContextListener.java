package test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class TestContextListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(TestContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("contextInitialized");
	}

}
