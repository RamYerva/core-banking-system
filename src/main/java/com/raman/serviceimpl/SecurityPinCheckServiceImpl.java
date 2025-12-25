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
import com.raman.model.transaction.Transaction;
import com.raman.model.transaction.Transaction_Status;
import com.raman.repository.CustomerRepository;
import com.raman.repository.TransactionalRepository;
import com.raman.service.SecurityPinCheckService;


@Service
public class SecurityPinCheckServiceImpl implements SecurityPinCheckService {

	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository customerRepository;
	private final TransactionalRepository transactionalRepository;

	public SecurityPinCheckServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
			                                      TransactionalRepository transactionalRepository) {
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
		this.transactionalRepository = transactionalRepository;
	}
	
	private static final int MAX_PIN_ATTEMPTS = 3;

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
		
		Transaction transaction = transactionalRepository
		        .findByReferenceIdWithLock(pin.getTransactionReference());
		
		
		if(transaction.getPinAttempts()>=MAX_PIN_ATTEMPTS) {
			transaction.setTransaction_Status(Transaction_Status.FAILED);
			transactionalRepository.save(transaction);
			throw new RuntimeException("Transaction blocked due to multiple invalid PIN attempts");
		}
		
		
	boolean matches = passwordEncoder.matches(pin.getPin(),pinCreation.getPin());
	
		if(!matches) {
			transaction.setPinAttempts(transaction.getPinAttempts()+1);
			transactionalRepository.save(transaction);
			throw new InvalidPinOrReferenceIDException("invalid pin");
		}
		
		transaction.setPinAttempts(0);
		transactionalRepository.save(transaction);
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
