package com.formation.blogapp.exception;

import org.springframework.http.HttpStatus;

public class PasswordSameAsOldException extends ApiException{
    public PasswordSameAsOldException() {
        super(HttpStatus.BAD_REQUEST, "The new password should not be the same as the old one");
    }
}
