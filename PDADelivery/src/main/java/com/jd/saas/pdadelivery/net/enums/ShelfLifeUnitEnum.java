package com.jd.saas.pdadelivery.net.enums;

public enum ShelfLifeUnitEnum {
    DAY(1, "天"),
    MONTH(2, "月"),
    YEAR(3, "年"),
    HOUR(4, "小时");

    private final int value;
    private final String desc;

    ShelfLifeUnitEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ShelfLifeUnitEnum fromValue(int value) {
        if (value == MONTH.value) {
            return MONTH;
        } else if (value == YEAR.value) {
            return YEAR;
        } else if (value == HOUR.value) {
            return HOUR;
        } else {
            return DAY;
        }
    }
}
