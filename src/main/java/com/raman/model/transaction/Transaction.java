package com.raman.model.transaction;

import java.time.LocalDateTime;

import com.raman.model.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transaction_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source_account_id", nullable = true)
	private Account sourceAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_account_id", nullable = true)
	private Account targetAccount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Transaction_Type transaction_type;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private Double openingBalance;

	@Column(nullable = true)
	private Double closingBalance;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Transaction_Status transaction_Status;

	@Column(unique = true, nullable = false)
	private String referenceId;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	public void onTransaction() {
		this.createdAt = LocalDateTime.now();
	}

	public Long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Account getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(Account sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public Account getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(Account targetAccount) {
		this.targetAccount = targetAccount;
	}

	public Transaction_Type getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(Transaction_Type transaction_type) {
		this.transaction_type = transaction_type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Transaction_Status getTransaction_Status() {
		return transaction_Status;
	}

	public void setTransaction_Status(Transaction_Status transaction_Status) {
		this.transaction_Status = transaction_Status;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String reference_id) {
		this.referenceId = reference_id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(Double closingBalance) {
		this.closingBalance = closingBalance;
	}

}
