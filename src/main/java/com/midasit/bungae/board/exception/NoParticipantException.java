package com.midasit.bungae.board.exception;

public class NoParticipantException extends RuntimeException {
    public NoParticipantException() {
        super();
    }

    public NoParticipantException(String message) {
        super(message);
    }

    public NoParticipantException(String message, Throwable cause) {
        super(message, cause);
    }
}
