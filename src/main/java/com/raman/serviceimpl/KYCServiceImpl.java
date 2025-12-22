package com.raman.serviceimpl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raman.dto.KYCRequestDTO;
import com.raman.model.customer.Customer;
import com.raman.model.customer.KYCStatus;
import com.raman.model.customer.KYCVerification;
import com.raman.repository.CustomerRepository;
import com.raman.repository.KYCVerificationRepository;
import com.raman.service.KYCService;


@Service
public class KYCServiceImpl implements KYCService{
	
	private final CustomerRepository customerRepository;
	private final KYCVerificationRepository kycRepository;

	public KYCServiceImpl(CustomerRepository customerRepository, KYCVerificationRepository kycRepository) {
		
		this.customerRepository = customerRepository;
		this.kycRepository = kycRepository;
	}

	@Override
	public String uploadKYCDocuments(KYCRequestDTO kycRequestDTO) {
		
		
	String loggedUser = SecurityContextHolder.getContext()
			                                 .getAuthentication()
			                                 .getName();
	
    
	Customer customer = customerRepository.findByUserEmail(loggedUser)
	        .orElseThrow(() -> new RuntimeException("Customer not found"));
	
	KYCVerification kyc = new KYCVerification();
	kyc.setCustomer(customer);
	kyc.setDocumentType(kycRequestDTO.getDocumentType());
	kyc.setDocumentNumber(kycRequestDTO.getDocumentNumber());
	
	kycRepository.save(kyc);
	
	customer.setKycStatus(KYCStatus.PENDING);
	customerRepository.save(customer);
		
	return "KYC documents uploaded successfully and are pending verification.";
	}

}
