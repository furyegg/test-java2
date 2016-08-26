package com.howtodoinjava.client;

import com.howtodoinjava.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserBean {
	public String say(User user) {
		return "Hello " + user.getFirstName() + " " + user.getLastName();
	}
}