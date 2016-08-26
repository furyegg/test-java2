package test.java.pentaho.controller;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("appBean")
@ApplicationScoped
public class ApplicationBean {
	private static final Logger log = LoggerFactory.getLogger(ApplicationBean.class);

	private String appName = "Pentaho Reporting";

	@PostConstruct
	public void init() {
		log.info("Pentaho engine initialize beginning...");
		ClassicEngineBoot.getInstance().start();
		log.info("Pentaho engine initialize end.");
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}