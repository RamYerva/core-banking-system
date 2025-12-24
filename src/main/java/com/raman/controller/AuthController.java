package com.raman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.dto.LoginRequestDTO;
import com.raman.dto.LoginResponseDTO;
import com.raman.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user authentication and token generation")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "User Login", description = "Authenticates user and generates JWT token")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		try {
		   LoginResponseDTO tokenResponseDTO = authService.login(loginRequestDTO);
		   return ResponseEntity.ok(tokenResponseDTO);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}
	}

}
