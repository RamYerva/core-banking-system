package com.raman.model.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.raman.model.account.Account;
import com.raman.model.account.SecurityPinCreation;
import com.raman.model.auth.User;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Table(name = "customers")
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(nullable = false)
	private LocalDate dob;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
    private Gender gender;
	
	@Column(nullable = false)
	private int age;
	
	@Column(nullable = false)
	private String nationality;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private KYCStatus kycStatus;
	
	@Column(nullable = false)
	private String address;
	

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<KYCVerification> kycVerifications = new ArrayList<>();
	

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Account> accounts = new ArrayList<>();
	
	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private SecurityPinCreation pinCreation;
	
	@PrePersist
	public void createdAt() {
		this.kycStatus = KYCStatus.NOT_STARTED;
		this.createdAt = LocalDateTime.now();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public KYCStatus getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(KYCStatus kycStatus) {
		this.kycStatus = kycStatus;
	}
	
	public List<KYCVerification> getKycVerifications() {
		return kycVerifications;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public SecurityPinCreation getPinCreation() {
		return pinCreation;
	}

	public void setPinCreation(SecurityPinCreation pinCreation) {
		this.pinCreation = pinCreation;
	}


}
