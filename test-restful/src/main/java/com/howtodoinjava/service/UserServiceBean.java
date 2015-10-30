package com.howtodoinjava.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceBean implements UserService {
	public String greeting(String name) {
		return "Hello " + name;
	}
}