package com.dayatang.banking.domain;

public class DepositException extends RuntimeException {

	private static final long serialVersionUID = -6483301262559409020L;

	public DepositException() {
	}

	public DepositException(String message) {
		super(message);
	}

	public DepositException(Throwable cause) {
		super(cause);
	}

	public DepositException(String message, Throwable cause) {
		super(message, cause);
	}

}
