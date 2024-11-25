package com.rntgroup.service.exception;

public class DepartmentHasNotHireEmployees extends RuntimeException {
    public DepartmentHasNotHireEmployees(String error) {
        super(error);
    }
}
