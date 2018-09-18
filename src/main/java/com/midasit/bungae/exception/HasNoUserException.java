package com.midasit.bungae.exception;

public class HasNoUserException extends RuntimeException {
    public HasNoUserException() {
        super();
    }

    public HasNoUserException(String message) {
        super(message);
    }

    public HasNoUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
