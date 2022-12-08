package com.jd.saas.pdadelivery.net.enums;

public enum SkuTypeEnum {
    SKU(1),
    RAW(2),
    HC(3);
    private final int value;

    SkuTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
