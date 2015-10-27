package test.jsf.web;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@Named("rendererBean")
@ViewScoped
public class RendererBean {

	private boolean checked;
	private int check = -1;
	private String greeting = "Hello!";

	public boolean isChecked() {
		System.out.println("gggggetCheckedddddddddd()...");
		return checked;
	}

	public void setChecked(boolean checked) {
		System.out.println("setCheckedddddddddd()...");
		this.checked = checked;
	}

	public int getCheck() {
		System.out.println("getCheck()...");
		return check;
	}

	public void setCheck(int check) {
		System.out.println("setCheck()...");
		this.check = check;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

}
