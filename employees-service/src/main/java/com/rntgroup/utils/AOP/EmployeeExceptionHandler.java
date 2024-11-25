package com.rntgroup.utils.AOP;


import com.rntgroup.service.exception.IdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class EmployeeExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(EmployeeExceptionHandler.class);

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseBody
    public String noSuchEmployee(IdNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
