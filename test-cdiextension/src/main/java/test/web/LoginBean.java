package test.web;

import org.slf4j.Logger;
import test.util.ConfigPackageThreadLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	public String login() {
		ConfigPackageThreadLocal.getInstance().set("ECR");
		return "success";
	}

}