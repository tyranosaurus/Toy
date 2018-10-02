package com.midasit.bungae.exception;

public class EmptyValueOfNoticeCreationException extends RuntimeException {
    public EmptyValueOfNoticeCreationException() {
        super();
    }

    public EmptyValueOfNoticeCreationException(String message) {
        super(message);
    }

    public EmptyValueOfNoticeCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
