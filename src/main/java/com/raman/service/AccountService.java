package com.raman.service;

import com.raman.dto.AccountResponseDTO;
import com.raman.dto.CreateAccountRequestDTO;
import com.raman.dto.SecurityPinRequestDTO;

public interface AccountService {
	
	public AccountResponseDTO createAccount(CreateAccountRequestDTO requestDTO);
	
	//public void createOrUpdatePin(SecurityPinRequestDTO pinRequestDTO);

}
