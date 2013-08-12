package com.dayatang.banking.domain;

public class BalanceInsufficientException extends RuntimeException {

	private static final long serialVersionUID = -5285878158672877285L;

	public BalanceInsufficientException() {
	}

	public BalanceInsufficientException(String message) {
		super(message);
	}

	public BalanceInsufficientException(Throwable cause) {
		super(cause);
	}

	public BalanceInsufficientException(String message, Throwable cause) {
		super(message, cause);
	}

}
