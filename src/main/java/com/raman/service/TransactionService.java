package com.raman.service;



import java.time.LocalDate;
import java.util.List;

import com.raman.dto.SecurityPinResponseDTO;
import com.raman.dto.SecurityPinValidateDTO;
import com.raman.dto.TransactionRequestDTO;
import com.raman.dto.TransactionResponseDTO;

public interface TransactionService {
	
	//deposit
	public TransactionResponseDTO depositAmount(TransactionRequestDTO requestDTO);
	
	//withdraw
	public SecurityPinResponseDTO initiateWithdrawAmount(TransactionRequestDTO requestDTO);
	
	public TransactionResponseDTO confirmWithdrawAmount(SecurityPinValidateDTO pinCheckDTO);
	
	//transfer
	public SecurityPinResponseDTO initiateTransferAmount(TransactionRequestDTO requestDTO);
	
	public TransactionResponseDTO confirmTransferAmount(SecurityPinValidateDTO pinCheckDTO);
	
	
	//transx_history
	List<TransactionResponseDTO> getTransactionHistory(
            String accountNumber, LocalDate fromDate, LocalDate toDate);
	
	public TransactionResponseDTO getTransactionByReference(String transactionReference);
	

}