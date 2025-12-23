package com.raman.serviceimpl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raman.dto.SecurityPinRequestDTO;
import com.raman.dto.SecurityPinValidateDTO;
import com.raman.exceptions.CustomerNotFoundException;
import com.raman.exceptions.IncompleteKYCException;
import com.raman.exceptions.InvalidPinOrReferenceIDException;
import com.raman.model.account.Account;
import com.raman.model.account.SecurityPinCreation;
import com.raman.model.customer.Customer;
import com.raman.model.customer.KYCStatus;
import com.raman.repository.CustomerRepository;
import com.raman.service.SecurityPinCheckService;


@Service
public class SecurityPinCheckServiceImpl implements SecurityPinCheckService {

	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository customerRepository;

	public SecurityPinCheckServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
	}

	@Override
	public boolean validateSecurityPin(Account account, SecurityPinValidateDTO pin) {
		
		
		if(pin.getPin() == null || pin.getTransactionReference() == null) {
			throw new InvalidPinOrReferenceIDException("please enter the security pin");
		}
		
		Customer customer = account.getCustomer();
		
		SecurityPinCreation pinCreation = customer.getPinCreation();
		
		if(pinCreation == null) {
			throw new InvalidPinOrReferenceIDException("Security pin not created");
		}
		
	boolean matches = passwordEncoder.matches(pin.getPin(),pinCreation.getPin());
	
		if(!matches) {
			throw new InvalidPinOrReferenceIDException("Invalid pin");
		}

		return true;
	}
	
	

	@Override
	public String pinCreation(SecurityPinRequestDTO pin) {

		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerRepository.findByUserEmail(loggedUser)
				.orElseThrow(() -> new CustomerNotFoundException("customer not found"));
		
		
		if(customer.getKycStatus() != KYCStatus.VERIFIED) {
			throw new IncompleteKYCException("complete kyc to create pin");
		}
		
		if (customer.getPinCreation() != null) {
		    throw new RuntimeException("Security PIN already exists");
		}


		String encryptedPin = passwordEncoder.encode(pin.getPin());

		SecurityPinCreation spc = new SecurityPinCreation();
		spc.setCustomer(customer);
		spc.setPin(encryptedPin);

		customer.setPinCreation(spc);
		customerRepository.save(customer);

		return "security pin is succesfully created";
	}

}
