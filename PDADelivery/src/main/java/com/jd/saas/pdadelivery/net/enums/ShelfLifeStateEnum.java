package com.jd.saas.pdadelivery.net.enums;

/**
 * 0全部1正常2提示3预警4失效
 */
public enum ShelfLifeStateEnum {
    ALL(0),
    NORMAL(1),
    TIP(2),
    WARN(3),
    EXPIRE(4);
    private final int value;

    ShelfLifeStateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
