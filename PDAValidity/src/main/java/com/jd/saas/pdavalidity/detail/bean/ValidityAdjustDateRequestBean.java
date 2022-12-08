package com.jd.saas.pdavalidity.detail.bean;

/**
 * 根据sku&生产日期查询商品效期状态 的请求体
 *
 * @author majiheng
 */
public class ValidityAdjustDateRequestBean {

    private String storeId;
    private String skuId;
    // 生产日期
    private String produceDate;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }
}
