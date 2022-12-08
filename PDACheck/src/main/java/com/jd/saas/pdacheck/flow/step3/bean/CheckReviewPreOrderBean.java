package com.jd.saas.pdacheck.flow.step3.bean;

import com.jd.saas.pdacheck.util.CheckFormatter;

import java.math.BigDecimal;

/**
 * 预盘单的数据bean
 *
 * @author gouhetong
 */
public class CheckReviewPreOrderBean {
    private String id;
    private String createDate;
    private String tenantId;
    private String whId;
    private String taskNo;
    private String skuId;
    private String couHNo;
    private String locNo;
    private String qty;
    private BigDecimal actualQty = BigDecimal.ZERO;
    private String operator;

    //本地属性 输入的盘点数量
    private BigDecimal inputQty = BigDecimal.ZERO;

    public String getInputQtyStr() {
        return CheckFormatter.format(inputQty);
    }

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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getCouHNo() {
        return couHNo;
    }

    public void setCouHNo(String couHNo) {
        this.couHNo = couHNo;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getInputQty() {
        return inputQty;
    }

    public void setInputQty(BigDecimal inputQty) {
        this.inputQty = inputQty;
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }
}
