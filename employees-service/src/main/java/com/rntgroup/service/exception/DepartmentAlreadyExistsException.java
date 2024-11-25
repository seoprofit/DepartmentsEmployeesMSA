package com.rntgroup.service.exception;

public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String error) {
        super(error);
    }
}
