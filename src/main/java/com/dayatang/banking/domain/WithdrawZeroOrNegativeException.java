package com.dayatang.banking.domain;

public class WithdrawZeroOrNegativeException extends RuntimeException {

	private static final long serialVersionUID = 6011359339902205035L;

	public WithdrawZeroOrNegativeException() {
	}

	public WithdrawZeroOrNegativeException(String message) {
		super(message);
	}

	public WithdrawZeroOrNegativeException(Throwable cause) {
		super(cause);
	}

	public WithdrawZeroOrNegativeException(String message, Throwable cause) {
		super(message, cause);
	}

	public WithdrawZeroOrNegativeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
