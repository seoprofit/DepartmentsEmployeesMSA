package com.rntgroup.service.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String error) {
        super(error);
    }
}