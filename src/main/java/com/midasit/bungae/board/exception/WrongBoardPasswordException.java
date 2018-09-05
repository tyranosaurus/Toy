package com.midasit.bungae.board.exception;

public class WrongBoardPasswordException extends RuntimeException {
    public WrongBoardPasswordException(String message) {
        super(message);
    }

    public WrongBoardPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
