package com.jd.saas.pdacheck.flow.step3.enums;

/**
 * 复盘核对列表 明细数量差异的类型
 *
 * @author gouhetong
 */
public enum CheckReviewQtyType {

    /** 盘亏 */
    LOSS(-1),
    /** 盘盈 */
    OVER(1),
    /** 无差异 */
    EQUAL(0),
    /** 有差异，不为0的 */
    NOT_EQUAL(2);

    private final int value;

    CheckReviewQtyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
