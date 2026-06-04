package com.mcmanuel.exception;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ProblemDetail UnhandledException(Exception ex) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        problemDetail.setTitle("internal error");
        problemDetail.setProperty("category", "book service");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }


    @ExceptionHandler(AccountLockedException.class)
    private ProblemDetail AccountLockedExceptionHandler(AccountLockedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NO_CONTENT, ex.getMessage());

        problemDetail.setTitle("Account Is Locked");
        problemDetail.setProperty("category", "catalog service");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail userNotFoundExceptionHandler(UserNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("timeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ProblemDetail bookNotFoundExceptionHandler(BookNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("timeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(BookNotShareableOrAvailableException.class)
    public ProblemDetail bookNotShareableOrAvailableExceptionHandler(BookNotShareableOrAvailableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("timeStamp", Instant.now());
        return problemDetail;
    }
}
