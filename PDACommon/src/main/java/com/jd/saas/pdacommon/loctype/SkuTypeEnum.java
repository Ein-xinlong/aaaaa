package com.jd.saas.pdacommon.loctype;

/**
 * 普通商品：1、原材料：2、耗材：3
 * @author majiheng
 */
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
