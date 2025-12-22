package com.raman.admin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.raman.exceptions.AdminCreationEcxception;
import com.raman.model.auth.RoleType;
import com.raman.model.auth.User;
import com.raman.repository.UserRepository;


@Configuration
public class DataInitializer {
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		
		String adminEmail = "admin@bank.com";
		return args -> {
		try {
		Optional<User> adminOptional = userRepository.findByEmail(adminEmail);
		
		if(adminOptional.isEmpty()) {
			User admin = new User();
			
			admin.setEmail(adminEmail);
			admin.setPasswordHash(passwordEncoder.encode("admin123"));
			admin.setMobile("6459785463");
			 Set<RoleType> role = new HashSet<>();
		     role.add(RoleType.ADMIN);
		     admin.setRoles(role);
			User saved = userRepository.save(admin);
			
			if(saved.getId()==null) {
				throw new AdminCreationEcxception("Admin creation failed");
			}
		}
	}
		catch (Exception e) {
			throw new AdminCreationEcxception("Admin creation error " + e.getMessage());
		}
		};
	}
}