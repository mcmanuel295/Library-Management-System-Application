package com.mcmanuel.exception;

public class BookNotShareableOrAvailableException extends RuntimeException {
    public BookNotShareableOrAvailableException(String message) {
        super(message);
    }
}
