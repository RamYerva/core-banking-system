package com.raman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.dto.UserDetailsResponseDTO;
import com.raman.dto.UserRegisterRequestDTO;
import com.raman.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for user registration and management")
public class UserController {
	
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("/register")
	@Operation(summary = "Register a new user", description = "Registers")
	public ResponseEntity<UserDetailsResponseDTO> registerUser(@Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDTO){
		  UserDetailsResponseDTO responseDTO = userService.registerUser(userRegisterRequestDTO);
		  return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}
	
    

}
