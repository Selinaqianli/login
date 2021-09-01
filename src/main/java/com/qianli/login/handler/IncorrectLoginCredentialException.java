package com.qianli.login.handler;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class IncorrectLoginCredentialException extends RuntimeException{
    public IncorrectLoginCredentialException(final String message) {
        super(message);
    }
}
