package com.raman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.dto.SecurityPinResponseDTO;
import com.raman.dto.SecurityPinValidateDTO;
import com.raman.dto.TransactionRequestDTO;
import com.raman.dto.TransactionResponseDTO;
import com.raman.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}


	@PostMapping("/deposit")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<TransactionResponseDTO> depositAmount(@Valid @RequestBody TransactionRequestDTO requestDTO){
		TransactionResponseDTO response = transactionService.depositAmount(requestDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/initiate/withdraw")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<SecurityPinResponseDTO> initiateWithdrawAmount(@Valid @RequestBody TransactionRequestDTO requestDTO){
		SecurityPinResponseDTO response = transactionService.initiateWithdrawAmount(requestDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/confirm/withdraw")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<TransactionResponseDTO> confirmWithdrawAmount(@Valid @RequestBody SecurityPinValidateDTO pinCheckDTO){
		TransactionResponseDTO response = transactionService.confirmWithdrawAmount(pinCheckDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/initiate/transfer")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<SecurityPinResponseDTO> initiateTransferAmount(@Valid @RequestBody TransactionRequestDTO requestDTO){
		SecurityPinResponseDTO response = transactionService.initiateTransferAmount(requestDTO);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/confirm/transfer")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<TransactionResponseDTO> confirmTransferAmount(@Valid @RequestBody SecurityPinValidateDTO pinCheckDTO){
		TransactionResponseDTO response = transactionService.confirmTransferAmount(pinCheckDTO);
		return ResponseEntity.ok(response);
	}

	
	
	
	
	
	
	
	
	
	
	

}
