package com.raman.model.account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.raman.model.customer.Customer;
import com.raman.model.transaction.Transaction;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Table(name = "accounts")
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "account_number", unique = true, nullable = false, length = 20)
	private String accountNumber;

	@Column(nullable = false, updatable = false)
	private String ifscCode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountType accountType;

	@Column(nullable = false)
	private Double balance;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountStatus status;

	@OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> outgoingTransactions = new ArrayList<>();

	@OneToMany(mappedBy = "targetAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> incomingTransactions = new ArrayList<>();
	
	@Version
	@Column(name = "version")
	private Long version;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		if (this.balance == null) {
			this.balance = 0.0;
		}
		if (this.branch != null) {
			this.ifscCode = branch.getIfscCode();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public List<Transaction> getOutgoingTransactions() {
		return outgoingTransactions;
	}

	public void setOutgoingTransactions(List<Transaction> outgoingTransactions) {
		this.outgoingTransactions = outgoingTransactions;
	}

	public List<Transaction> getIncomingTransactions() {
		return incomingTransactions;
	}

	public void setIncomingTransactions(List<Transaction> incomingTransactions) {
		this.incomingTransactions = incomingTransactions;
	}

}
