package com.midasit.bungae.errorcode;

public enum ServerErrorCode {
    NOT_EQUAL_WRITER(610),
    NOT_EQUAL_PASSWORD(620),
    EMPTY_VALUE_OF_BOARD_CREATION(630),
    HAS_NO_USER(640),
    MAX_BOARD_OVERFLOW(650),
    DUPLICATION_USER_ID(660),
    ALREADY_PARTICIPANT_USER(670),
    MAX_PARTICIPANT_OVERFLOW(680),
    NO_PARTICIPANT(690);

    private int value;

    ServerErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
