package com.jd.saas.pdacheck.flow.step2.bean;

/**
 * 漏盘商品的销售类型
 */
public enum CheckMissedSkuSaleModeEnum {
    //计件
    PIECE(1),
    //称重
    WEIGHT(2);

    private final int value;

    CheckMissedSkuSaleModeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
