package com.raman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.dto.SecurityPinRequestDTO;
import com.raman.service.SecurityPinCheckService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pin")
public class SecurityPinCreationController {
	
	
	private final SecurityPinCheckService pinCheckService;
	
	public SecurityPinCreationController(SecurityPinCheckService pinCheckService) {
		this.pinCheckService = pinCheckService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createSecurityPin(@Valid @RequestBody SecurityPinRequestDTO pin){
		String pinResponse = pinCheckService.pinCreation(pin);
		return ResponseEntity.ok(pinResponse);
	}

}
