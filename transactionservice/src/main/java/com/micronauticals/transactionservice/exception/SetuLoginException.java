package com.micronauticals.transactionservice.exception;

public class SetuLoginException extends RuntimeException {
    public SetuLoginException(String message) {
        super(message);
    }

    public SetuLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}