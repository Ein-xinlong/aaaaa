package com.jd.saas.pdacommon.loctype;

/**
 * 好坏库位类型，0：好 1：坏
 * @author majiheng
 */
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
