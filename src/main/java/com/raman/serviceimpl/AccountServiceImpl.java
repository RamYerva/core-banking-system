package com.raman.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raman.dto.AccountResponseDTO;
import com.raman.dto.CreateAccountRequestDTO;
import com.raman.exceptions.AccountAlreadyExistsException;
import com.raman.exceptions.AccountNotFoundException;
import com.raman.exceptions.CustomerNotFoundException;
import com.raman.exceptions.IncompleteKYCException;
import com.raman.model.account.Account;
import com.raman.model.account.AccountStatus;
import com.raman.model.account.AccountType;
import com.raman.model.account.Branch;
import com.raman.model.customer.Customer;
import com.raman.model.customer.KYCStatus;
import com.raman.repository.AccountRepository;
import com.raman.repository.CustomerRepository;
import com.raman.service.AccountService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final ModelMapper mapper;
	private final JdbcTemplate jdbcTemplate;

	public AccountServiceImpl(CustomerRepository customerRepository,
			 AccountRepository accountRepository,
			ModelMapper mapper, JdbcTemplate jdbcTemplate) {

		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.mapper = mapper;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	private static final long MULTIPLIER = 7919L;
	private static final long OFFSET = 104729L;

	

	@Override
	@Transactional
	public AccountResponseDTO createAccount(CreateAccountRequestDTO requestDTO) {

		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();

		Customer customer = customerRepository.findByUserEmail(loggedUser)
				.orElseThrow(() -> new CustomerNotFoundException("customer not found with this : " + loggedUser));

		// KYCStatus status =
		// kycVerificationRepository.findByStatus(KYCStatus.APPROVED);

		if (customer.getKycStatus() != KYCStatus.VERIFIED) {
			throw new IncompleteKYCException("Unauthorized: Your KYC status is " + customer.getKycStatus()
					+ ". You can only open accounts after Admin approval.");
		}

		Account account = new Account();

		/*
		 * if(account.getStatus() != AccountStatus.ACTIVE) { throw new
		 * RuntimeException("Unauthorized: Your Bank account is " + account.getStatus()
		 * + ". "); }
		 */

		if (accountRepository.existsByCustomerAndAccountType(customer, requestDTO.getAccountType())) {
			throw new AccountAlreadyExistsException("You already have a " + requestDTO.getAccountType() + " account.");
		}
		
		
		System.out.println("Selected Branch: " + requestDTO.getAccBranch());
		System.out.println("IFSC from Enum: " + requestDTO.getAccBranch().getIfscCode());

		account.setCustomer(customer);
		account.setAccountType(requestDTO.getAccountType());
		account.setBranch(requestDTO.getAccBranch());
		account.setIfscCode(requestDTO.getAccBranch().getIfscCode());

		account.setAccountNumber(accountNumberGenerator(requestDTO.getAccBranch(), requestDTO.getAccountType()));
		account.setBalance(0.0);
		account.setStatus(AccountStatus.ACTIVE);

		Account createdAccount = accountRepository.save(account);
		AccountResponseDTO responseDto = mapper.map(createdAccount, AccountResponseDTO.class);

		return responseDto;
	}

	private String accountNumberGenerator(Branch branch, AccountType accountType) {

		String branchCode = branch.getIfscCode().replaceAll("\\D+", "").substring(0, 4);
		String schemeCode = accountType.getSchemeCode();

		jdbcTemplate.execute("UPDATE cbs_acc_seq_table SET next_val = LAST_INSERT_ID(next_val + 1)");
		Long serialValue = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		String maskedSeq = maskSequence(serialValue);

		String base = branchCode + schemeCode + maskedSeq;
		int checkDigit = calculateModulo10(base);

		return base + checkDigit;
	}

	private int calculateModulo10(String str) {
		int sum = 0;
		for (char c : str.toCharArray()) {
			sum += Character.getNumericValue(c);
		}
		return sum % 10;
	}
	
	private String maskSequence(long seq) {
	    long scrambled = (seq ^ OFFSET) * MULTIPLIER;
	    long bounded = Math.abs(scrambled) % 10_000_000_000L; 
	    return String.format("%010d", bounded);
	}
	
	
	
	@Override
	public Double getAccountBalance(String accountNumber) {
		
		Account account = accountRepository.findByAccountNumber(accountNumber);
		
		if(account == null) {
			throw new AccountNotFoundException("No account is found with this " + accountNumber);
		}
		//based on acc-number, will get acc-info like,
		//acc-balance, transactions he did		
		return account.getBalance();
	}


}
