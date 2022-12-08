package com.jd.saas.pdacheck.flow.step3.bean;

import java.math.BigDecimal;

public class CheckReviewSubmitOrderParam {
    private String tenantId;
    private String whId;
    private String taskNo;
    private String couHNo;
    private int id;
    private String skuId;
    private String locNo;
    private BigDecimal actualQty;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getCouHNo() {
        return couHNo;
    }

    public void setCouHNo(String couHNo) {
        this.couHNo = couHNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }
}
