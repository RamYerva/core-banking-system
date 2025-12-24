package com.raman.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raman.dto.SecurityPinResponseDTO;
import com.raman.dto.SecurityPinValidateDTO;
import com.raman.dto.TransactionRequestDTO;
import com.raman.dto.TransactionResponseDTO;
import com.raman.exceptions.AccountNotFoundException;
import com.raman.exceptions.FalseTransactionException;
import com.raman.exceptions.InvalidAccountStatusException;
import com.raman.exceptions.InvalidPinOrReferenceIDException;
import com.raman.exceptions.InvalidTransactionalAmountException;
import com.raman.exceptions.IvalidTransactionalAmountException;
import com.raman.model.account.Account;
import com.raman.model.account.AccountStatus;
import com.raman.model.customer.Customer;
import com.raman.model.transaction.Transaction;
import com.raman.model.transaction.Transaction_Status;
import com.raman.model.transaction.Transaction_Type;
import com.raman.repository.AccountRepository;
import com.raman.repository.CustomerRepository;
import com.raman.repository.TransactionalRepository;
import com.raman.service.SecurityPinCheckService;
import com.raman.service.TransactionService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final AccountRepository accountRepository;
	private final TransactionalRepository transactionalRepository;
	private final ModelMapper mapper;
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CustomerRepository customerRepository;

	private final SecurityPinCheckService pinCheckService;

	private final String PIN_MSG = "enter PIN to confirm withdrawal";

	public TransactionServiceImpl(AccountRepository accountRepository, TransactionalRepository transactionalRepository,
			ModelMapper mapper, SecurityPinCheckService pinCheckService, JdbcTemplate jdbcTemplate) {

		this.accountRepository = accountRepository;
		this.transactionalRepository = transactionalRepository;
		this.mapper = mapper;
		this.pinCheckService = pinCheckService;
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final long OFFSET = 12345L;
	private static final long MULTIPLIER = 987654321L;
	
	

	@Override
	@Transactional
	public TransactionResponseDTO depositAmount(TransactionRequestDTO requestDTO) {

		// get the account
		Account account = accountRepository.findByAccountNumberWithLock(requestDTO.getTargetAccountNumber());

		// validate account
		if (account == null) {
			throw new AccountNotFoundException(
					"Account is not found with this : " + requestDTO.getTargetAccountNumber());
		}

		// validate the account status
		if (account.getStatus() != AccountStatus.ACTIVE) {
			throw new InvalidAccountStatusException("Account must be ACTIVE to receive deposits.");
		}

		Double transactionalAmount = requestDTO.getAmount();

		// validate the amount
		if (requestDTO.getAmount() == null || requestDTO.getAmount() <= 0) {
			throw new InvalidTransactionalAmountException("Amount must be greater than zero.");
		}

		Double openingBalance = account.getBalance();
		Double closingBalance = round(openingBalance + transactionalAmount);

		// change and save the account
		account.setBalance(closingBalance);
		accountRepository.save(account);

		// Transactional record
		Transaction transaction = new Transaction();

		transaction.setTargetAccount(account);
		transaction.setTransaction_type(Transaction_Type.DEPOSIT);
		transaction.setTransaction_Status(Transaction_Status.SUCCESS);
		transaction.setAmount(transactionalAmount);
		transaction.setOpeningBalance(openingBalance);
		transaction.setClosingBalance(closingBalance);

		transaction.setReferenceId(generateTransactionReference());
		//transaction.setCreatedAt(LocalDateTime.now());

		// save the transaction after security_pin authentication
		Transaction savedTransaction = transactionalRepository.save(transaction);

		// map to dto
		TransactionResponseDTO responseDTO = mapper.map(savedTransaction, TransactionResponseDTO.class);
		if (savedTransaction.getTargetAccount() != null) {
		    responseDTO.setToAccountNumber(
		        savedTransaction.getTargetAccount().getAccountNumber()
		    );
		}

		if (savedTransaction.getSourceAccount() != null) {
		    responseDTO.setFromAccountNumber(
		        savedTransaction.getSourceAccount().getAccountNumber()
		    );
		}
		
		responseDTO.setTransactionTime(savedTransaction.getCreatedAt());


		return responseDTO;
	}

	// helper method for amount correction
	private Double round(Double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	// helper method for generating Transactional Ref_Id
	private String generateTransactionReference() {

		jdbcTemplate.execute("UPDATE txs_ref_seq SET next_val = LAST_INSERT_ID(next_val + 1)");
		Long serialValue = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		String maskedSeq = maskSequence(serialValue); 
		String yearPart = String.valueOf(LocalDate.now().getYear());
		return "TXN" + yearPart + maskedSeq;
	}
	
	private String maskSequence(long seq) {
		
		long scrambled = (seq ^ OFFSET) * MULTIPLIER;
		long bounded = Math.abs(scrambled);
		String base36 = Long.toString(bounded, 36).toUpperCase();
		return String.format("%1$" + 8 + "s", base36).replace(' ', '0');

	}
	
	@Override
	@Transactional
	public SecurityPinResponseDTO initiateWithdrawAmount(TransactionRequestDTO requestDTO) {
		
		Account account = accountRepository.findByAccountNumberWithLock(requestDTO.getSourceAccountNumber());

		if (account == null) {
			throw new AccountNotFoundException(
					"Account is not found with this : " + requestDTO.getSourceAccountNumber());
		}

		// validate the account status
		if (account.getStatus() != AccountStatus.ACTIVE) {
			throw new InvalidAccountStatusException("Account must be ACTIVE.");
		}

		Double transactionalAmount = requestDTO.getAmount(); // 10000

		Double openingBalance = account.getBalance(); //// initial balance = 50000

		if (transactionalAmount == null || transactionalAmount <= 0) {
		    throw new InvalidTransactionalAmountException("Amount must be greater than zero");
		}

		if (transactionalAmount > openingBalance) {
		    throw new InvalidTransactionalAmountException("Insufficient balance");
		}


		Transaction transaction = new Transaction();

		transaction.setSourceAccount(account);
		transaction.setTransaction_type(Transaction_Type.WITHDRAWAL);
		transaction.setTransaction_Status(Transaction_Status.PENDING);
		transaction.setOpeningBalance(openingBalance); // 50000
		transaction.setAmount(transactionalAmount); // 10000
		transaction.setClosingBalance(openingBalance);
		transaction.setReferenceId(generateTransactionReference());

		// save the transaction after security_pin authentication
		Transaction savedTransaction = transactionalRepository.save(transaction);

		SecurityPinResponseDTO responseDTO = new SecurityPinResponseDTO();
		responseDTO.setTransactionReference(savedTransaction.getReferenceId());
		responseDTO.setMessage(PIN_MSG);

		return responseDTO;
	}

	
	
	
	@Override
	@Transactional
	public TransactionResponseDTO confirmWithdrawAmount(SecurityPinValidateDTO pinCheckDTO) {

		Transaction transaction = transactionalRepository
				.findByReferenceIdWithLock(pinCheckDTO.getTransactionReference());

		if (transaction == null) {
			throw new FalseTransactionException("transaction is not initiated");
		}

		if (transaction.getTransaction_Status() != Transaction_Status.PENDING) {
			throw new RuntimeException("Transaction already processed");
		}

		Account account = accountRepository
				.findByAccountNumberWithLock(transaction.getSourceAccount().getAccountNumber());

		// validation with securityPin
		if (!pinCheckService.validateSecurityPin(account, pinCheckDTO)) {
			transaction.setTransaction_Status(Transaction_Status.FAILED);
			transactionalRepository.save(transaction);
			throw new InvalidPinOrReferenceIDException("Invalid pin or reference");
		}

		Double balance = account.getBalance();
		Double amount = transaction.getAmount();

		if (amount > balance || amount < 0) {
			transaction.setTransaction_Status(Transaction_Status.FAILED);
			transactionalRepository.save(transaction);
			throw new InvalidTransactionalAmountException("Insufficient Balance");
		}

		Double closingBalance = round(balance - amount);

		account.setBalance(closingBalance);
		accountRepository.save(account);

		transaction.setClosingBalance(closingBalance);
		transaction.setTransaction_Status(Transaction_Status.SUCCESS);
		//transaction.setCreatedAt(LocalDateTime.now());

		transactionalRepository.save(transaction);

		TransactionResponseDTO responseDTO = mapper.map(transaction, TransactionResponseDTO.class);

		return responseDTO;
	}
	
	
	@Override
	@Transactional
	public SecurityPinResponseDTO initiateTransferAmount(TransactionRequestDTO requestDTO) {

		// find the source account
		Account sourceAccount = accountRepository.findByAccountNumberWithLock(requestDTO.getSourceAccountNumber());

		// find the target account
		Account targetAccount = accountRepository.findByAccountNumberWithLock(requestDTO.getTargetAccountNumber());

		if (sourceAccount == null) {
			throw new AccountNotFoundException("Account is not found with " + requestDTO.getSourceAccountNumber());
		}

		if (targetAccount == null) {
			throw new AccountNotFoundException("Account is not found with " + requestDTO.getTargetAccountNumber());
		}

		if (sourceAccount.getStatus() != AccountStatus.ACTIVE || targetAccount.getStatus() != AccountStatus.ACTIVE) {
			throw new InvalidAccountStatusException("Account must be ACTIVE.");
		}

		// transfer the amount from source-account to target-account

		// get the amount
		Double amount = requestDTO.getAmount();

		if (amount == null || amount <= 0) {
		    throw new InvalidTransactionalAmountException("Amount must be greater than zero");
		}

		if (amount > sourceAccount.getBalance()) {
		    throw new InvalidTransactionalAmountException("Insufficient Balance");
		}


		// balance before transax -> sourceAcc
		Double openingBalanceofSourceAcc = sourceAccount.getBalance(); // 70000

		// balance before transx -> targetAcc

		Transaction transaction = new Transaction();

		transaction.setSourceAccount(sourceAccount);
		transaction.setTargetAccount(targetAccount);
		transaction.setAmount(amount);
		transaction.setOpeningBalance(openingBalanceofSourceAcc);
		transaction.setTransaction_type(Transaction_Type.TRANSFER);
		transaction.setClosingBalance(openingBalanceofSourceAcc);

		transaction.setReferenceId(generateTransactionReference());
		transaction.setTransaction_Status(Transaction_Status.PENDING);

		Transaction savedTransaction = transactionalRepository.save(transaction);

		SecurityPinResponseDTO responseDTO = mapper.map(savedTransaction, SecurityPinResponseDTO.class);
		responseDTO.setTransactionReference(savedTransaction.getReferenceId());
		responseDTO.setMessage(PIN_MSG);

		return responseDTO;
	}

	@Override
	@Transactional
	public TransactionResponseDTO confirmTransferAmount(SecurityPinValidateDTO pinCheckDTO) {

		Transaction transaction = transactionalRepository
				.findByReferenceIdWithLock(pinCheckDTO.getTransactionReference());

		if (transaction == null) {
			throw new FalseTransactionException("transaction is not initiated");
		}

		if (transaction.getTransaction_Status() != Transaction_Status.PENDING) {
			throw new RuntimeException("Transaction already processed");
		}

		String srcNo = transaction.getSourceAccount().getAccountNumber(); 
		String tgtNo = transaction.getTargetAccount().getAccountNumber(); 

		Account sourceAccount;
		Account targetAccount;

		if (srcNo.compareTo(tgtNo) < 0) {
			sourceAccount = accountRepository.findByAccountNumberWithLock(srcNo);
			targetAccount = accountRepository.findByAccountNumberWithLock(tgtNo);
		} else {
			sourceAccount = accountRepository.findByAccountNumberWithLock(tgtNo);
			targetAccount = accountRepository.findByAccountNumberWithLock(srcNo);
		}

		
	/*	Account sourceAccount = accountRepository
				.findByAccountNumberWithLock(transaction.getSourceAccount().getAccountNumber());

		Account targetAccount = accountRepository
				.findByAccountNumberWithLock(transaction.getTargetAccount().getAccountNumber());
    */		

		if (!pinCheckService.validateSecurityPin(sourceAccount, pinCheckDTO)) {
			transaction.setTransaction_Status(Transaction_Status.FAILED);
			transactionalRepository.save(transaction);
			throw new InvalidPinOrReferenceIDException("Invalid pin or reference");
		}

		Double balance = sourceAccount.getBalance(); // 70000
		Double amount = transaction.getAmount(); // 20000

		if (amount > balance || amount < 0) {
			transaction.setTransaction_Status(Transaction_Status.FAILED);
			transactionalRepository.save(transaction);
			throw new InvalidTransactionalAmountException("Insufficient Balance");
		}

		Double closingBalance = round(balance - amount);
		Double targetAccountClosingBalance = round(targetAccount.getBalance() + amount);

		sourceAccount.setBalance(closingBalance); // 70000 - 20000 = 50000
		targetAccount.setBalance(targetAccountClosingBalance); // 20000 + 20000 = 40000

		accountRepository.save(sourceAccount);
		accountRepository.save(targetAccount);

		transaction.setClosingBalance(closingBalance);
		transaction.setTransaction_Status(Transaction_Status.SUCCESS);

		Transaction savedTrnx = transactionalRepository.save(transaction);

		TransactionResponseDTO trnxDto = mapper.map(savedTrnx, TransactionResponseDTO.class);

		return trnxDto;

	}
	

	@Override
	public List<TransactionResponseDTO> getTransactionHistory(String accountNumber, LocalDate fromDate,
			LocalDate toDate) {
		
		Account account = accountRepository.findByAccountNumber(accountNumber);
		
		if(account == null) {
			throw new AccountNotFoundException("No account is found with this " + accountNumber);
		}
		
		for(Transaction transaction : account.getIncomingTransactions()) {
			
		}
		
		
		
		return null;
	}

	@Override
	public TransactionResponseDTO getTransactionByReference(String transactionReference) {
		// TODO Auto-generated method stub
		return null;
	}

}
