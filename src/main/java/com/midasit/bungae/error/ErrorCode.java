package com.midasit.bungae.error;

public enum ErrorCode {
    NOT_EQUAL_WRITER(610),
    NOT_EQUAL_PASSWORD(620),
    EMPTY_VALUE_OF_BOARD_CREATION(630),
    HAS_NO_USER(640),
    SESSION_HAS_NO_USER_INFO(650);

    private int value;

    ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
