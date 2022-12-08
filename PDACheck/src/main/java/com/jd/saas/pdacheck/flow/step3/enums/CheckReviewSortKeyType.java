package com.jd.saas.pdacheck.flow.step3.enums;

import java.io.Serializable;

/**
 * 复盘核对列表 排序方式的类型
 *
 * @author gouhetong
 */
public enum CheckReviewSortKeyType implements Serializable {

    /** 差异金额 */
    DIFF_AMOUNT(1),
    /** 差异数量 */
    DIFF_QTY(2);


    private final int value;

    CheckReviewSortKeyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

