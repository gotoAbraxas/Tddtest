package com.example.demo.config.exception;

public class EmptyTokenException extends RuntimeException{
    public  EmptyTokenException(String message){
        super((message));
    }
}
