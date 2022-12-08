package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;

public class StockExchangeParam {
    private String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private String warehouseId = UserManager.getInstance().getUserData().getShopId();
    private int page = 1;
    private int pageSize = 100;
    private String skuId;//商品ID
    private String transType = "REC_NORM";
    private String docNo;

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

}
