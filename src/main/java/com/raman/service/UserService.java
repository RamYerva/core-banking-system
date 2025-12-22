package com.raman.service;

import com.raman.dto.UserDetailsResponseDTO;
import com.raman.dto.UserRegisterRequestDTO;



public interface UserService {
	
	public UserDetailsResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO);

}
