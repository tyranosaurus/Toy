package com.midasit.bungae.board.exception;

public class MaxUserOverflowInBoardException extends RuntimeException {
    public MaxUserOverflowInBoardException() {
        super();
    }

    public MaxUserOverflowInBoardException(String message) {
        super(message);
    }

    public MaxUserOverflowInBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
