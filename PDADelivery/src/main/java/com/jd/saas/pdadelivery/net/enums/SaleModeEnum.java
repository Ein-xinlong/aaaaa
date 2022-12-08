package com.jd.saas.pdadelivery.net.enums;

public enum SaleModeEnum {
    //计件
    PIECE(1),
    //称重
    WEIGHT(2);

    private final int value;

    SaleModeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
