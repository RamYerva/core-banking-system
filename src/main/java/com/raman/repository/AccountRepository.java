package com.raman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raman.model.account.Account;
import com.raman.model.customer.Customer;
import com.raman.model.account.AccountType;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public boolean existsByCustomerAndAccountType(Customer customer, AccountType accountType);

}
