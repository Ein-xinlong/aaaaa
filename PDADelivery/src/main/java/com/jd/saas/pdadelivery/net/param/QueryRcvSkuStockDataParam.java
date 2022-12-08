package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;

public class QueryRcvSkuStockDataParam {
    private String storeId = UserManager.getInstance().getUserData().getShopId();
    private String docNo;

    public QueryRcvSkuStockDataParam(String docNo) {
        this.docNo = docNo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }
}
