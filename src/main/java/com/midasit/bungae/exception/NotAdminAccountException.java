package com.midasit.bungae.exception;

public class NotAdminAccountException extends RuntimeException {
    public NotAdminAccountException() {
        super();
    }

    public NotAdminAccountException(String message) {
        super(message);
    }

    public NotAdminAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
