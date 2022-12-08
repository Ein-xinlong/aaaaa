package com.jd.saas.pdadelivery.net.param;

import java.math.BigDecimal;

public class DiffInfoParam {
    private long asnDetailId;
    private int diffType;
    private String reasonCode;
    private BigDecimal expectedQty;
    private BigDecimal actualQty;
    private String reasonDesc;
    private BigDecimal diffQty;
    private String upcCodes;
    private String skuName;
    private String skuId;

    public long getAsnDetailId() {
        return asnDetailId;
    }

    public void setAsnDetailId(long asnDetailId) {
        this.asnDetailId = asnDetailId;
    }

    public int getDiffType() {
        return diffType;
    }

    public void setDiffType(int diffType) {
        this.diffType = diffType;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public BigDecimal getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(BigDecimal expectedQty) {
        this.expectedQty = expectedQty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public BigDecimal getDiffQty() {
        return diffQty;
    }

    public void setDiffQty(BigDecimal diffQty) {
        this.diffQty = diffQty;
    }

    public String getUpcCodes() {
        return upcCodes;
    }

    public void setUpcCodes(String upcCodes) {
        this.upcCodes = upcCodes;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
