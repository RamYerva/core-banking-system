package com.raman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.dto.KYCRequestDTO;
import com.raman.service.KYCService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/kyc")
@Tag(name = "KYC Verification", description = "APIs for KYC document upload and verification")
public class KYCVerificationController {
	
	
	private final KYCService kycService;

	public KYCVerificationController(KYCService kycService) {
		this.kycService = kycService;
	}
	
	
	
	@PostMapping("/upload")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@Operation(summary = "Upload KYC Documents", description = "Uploads KYC documents for verification")
	public ResponseEntity<?> uploadKYCDocuments(@Valid @RequestBody KYCRequestDTO kycRequestDTO){
		String response = kycService.uploadKYCDocuments(kycRequestDTO);
		return ResponseEntity.status(200).body(response);
	}

}
