package com.raman.serviceimpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.raman.exceptions.UserNotFoundException;
import com.raman.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
	    return userRepository.findByEmail(email)
	            .map(CustomUserDetails::new)
	            .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
	}
}
