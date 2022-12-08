package com.jd.saas.pdacheck.flow.step3.enums;

import java.io.Serializable;

/**
 * 复盘核对列表 排序标志的类型
 *
 * @author gouhetong
 */
public enum CheckReviewSortFlagType implements Serializable {

    /** 降序 */
    DESC(1),
    /** 升序 */
    ASC(2);


    private final int value;

    CheckReviewSortFlagType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
