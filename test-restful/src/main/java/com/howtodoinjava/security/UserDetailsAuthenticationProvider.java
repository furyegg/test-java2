package com.howtodoinjava.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authToken)
			throws AuthenticationException {
		String password = authToken.getCredentials().toString();
		System.out.println("additionalAuthenticationChecks: " + password);
		if (!password.equals(userDetails.getPassword())) {
			throw new SecurityException("Invalid password");
		}
		authToken.setDetails(userDetails);
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authToken)
			throws AuthenticationException {
		User user = new User(username, "password", Collections.emptyList());
		return user;
	}
}