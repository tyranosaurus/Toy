package com.midasit.bungae.exception;

public class AlreadyJoinUserException extends RuntimeException {
    public AlreadyJoinUserException() {
        super();
    }

    public AlreadyJoinUserException(String message) {
        super(message);
    }

    public AlreadyJoinUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
