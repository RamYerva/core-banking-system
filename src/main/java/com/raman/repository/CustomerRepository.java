package com.raman.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raman.model.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	public Customer findByUserId(Long userId);
	
	public Optional<Customer> findByUserEmail(String email);

}
