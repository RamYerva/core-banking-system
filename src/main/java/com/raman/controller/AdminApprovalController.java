package com.raman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raman.admin.AdminCheckDTO;
import com.raman.service.AdminApprovalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/admin/approvals")
@Tag(name = "Admin Approvals", description = "APIs for Admin checks")
public class AdminApprovalController {
	
	
	private final AdminApprovalService adminApprovalService;
	
	
	public AdminApprovalController(AdminApprovalService adminApprovalService) {
		this.adminApprovalService = adminApprovalService;
	}

     
	@PostMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	 @Operation(summary = "Admin Approvals", description = "admin verifies and aproves the KYC of Customers")
	public ResponseEntity<?> approveCustomer(@PathVariable Long id,  @RequestBody AdminCheckDTO approvalDTO){
		 AdminCheckDTO response =  adminApprovalService.approveCustomer(id, approvalDTO);
		 return ResponseEntity.ok(response);
	}

}
