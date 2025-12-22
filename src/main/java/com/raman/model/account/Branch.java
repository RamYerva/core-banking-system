package com.raman.model.account;

public enum Branch {

	
	HYDERABAD("SINDH1012HYD"),
	VIJAYAWADA("SINDH1112VIJ"),
	CHENNAI("SINDH2102CHE"),
	BANGALORE("SINDH2712BAN");
	
	private final String ifscCode;

	private Branch(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	
	public String getIfscCode() {
        return ifscCode;
    }
}
