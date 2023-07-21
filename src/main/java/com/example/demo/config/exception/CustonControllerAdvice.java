package com.example.demo.config.exception;

import com.example.demo.config.domain.response.ErrorRespone;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustonControllerAdvice {


    @ExceptionHandler(EmptyTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespone emptyTokenExceptionHandler(EmptyTokenException e){
        return new ErrorRespone(e.getMessage(),e.getCause());
    }


    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespone loginFailExceptionHandler(LoginFailException e){
        return  new ErrorRespone(e.getMessage(), e.getCause());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespone duplicateEmailExceptionHandler(DuplicateEmailException e){
        return  new ErrorRespone(e.getMessage(), e.getCause());
    }

    @ExceptionHandler(WeakKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespone WeakKeyExceptionHandler(WeakKeyException e){
        return  new ErrorRespone("Tampered Token", e.getCause());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorRespone ExpiredJwtExceptionHandler(ExpiredJwtException e){
        return  new ErrorRespone("TimeOut Token", e.getCause());
    }


}
