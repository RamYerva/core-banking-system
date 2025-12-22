package com.raman.exceptions;

public class KYCNotApprovedException extends RuntimeException {
	public KYCNotApprovedException(String message) {
		super(message);
	}
}
