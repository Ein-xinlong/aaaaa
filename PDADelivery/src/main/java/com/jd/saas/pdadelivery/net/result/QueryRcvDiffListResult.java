package com.jd.saas.pdadelivery.net.result;

import java.math.BigDecimal;

public class QueryRcvDiffListResult {
    /**
     * 入库明细id
     */
    private Long asnDetailId;

    /**
     * 差异类型 1-缺收 2-超收
     */
    private Integer diffType;

    /**
     * 原因编码
     */
    private String reasonCode;

    /**
     * 计划收货数量
     */
    private BigDecimal expectedQty;

    /**
     * 实际收货数量
     */
    private BigDecimal actualQty;

    /**
     * 原因编码描述
     */
    private String reasonDesc;

    /**
     * 差异数量
     */
    private BigDecimal diffQty;

    /**
     * 商品条码，多个时用分号分割
     */
    private String upcCodes;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 商品skuId
     */
    private String skuId;

    /**
     * 商品单位
     */
    private String saleUnitName;
    /**
     * 商品logo
     */
    private String logo;

    public Long getAsnDetailId() {
        return asnDetailId;
    }

    public void setAsnDetailId(Long asnDetailId) {
        this.asnDetailId = asnDetailId;
    }

    public Integer getDiffType() {
        return diffType;
    }

    public void setDiffType(Integer diffType) {
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

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
