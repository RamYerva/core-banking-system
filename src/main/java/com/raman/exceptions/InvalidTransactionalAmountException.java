package com.raman.exceptions;

public class InvalidTransactionalAmountException extends RuntimeException {
	
	public InvalidTransactionalAmountException(String msg) {
		super(msg);
	}

}
