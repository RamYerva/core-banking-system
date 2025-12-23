package com.raman.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SecurityPinValidateDTO {
	
	@NotBlank
    private String transactionReference;

    @NotBlank
    @Size(min = 4, max = 6)
    @Pattern(regexp = "^[0-9]*$")
    private String pin;

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	

}
