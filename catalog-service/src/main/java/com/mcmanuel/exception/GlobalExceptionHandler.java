package com.mcmanuel.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
     private ProblemDetail UnhandledException(Exception ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        problemDetail.setTitle("internal error");
        problemDetail.setProperty("category", "book service");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }


    @ExceptionHandler(BookNotFoundException.class)
    private ProblemDetail BookNotFoundExceptionHandler(BookNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problemDetail.setTitle("Book not found");
        problemDetail.setProperty("category", "book service");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ProblemDetail MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        ArrayList<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach( (error) ->{
            errors.add( error.getDefaultMessage());
        });

        problemDetail.setTitle("Method argument not valid exception");
        problemDetail.setProperty("error",errors);
        problemDetail.setProperty("category", "book service");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }
}
