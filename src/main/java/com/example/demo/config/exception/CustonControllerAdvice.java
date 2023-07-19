package com.example.demo.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustonControllerAdvice {
    @ExceptionHandler(LoginFailExceltion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailExceptionHandler(LoginFailExceltion e){
        return e.getMessage();
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String duplicateEmailExceptionHandler(DuplicateEmailException e){
        return e.getMessage();
    }
}
