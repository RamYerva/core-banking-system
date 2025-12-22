package com.raman.serviceimpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raman.dto.LoginRequestDTO;
import com.raman.dto.LoginResponseDTO;
import com.raman.model.auth.User;
import com.raman.repository.UserRepository;
import com.raman.security.JwtService;
import com.raman.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {

		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		String token = jwtService.generateToken(userDetails);
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO.setJwtToken("Bearer " + token);

		return loginResponseDTO;
	}

}
