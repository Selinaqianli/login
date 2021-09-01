package com.qianli.login.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoginCredentialException extends RuntimeException{
    public InvalidLoginCredentialException(final String message) {
        super(message);
    }
}
