package com.raman.service;

import com.raman.admin.AdminCheckDTO;

public interface AdminApprovalService {
	
	
	public AdminCheckDTO approveCustomer(Long id,AdminCheckDTO approvalDTO);
	
	

}
