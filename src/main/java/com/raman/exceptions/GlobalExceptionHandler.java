package com.raman.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
	
	
	@ExceptionHandler(AdminCreationEcxception.class)
    public String handleAdminCreationException(AdminCreationEcxception exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String resourseNotFoundException(ResourceNotFoundException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public String CustomerNotFoundException(CustomerNotFoundException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(AccountAlreadyExistsException.class)
	public String AccountAlreadyExistsException(AccountAlreadyExistsException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(IncompleteKYCException.class)
	public String IncompleteKYCException(IncompleteKYCException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public String UserNotFoundException(UserNotFoundException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(KYCRecordNotFoundException.class)
	public String KYCRecordNotFoundException(KYCRecordNotFoundException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(KYCNotApprovedException.class)
	public String KYCNotApprovedException(KYCNotApprovedException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(CustomerAlreadyVerifiedException.class)
	public String CustomerAlreadyVerifiedException(CustomerAlreadyVerifiedException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(InvalidAdminDecisionException.class)
	public String InvalidAdminDecisionException(InvalidAdminDecisionException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	public String AccountNotFoundException(AccountNotFoundException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(InvalidAccountStatusException.class)
	public String InvalidAccountStatusException(InvalidAccountStatusException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(IvalidTransactionalAmountException.class)
	public String IvalidTransactionalAmountException(IvalidTransactionalAmountException exception){
		return exception.getMessage();
	}
	
	@ExceptionHandler(InvalidPinOrReferenceIDException.class)
	public String InvalidPinOrReferenceIDException(InvalidPinOrReferenceIDException exception){
		return exception.getMessage();
	}
	
}
