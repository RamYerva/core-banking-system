package com.raman.exceptions;


public class CustomerAlreadyVerifiedException extends RuntimeException {
         public CustomerAlreadyVerifiedException(String msg) {
        	 super(msg);
         }
}
