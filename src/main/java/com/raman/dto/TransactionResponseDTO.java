package com.raman.dto;

import java.time.LocalDateTime;

import com.raman.model.transaction.Transaction_Status;
import com.raman.model.transaction.Transaction_Type;

public class TransactionResponseDTO {

	private String transactionReference;

	private String fromAccountNumber;
	private String toAccountNumber;

	private Double openingBalance;
	private Double transactionAmount;
	private Double closingBalance;

	private Transaction_Type transactionType;
	private Transaction_Status status;

	private LocalDateTime transactionTime;
	

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public Double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(Double closingBalance) {
		this.closingBalance = closingBalance;
	}

	public Transaction_Type getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Transaction_Type transactionType) {
		this.transactionType = transactionType;
	}

	public Transaction_Status getStatus() {
		return status;
	}

	public void setStatus(Transaction_Status status) {
		this.status = status;
	}

	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}
}
