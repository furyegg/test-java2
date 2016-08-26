package com.howtodoinjava.security;

import org.springframework.security.core.AuthenticationException;

public class SecurityException extends AuthenticationException {
	public SecurityException(String msg) {
		super(msg);
	}
}