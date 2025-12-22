package com.raman.dto;

public class LoginResponseDTO {

    private String jwtToken;

    public LoginResponseDTO() {
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}