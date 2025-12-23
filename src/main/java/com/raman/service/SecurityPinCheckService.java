package com.raman.service;

import com.raman.dto.SecurityPinRequestDTO;
import com.raman.dto.SecurityPinValidateDTO;
import com.raman.model.account.Account;

public interface SecurityPinCheckService {
	
	
	public boolean validateSecurityPin(Account account, SecurityPinValidateDTO pin);
	
	public String pinCreation(SecurityPinRequestDTO pin);

}
