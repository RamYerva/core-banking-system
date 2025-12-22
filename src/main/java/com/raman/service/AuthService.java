package com.raman.service;

import com.raman.dto.LoginRequestDTO;
import com.raman.dto.LoginResponseDTO;

public interface AuthService {
	
	
	public LoginResponseDTO login(LoginRequestDTO loginRequest);

}
