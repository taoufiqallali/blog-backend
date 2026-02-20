package com.formation.blogapp.exception;

import org.springframework.http.HttpStatus;

public class BlogPostNotFoundException extends ApiException{
    public BlogPostNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Blog post with id:" + id + " was not found");
    }
}
