package com.mcmanuel;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail bookNotFoundExceptionHandler(Exception ex){
        ProblemDetail problemDetail= ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
        problemDetail.setProperty("Timestamp", Instant.now());
        return problemDetail;
    }
}
