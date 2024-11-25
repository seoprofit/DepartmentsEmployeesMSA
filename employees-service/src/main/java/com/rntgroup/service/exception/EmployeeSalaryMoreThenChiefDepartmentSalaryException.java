package com.rntgroup.service.exception;

public class EmployeeSalaryMoreThenChiefDepartmentSalaryException extends RuntimeException {
    public EmployeeSalaryMoreThenChiefDepartmentSalaryException(String error) {
        super(error);
    }
}
