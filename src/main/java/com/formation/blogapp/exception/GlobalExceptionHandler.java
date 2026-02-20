package com.formation.blogapp.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(ApiException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                ex.getHttpStatus(),
                ex.getMessage()
        );

        problem.setTitle(ex.getClass().getSimpleName());
        problem.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(ex.getHttpStatus()).body(problem);
    }


}
