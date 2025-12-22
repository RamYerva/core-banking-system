package com.raman.service;

import com.raman.dto.AccountResponseDTO;
import com.raman.dto.CreateAccountRequestDTO;

public interface AccountService {
	
	public AccountResponseDTO createAccount(CreateAccountRequestDTO requestDTO);

}
