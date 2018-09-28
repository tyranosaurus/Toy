package com.midasit.bungae.exception;

public class EmptyValueOfUserJoinException extends RuntimeException {
    public EmptyValueOfUserJoinException() {
        super();
    }

    public EmptyValueOfUserJoinException(String message) {
        super(message);
    }

    public EmptyValueOfUserJoinException(String message, Throwable cause) {
        super(message, cause);
    }
}
