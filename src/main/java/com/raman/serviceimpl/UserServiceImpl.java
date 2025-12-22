package com.raman.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raman.dto.UserDetailsResponseDTO;
import com.raman.dto.UserRegisterRequestDTO;
import com.raman.exceptions.UserNotFoundException;
import com.raman.model.auth.RoleType;
import com.raman.model.auth.User;
import com.raman.repository.UserRepository;
import com.raman.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ModelMapper mapper;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository,ModelMapper mapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetailsResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {

		if (userRepository.findByEmail(userRegisterRequestDTO.getEmail()).isPresent()) {
            throw new UserNotFoundException("User with email " + userRegisterRequestDTO.getEmail() + " already exists.");
        }
		
		Set<RoleType> roles = new HashSet<>();
	    roles.add(RoleType.CUSTOMER);

		User user = new User();
		user.setMobile(userRegisterRequestDTO.getMobile());
		user.setEmail(userRegisterRequestDTO.getEmail());
		user.setPasswordHash(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));
        user.setRoles(roles);
		
        User savedUser = userRepository.save(user);
        UserDetailsResponseDTO responseDTO = mapper.map(savedUser, UserDetailsResponseDTO.class);
        
		return responseDTO;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
