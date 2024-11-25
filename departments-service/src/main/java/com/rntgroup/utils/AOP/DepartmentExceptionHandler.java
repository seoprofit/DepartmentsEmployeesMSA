package com.rntgroup.utils.AOP;


import com.rntgroup.service.exception.ChiefDepartmentAlreadyExistsException;
import com.rntgroup.service.exception.DepartmentAlreadyExistsException;
import com.rntgroup.service.exception.DepartmentAlreadyHasChiefException;
import com.rntgroup.service.exception.DepartmentHasNotHireEmployees;
import com.rntgroup.service.exception.IdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DepartmentExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DepartmentExceptionHandler.class);

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String noSuchDepartment(IdNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String departmentAlreadyExists(DepartmentAlreadyExistsException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DepartmentHasNotHireEmployees.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String departmentHasNotHireEmployees(DepartmentHasNotHireEmployees e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DepartmentAlreadyHasChiefException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String departmentAlreadyHasChiefException(DepartmentAlreadyHasChiefException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(ChiefDepartmentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String chiefDepartmentAlreadyExistsException(ChiefDepartmentAlreadyExistsException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
