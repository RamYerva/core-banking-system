package com.raman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.dto.AccountResponseDTO;
import com.raman.dto.CreateAccountRequestDTO;
import com.raman.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/account")
@Tag(name = "Account Management", description = "APIs for Account Creation")
public class AccountController {
	
	
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/create")
	@Operation(summary = "creating a new user", description = "customer can open the new account")
	public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequestDTO requestDTO){
		AccountResponseDTO responseDTO = accountService.createAccount(requestDTO);
		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/balance/{accountNumber}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<?> getAccountBalance(@PathVariable String accountNumber){
		double balance = accountService.getAccountBalance(accountNumber);
		return ResponseEntity.ok(balance);
	}

}
