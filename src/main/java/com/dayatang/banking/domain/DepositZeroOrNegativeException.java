package com.dayatang.banking.domain;

public class DepositZeroOrNegativeException extends RuntimeException {

	private static final long serialVersionUID = -6483301262559409020L;

	public DepositZeroOrNegativeException() {
	}

	public DepositZeroOrNegativeException(String message) {
		super(message);
	}

	public DepositZeroOrNegativeException(Throwable cause) {
		super(cause);
	}

	public DepositZeroOrNegativeException(String message, Throwable cause) {
		super(message, cause);
	}

}
