package com.howtodoinjava.service;

public class UserServiceBean2 implements UserService {
	public String greeting(String name) {
		return "Bonjour " + name;
	}
}