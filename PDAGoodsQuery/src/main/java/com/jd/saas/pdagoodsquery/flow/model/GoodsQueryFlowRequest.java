package com.jd.saas.pdagoodsquery.flow.model;

public class GoodsQueryFlowRequest {
    private String warehouseId;//[:LONG]仓库编号，即门店ID，必填
    private String tenantId;//[:LONG]租户ID，必填
    private String skuId;//商品ID
    private String pageNo;
    private String pageSize;
    private String startTime;//[:DATE]创建开始时间
    private String endTime;//[:DATE]创建结束时间
    private String transType;
    private String transChildType;

    public void setTransType(String galBizType) {
        this.transType = galBizType;
    }

    public void setTransChildType(String transChildType) {
        this.transChildType = transChildType;
    }

    public String getTransChildType() {
        return transChildType;
    }

    public String getTransType() {
        return transType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setPageNo(String page) {
        this.pageNo = page;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getSkuId() {
        return skuId;
    }

    public String getPageNo() {
        return pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }
}
