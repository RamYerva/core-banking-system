package com.raman.dto;


public class SecurityPinResponseDTO {
	
	
    private String message;
    
	private String transactionReference;

	
	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
