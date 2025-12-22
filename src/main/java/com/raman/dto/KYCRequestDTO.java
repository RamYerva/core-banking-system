package com.raman.dto;

import com.raman.model.customer.KYCDocumentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class KYCRequestDTO {

	@NotNull
	private KYCDocumentType documentType;
	@NotBlank
	private String documentNumber;

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

}
