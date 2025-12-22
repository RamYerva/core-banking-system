package com.raman.dto;

import java.time.LocalDate;
import java.util.List;

import com.raman.model.customer.Gender;
import com.raman.model.customer.KYCStatus;

public class CustomerResponseDTO {
	
	
	private Long id;
	private String fullName;
	private LocalDate dob;
	private int age;
	private Gender gender;
	private String nationality;
	private KYCStatus kycStatus;
	private String address;
	private List<AccountResponseDTO> accounts;

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public KYCStatus getKycStatus() {
		return kycStatus;
	}
	public void setKycStatus(KYCStatus kycStatus) {
		this.kycStatus = kycStatus;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<AccountResponseDTO> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<AccountResponseDTO> accounts) {
		this.accounts = accounts;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
