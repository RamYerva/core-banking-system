package com.raman.dto;

import com.raman.model.account.AccountType;
import com.raman.model.account.Branch;

import jakarta.validation.constraints.NotNull;

public class CreateAccountRequestDTO {
	
	@NotNull
	private AccountType accountType;
	@NotNull
	private Branch accBranch;
	

	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public Branch getAccBranch() {
		return accBranch;
	}
	public void setAccBranch(Branch accBranch) {
		this.accBranch = accBranch;
	}

}
