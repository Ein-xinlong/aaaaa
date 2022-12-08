package com.jd.saas.pdacommon.enums;

public enum AppType {
    STORE(1),
    WAREHOUSE(2);

    private final int value;

    AppType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
