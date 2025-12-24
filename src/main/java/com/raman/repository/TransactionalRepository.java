package com.raman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.raman.model.transaction.Transaction;

import jakarta.persistence.LockModeType;


public interface TransactionalRepository extends JpaRepository<Transaction, Long>{
	
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT t FROM Transaction t WHERE t.referenceId = :referenceId")
	public Transaction findByReferenceIdWithLock(String referenceId);


	public Transaction  findByReferenceId(String referenceId);
}
