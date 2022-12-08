package com.jd.saas.pdagoodsquery.sale.model;

public class GoodsQuerySaleRequest {
    //{
    //"data":{
    //"tenantId":101,
    //"storeId":111127,
    //"skuId":102412,
    //"dateType":"day",
    //"startTime":"2021-07-21",
    //"endTime":"2021-07-21"
    //}
    //}
    private String tenantId;
    private String storeId;
    private String skuId;
    private String dateType;
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

    public void setDateType(String dateType) {
        this.dateType = dateType;
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

    public String getSkuId() {
        return skuId;
    }

    public String getDateType() {
        return dateType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
