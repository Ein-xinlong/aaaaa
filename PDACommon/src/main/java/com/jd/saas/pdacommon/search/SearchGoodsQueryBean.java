package com.jd.saas.pdacommon.search;

/**
 * 商品查询request bean
 *
 * @author majiheng
 */
public class SearchGoodsQueryBean {

    // 门店id
    private String storeId;
    // 搜索内容
    private String condition;
    // 用户pin
    private String pin;
    // 当前分页
    private String page;
    // 分页一页x条
    private String pageSize;
    // 好坏品
    private String skuNature;
    // 商品skuId
    private String skuId;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuNature() {
        return skuNature;
    }

    public void setSkuNature(String skuNature) {
        this.skuNature = skuNature;
    }

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

}
