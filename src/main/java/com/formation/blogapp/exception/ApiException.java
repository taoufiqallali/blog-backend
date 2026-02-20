package com.formation.blogapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiException extends RuntimeException{

    private final HttpStatus httpStatus;

    protected ApiException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }

}

