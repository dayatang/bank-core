package com.dayatang.banking.domain;

public class TransferZeroOrNegativeException extends RuntimeException {

	private static final long serialVersionUID = 4038985568793486505L;

	public TransferZeroOrNegativeException() {
	}

	public TransferZeroOrNegativeException(String message) {
		super(message);
	}

	public TransferZeroOrNegativeException(Throwable cause) {
		super(cause);
	}

	public TransferZeroOrNegativeException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransferZeroOrNegativeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
