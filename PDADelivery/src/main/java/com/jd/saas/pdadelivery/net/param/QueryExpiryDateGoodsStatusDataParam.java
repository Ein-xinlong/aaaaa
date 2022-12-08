package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;

public class QueryExpiryDateGoodsStatusDataParam {
    private String storeId = UserManager.getInstance().getUserData().getShopId();
    private String skuId;
    private Long produceDate;

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

    public Long getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Long produceDate) {
        this.produceDate = produceDate;
    }
}
