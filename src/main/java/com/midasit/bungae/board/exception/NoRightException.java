package com.midasit.bungae.board.exception;

public class NoRightException extends RuntimeException {
    public NoRightException() {
        super();
    }

    public NoRightException(String message) {
        super(message);
    }

    public NoRightException(String message, Throwable cause) {
        super(message, cause);
    }
}
