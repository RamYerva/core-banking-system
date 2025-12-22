package com.raman.serviceimpl;


import java.util.Objects;

import org.springframework.stereotype.Service;

import com.raman.admin.AdminCheckDTO;
import com.raman.exceptions.CustomerAlreadyVerifiedException;
import com.raman.exceptions.CustomerNotFoundException;
import com.raman.exceptions.InvalidAdminDecisionException;
import com.raman.exceptions.KYCNotApprovedException;
import com.raman.exceptions.KYCRecordNotFoundException;
import com.raman.model.auth.User;
import com.raman.model.customer.Customer;
import com.raman.model.customer.KYCStatus;
import com.raman.model.customer.KYCVerification;
import com.raman.repository.CustomerRepository;
import com.raman.repository.KYCVerificationRepository;
import com.raman.service.AdminApprovalService;

import jakarta.transaction.Transactional;


@Service
public class AdminApprovalServiceImpl implements AdminApprovalService{
	
	
	private final KYCVerificationRepository kycVerificationRepository;
	private final CustomerRepository customerRepository;
	

	public AdminApprovalServiceImpl(KYCVerificationRepository kycVerificationRepository, CustomerRepository customerRepository) {
		this.kycVerificationRepository = kycVerificationRepository;
		this.customerRepository = customerRepository;
	}


	@Override
	@Transactional
	public AdminCheckDTO approveCustomer(Long id, AdminCheckDTO approvalDTO) {
		
		
	  KYCVerification verification = kycVerificationRepository.findById(id)
			                                                  .orElseThrow(()->new KYCRecordNotFoundException("KYC Record not found with ID: " + id));
	   
	  if (verification.getStatus() != KYCStatus.PENDING) {
	        throw new KYCNotApprovedException("Error: This record is " + verification.getStatus() + ". Only PENDING records can be approved.");
	  }
	  
	  Customer customer = verification.getCustomer();
	  
	  if(customer==null) {
		  throw new CustomerNotFoundException("Error: Customer not found for the KYC record ID: " + id);
	  }
	  
	  User user = customer.getUser();
	    if (user.getEmail() == null) {
	         throw new RuntimeException("Data Error: Linked User has no email identity.");
	    }
	  
	    if (customer.getKycStatus() == KYCStatus.VERIFIED) {
	        throw new CustomerAlreadyVerifiedException("Customer already KYC verified");
	    }
	    
	    if (approvalDTO.getStatus() != KYCStatus.APPROVED &&
	    	    approvalDTO.getStatus() != KYCStatus.REJECTED) {
	    	    throw new InvalidAdminDecisionException("Invalid admin decision");
	    }

	    
	    if(approvalDTO.getStatus() == KYCStatus.APPROVED) {
	    	
	    	boolean sameDocAlreadyApproved = customer.getKycVerifications().stream()
	    	        .anyMatch(k -> k.getStatus() == KYCStatus.APPROVED &&
	    	                Objects.equals(k.getDocumentNumber(),
	    	                        verification.getDocumentNumber()));

	    	if (sameDocAlreadyApproved) {
	    	    throw new CustomerAlreadyVerifiedException("This document is already approved.");
	    	}

	    	 verification.setStatus(approvalDTO.getStatus());
	         customer.setKycStatus(KYCStatus.VERIFIED);
	    }
	    else {
	    	verification.setStatus(KYCStatus.REJECTED);
	    }
	    
	    verification.setAdminRemarks(approvalDTO.getRemarks());
	    
	   kycVerificationRepository.save(verification);
	   customerRepository.save(customer);
	   
       return approvalDTO;
	}

}
