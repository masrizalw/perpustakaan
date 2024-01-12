package com.perpustakaan.app.exception;

import org.springframework.security.core.AuthenticationException;

public class UserException extends AuthenticationException {

	private static final long serialVersionUID = 8801332439465006770L;

	public UserException(String msg) {
		super(msg);
	}

	public UserException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
