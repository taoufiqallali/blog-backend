package com.formation.blogapp.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException{

    public UserNotFoundException(Long id){
        super(HttpStatus.NOT_FOUND, "user with id " + id + " doesn't exist");
    }

    public UserNotFoundException(String email){
        super(HttpStatus.NOT_FOUND, "user with email " + email + " doesn't exist");
    }

}
