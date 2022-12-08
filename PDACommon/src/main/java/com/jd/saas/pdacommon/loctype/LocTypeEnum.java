package com.jd.saas.pdacommon.loctype;

/**
 * 所有好/坏库位枚举
 * @author majiheng
 */
public enum LocTypeEnum {
    /**
     * 商品好品库位 "TOP"
     */
    SKU("TOP"),
    /**
     * 商品坏品库位 "DM"
     */
    SKU_BAD("DM"),

    /**
     * 原材料库位 "DRM"
     */
    RAW("DRM"),

    /**
     * 原材料坏品库位 "RD"
     */
    RAW_BAD("RD"),

    /**
     * 耗材库位 "HC"
     */
    HC("HC"),

    /**
     * 耗材坏品库位 "HCD"
     */
    HC_BAD("HCD");


    private final String value;

    LocTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
