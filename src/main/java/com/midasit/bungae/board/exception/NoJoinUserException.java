package com.midasit.bungae.board.exception;

public class NoJoinUserException extends RuntimeException {
    public NoJoinUserException() {
        super();
    }

    public NoJoinUserException(String message) {
        super(message);
    }

    public NoJoinUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
