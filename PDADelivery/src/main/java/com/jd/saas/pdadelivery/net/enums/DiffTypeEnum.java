package com.jd.saas.pdadelivery.net.enums;

public enum DiffTypeEnum {
    NONE(0),
    LESS(1),
    MORE(2);

    private final int value;

    DiffTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
