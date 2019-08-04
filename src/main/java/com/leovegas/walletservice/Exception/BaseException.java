package com.leovegas.walletservice.Exception;

public class BaseException extends Exception {
	private static final long serialVersionUID = -6097389074855191821L;
	private String message;

	public BaseException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
