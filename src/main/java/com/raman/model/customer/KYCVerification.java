package com.raman.model.customer;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class KYCVerification {
	
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer_id", nullable = false)
	private Customer customer;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private KYCDocumentType documentType;
	
	@Column(nullable = false)
	private String documentNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private KYCStatus status;
	
	private String adminRemarks; 

	@Column(nullable = false, updatable = false)
	private LocalDateTime uploadedAt;
	
	@PrePersist
	public void onUpload() {
		if (this.status == null) this.status = KYCStatus.PENDING;
		this.uploadedAt = LocalDateTime.now();
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public KYCDocumentType getDocumentType() {
		return documentType;
	}


	public void setDocumentType(KYCDocumentType documentType) {
		this.documentType = documentType;
	}


	public String getDocumentNumber() {
		return documentNumber;
	}


	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}


	public KYCStatus getStatus() {
		return status;
	}


	public void setStatus(KYCStatus status) {
		this.status = status;
	}


	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}


	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	
	public String getAdminRemarks() {
		return adminRemarks;
	}


	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}

	
	
	

}
