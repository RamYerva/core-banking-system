package com.raman.admin;

import com.raman.model.customer.KYCStatus;

public class AdminCheckDTO {
	
	
	private KYCStatus status;
	private String remarks;
	
	public KYCStatus getStatus() {
		return status;
	}
	public void setStatus(KYCStatus status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
