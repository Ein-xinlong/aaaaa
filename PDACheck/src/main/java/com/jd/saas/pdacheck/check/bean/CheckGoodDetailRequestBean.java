package com.jd.saas.pdacheck.check.bean;

/**
 * 获取商品详情入参bean
 *
 * @author majiheng
 */
public class CheckGoodDetailRequestBean {

    private String storeId;
    // condition对应的是upcCode
    private String condition;
    private String skuId;
    private String pin;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
