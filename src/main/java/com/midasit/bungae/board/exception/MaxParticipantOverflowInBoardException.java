package com.midasit.bungae.board.exception;

public class MaxParticipantOverflowInBoardException extends RuntimeException {
    public MaxParticipantOverflowInBoardException() {
        super();
    }

    public MaxParticipantOverflowInBoardException(String message) {
        super(message);
    }

    public MaxParticipantOverflowInBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
