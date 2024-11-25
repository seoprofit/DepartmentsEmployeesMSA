package com.rntgroup.service.exception;

public class WrongDateOfEmployeeException extends RuntimeException {
    public WrongDateOfEmployeeException(String error) {
        super(error);
    }
}
