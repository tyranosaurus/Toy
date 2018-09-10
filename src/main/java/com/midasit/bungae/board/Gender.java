package com.midasit.bungae.board;

public enum Gender {
    MALE(0), FEMALE(1);

    private final int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Gender valueOf(int value) {
        switch(value) {
            case 0:
                return MALE;
            case 1:
                return FEMALE;
            default:
                throw new AssertionError("일치하는 성별이 없습니다.");
        }
    }
}
