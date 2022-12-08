package com.jd.saas.pdagoodsquery.sale.model;

/**
 * 单据详情-请求参数
 * {
 *     "data":{
 *         "tenantId":100001601,
 *         "storeId":10014604,
 *         "skuId":"10000000015402",
 *         "startTime":"2021-08-01",
 *         "endTime":"2021-08-20"
 *     }
 * }
 */
public class GoodsQueryReceiptRequest {

        private String tenantId;
        private String storeId;
        private String skuId;
        private String startTime;
        private String endTime;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getSkuId(String skuId) {
        return this.skuId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
