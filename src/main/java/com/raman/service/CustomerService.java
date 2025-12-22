package com.raman.service;

import com.raman.dto.CustomerRegistrationDTO;
import com.raman.dto.CustomerResponseDTO;

public interface CustomerService {
	
	public CustomerResponseDTO registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);

}
