package com.raman.exceptions;

public class IncompleteKYCException extends RuntimeException {
    public IncompleteKYCException(String message) {
        super(message);
    }
}