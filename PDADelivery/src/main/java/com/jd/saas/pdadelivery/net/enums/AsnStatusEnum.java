package com.jd.saas.pdadelivery.net.enums;

public enum AsnStatusEnum {

    /**
     * 待收货
     */
    INITIAL(0),
    /**
     * 收货中
     */
    PART_RECEIVE(10),
    /**
     * 收货完成
     */
    RECEIVED(20),
    /**
     * 已取消
     */
    CANCEL_CLOSED(30),

    /**
     * 差异处理中
     */
    DIFF_AUDIT(40);

    AsnStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private final int value;

}
