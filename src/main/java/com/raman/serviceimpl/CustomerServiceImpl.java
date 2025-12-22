package com.raman.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raman.dto.CustomerRegistrationDTO;
import com.raman.dto.CustomerResponseDTO;
import com.raman.exceptions.CustomerNotFoundException;
import com.raman.model.auth.User;
import com.raman.model.customer.Customer;
import com.raman.repository.CustomerRepository;
import com.raman.repository.UserRepository;
import com.raman.service.CustomerService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	
	
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final ModelMapper mapper;

	public CustomerServiceImpl(UserRepository userRepository, CustomerRepository customerRepository, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.mapper = mapper;
	}



	@Override
	public CustomerResponseDTO registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
		
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByEmail(loggedUser)
				                  .orElseThrow(()->new CustomerNotFoundException(""+ "User not found with email: "+loggedUser));
		
		Customer customer = new Customer();
		customer.setUser(user);
		customer.setFullName(customerRegistrationDTO.getFullName());
		customer.setDob(customerRegistrationDTO.getDob());
		customer.setAge(customerRegistrationDTO.getAge());
		customer.setGender(customerRegistrationDTO.getGender());
		customer.setNationality(customerRegistrationDTO.getNationality());
		customer.setAddress(customerRegistrationDTO.getAddress());
		
		
		if(customer.getAge() < 10) {
	        throw new IllegalArgumentException("Customer age must be at least 10");
	    }
		
		customerRepository.save(customer);
		
		CustomerResponseDTO customerResponse = mapper.map(customer, CustomerResponseDTO.class);
		
		return customerResponse;
	}
	
	

}
