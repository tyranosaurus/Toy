package com.midasit.bungae.exception;

public class AlreadyParticipantException extends RuntimeException {
    public AlreadyParticipantException() {
        super();
    }

    public AlreadyParticipantException(String message) {
        super(message);
    }

    public AlreadyParticipantException(String message, Throwable cause) {
        super(message, cause);
    }
}
