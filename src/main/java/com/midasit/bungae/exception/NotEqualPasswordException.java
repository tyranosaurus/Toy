package com.midasit.bungae.exception;

public class NotEqualPasswordException extends RuntimeException {
    public NotEqualPasswordException() {
        super();
    }

    public NotEqualPasswordException(String message) {
        super(message);
    }

    public NotEqualPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
