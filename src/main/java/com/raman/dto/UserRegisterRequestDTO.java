package com.raman.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRegisterRequestDTO {

	@NotBlank
    private String mobile;
	@NotBlank
    private String password;
	@NotBlank
    private String email;

    public UserRegisterRequestDTO() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}