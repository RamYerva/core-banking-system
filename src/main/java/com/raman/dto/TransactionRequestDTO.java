package com.raman.dto;

import com.raman.model.transaction.Transaction_Type;

public class TransactionRequestDTO {

	private Transaction_Type transaction_Type;
	private String sourceAccountNumber;
	private String targetAccountNumber;

	private Double amount;

	public Transaction_Type getTransaction_Type() {
		return transaction_Type;
	}

	public void setTransaction_Type(Transaction_Type transaction_Type) {
		this.transaction_Type = transaction_Type;
	}

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public String getTargetAccountNumber() {
		return targetAccountNumber;
	}

	public void setTargetAccountNumber(String targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
