package test.spring.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class CounterService {

	private int n;
	
	public void count() {
		++n;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

}