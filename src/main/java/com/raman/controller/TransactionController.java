package com.raman.controller;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<TransactionResponseDTO> depositAmount(@Valid @RequestBody TransactionRequestDTO requestDTO){
		TransactionResponseDTO response = transactionService.depositAmount(requestDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/initiate/withdraw")
	public ResponseEntity<?> initiateWithdrawAmount(@Valid @RequestBody TransactionRequestDTO requestDTO){
		SecurityPinResponseDTO response = transactionService.initiateWithdrawAmount(requestDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/confirm/withdraw")
	public ResponseEntity<?> confirmWithdrawAmount(@Valid @RequestBody SecurityPinValidateDTO pinCheckDTO){
		TransactionResponseDTO response = transactionService.confirmWithdrawAmount(pinCheckDTO);
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
