package com.himax.ead.authuser.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ChildNotFoundException extends RuntimeException{

    public ChildNotFoundException(String message) {
        super(message);
    }
}
