package com.midasit.bungae.exception;

public class NoRightOfModifyAndDeleteException extends RuntimeException {
    public NoRightOfModifyAndDeleteException() {
        super();
    }

    public NoRightOfModifyAndDeleteException(String message) {
        super(message);
    }

    public NoRightOfModifyAndDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
