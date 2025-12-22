package com.raman.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raman.model.customer.KYCStatus;
import com.raman.model.customer.KYCVerification;

@Repository
public interface KYCVerificationRepository extends JpaRepository<KYCVerification, Long>{
	
	KYCStatus findByStatus(KYCStatus status);
	
    boolean existsByDocumentNumberAndStatus(String documentNumber, KYCStatus status);
	

}
