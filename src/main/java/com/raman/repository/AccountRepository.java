package com.raman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raman.model.account.Account;
import com.raman.model.customer.Customer;

import jakarta.persistence.LockModeType;

import com.raman.model.account.AccountType;





@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public boolean existsByCustomerAndAccountType(Customer customer, AccountType accountType);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
	Account findByAccountNumberWithLock(String accountNumber);

}
