package com.formation.blogapp.exception;


import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApiException{

    public EmailAlreadyExistsException(String email){
        super(HttpStatus.CONFLICT, "User with email: " + email + " already exists");

    }

}
