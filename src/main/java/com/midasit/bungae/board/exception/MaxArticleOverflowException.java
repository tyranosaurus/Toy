package com.midasit.bungae.board.exception;

public class MaxArticleOverflowException extends RuntimeException {
    public MaxArticleOverflowException(String message) {
        super(message);
    }

    public MaxArticleOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
