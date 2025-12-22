package com.raman.model.account;

public enum AccountType {
	
	SAVINGS("010"),
	BUSINESS("021"),
	CURRENT("012"),
	FIXED_DEPOSIT("120");
	
	
	private final String schemeCode;
	

	private AccountType(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getSchemeCode() {
		return schemeCode;
	}


}
