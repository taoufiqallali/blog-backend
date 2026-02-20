package com.formation.blogapp.exception;


import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ApiException{
    public CommentNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Comment with id: " + id + " not found ");
    }
}
