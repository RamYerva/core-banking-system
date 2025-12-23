package com.raman.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SecurityPinRequestDTO {

	

	@NotBlank(message = "PIN is required")
	@Size(min = 4, max = 6, message = "PIN must be between 4 and 6 digits")
	@Pattern(regexp = "^[0-9]*$", message = "PIN must contain only numbers")
	private String pin;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
