package com.howtodoinjava.client;

import com.howtodoinjava.model.User;
import org.springframework.context.annotation.Bean;

public class UserRes {
	@Bean
	public User newUser() {
		User user = new User();
		user.setFirstName("Kyle");
		user.setLastName("Li");
		return user;
	}
}