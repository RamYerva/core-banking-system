package com.raman.exceptions;

public class InvalidAccountStatusException extends RuntimeException {

	public InvalidAccountStatusException(String msg) {
		super(msg);
	}
	
}
