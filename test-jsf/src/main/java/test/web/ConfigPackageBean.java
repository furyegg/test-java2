package test.web;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ConfigPackageBean implements Serializable {

	@Inject
	private Logger log;

	private String configPackage;

	@PostConstruct
	private void init() {
		log.info("Created session scoped bean...");
	}

	public String getConfigPackage() {
		return configPackage;
	}

	public void setConfigPackage(String configPackage) {
		this.configPackage = configPackage;
	}
}