package com.jd.saas.pdadelivery.net.enums;

public enum AsnTypeEnum {
    /**
     * 本地属性 全部
     */
    ALL(-1),
    /**
     * 采购单
     */
    PURCHASE_ORDER(1),
    /**
     * 用户退货单
     */
    USER_RETURNS(2),
    /**
     * 配送拒收单
     */
    DELIVERY_REJECT(3),
    /**
     * 期初入库单
     */
    INITIALLY(0),
    /**
     * 配送
     */
    DISTRIBUTION(4),
    /**
     * 调拨单入
     */
    ALLOTIN(5),
    /**
     * 收货差异入
     */
    INCOME_DIFFERENCE(6),
    /**
     * 门店退仓入
     */
    RETURN_DC(7);

    AsnTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private final int value;
}
