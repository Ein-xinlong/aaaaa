package com.jd.saas.pdadelivery.net.enums;

public enum SkuNatureEnum {
    GOOD(0),
    BAD(1);
    private final int value;

    SkuNatureEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
