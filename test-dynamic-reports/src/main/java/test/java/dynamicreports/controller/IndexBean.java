package test.java.dynamicreports.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@Named("indexBean")
@ViewScoped
public class IndexBean {

	private String msg;
	
	@PostConstruct
	private void init() {
		msg = "Hello DynamicReports";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}