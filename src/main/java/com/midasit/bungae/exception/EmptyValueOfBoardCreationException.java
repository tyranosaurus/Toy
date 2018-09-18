package com.midasit.bungae.exception;

public class EmptyValueOfBoardCreationException extends RuntimeException {
    public EmptyValueOfBoardCreationException() {
        super();
    }

    public EmptyValueOfBoardCreationException(String message) {
        super(message);
    }

    public EmptyValueOfBoardCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
