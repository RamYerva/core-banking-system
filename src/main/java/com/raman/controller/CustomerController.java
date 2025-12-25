package com.raman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.dto.CustomerRegistrationDTO;
import com.raman.dto.CustomerResponseDTO;
import com.raman.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer Management", description = "APIs for Customer Registration and Management")
public class CustomerController {
	
	
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Register Customer", description = "Registers a new customer")
	public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegistrationDTO customerRegistrationDTO){
		
		CustomerResponseDTO responseDTO =   customerService.registerCustomer(customerRegistrationDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}
}
