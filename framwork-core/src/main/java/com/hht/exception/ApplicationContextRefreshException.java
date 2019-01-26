package com.hht.exception;

public class ApplicationContextRefreshException extends FrameworkException{

	private static final long serialVersionUID = 1L;

	public ApplicationContextRefreshException(String message) {
		super(message);
	}

	public ApplicationContextRefreshException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
