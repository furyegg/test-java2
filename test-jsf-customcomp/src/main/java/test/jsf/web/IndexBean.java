package test.jsf.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@Named("indexBean")
@ViewScoped
public class IndexBean {

	private String msg;
	
	// private cache
	
	@PostConstruct
	private void init() {
		
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}