package com.perpustakaan.app.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 8801332439465006770L;

	public CustomException() {
		super();
	}

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
