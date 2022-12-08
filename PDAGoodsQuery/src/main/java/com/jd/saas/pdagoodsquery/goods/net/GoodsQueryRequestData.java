package com.jd.saas.pdagoodsquery.goods.net;

public class GoodsQueryRequestData {
    private String storeId;
    private String condition;
    private String pin;
    private String page;
    private String pageSize;

    public GoodsQueryRequestData() {
    }

    public GoodsQueryRequestData(String storeId, String condition, String pin, String page, String pageSize) {
        this.storeId = storeId;
        this.condition = condition;
        this.pin = pin;
        this.page = page;
        this.pageSize = pageSize;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getCondition() {
        return condition;
    }

    public String getPin() {
        return pin;
    }

    public String getPage() {
        return page;
    }

    public String getPageSize() {
        return pageSize;
    }
}
