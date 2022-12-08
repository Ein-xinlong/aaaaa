package com.jd.saas.pdadelivery.net.result;

import java.math.BigDecimal;

public class RcvStockInfoResult {

    private String asnHeaderNo;
    private Integer saleMode;
    private String upcCodes;
    private String purchasePrice;
    private int skuType;
    private BigDecimal actualQty;
    private Integer saleUnit;
    private String extSkuId;
    private Integer lineStatus;
    private String skuName;
    private String skuModelNo;
    private String uom;
    private Integer warehouseId;
    private String skuOrderNo;
    private Integer tenantId;
    private BigDecimal expectedQty;
    private Integer shelfLife;
    //天（day:1），月（month:2），年（year:3），小时（hour:4）
    private Integer shelfLifeUnit;
    private String skuId;
    private String asnRefNo;
    private String createDate;
    private String logo;
    private boolean isShelfLifeSup;
    /**
     * 最大收货数量
     */
    private BigDecimal upperLimitReceived;

    public String getAsnHeaderNo() {
        return asnHeaderNo;
    }

    public void setAsnHeaderNo(String asnHeaderNo) {
        this.asnHeaderNo = asnHeaderNo;
    }

    public Integer getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(Integer saleMode) {
        this.saleMode = saleMode;
    }

    public String getUpcCodes() {
        return upcCodes;
    }

    public void setUpcCodes(String upcCodes) {
        this.upcCodes = upcCodes;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getSkuType() {
        return skuType;
    }

    public void setSkuType(int skuType) {
        this.skuType = skuType;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public Integer getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(Integer saleUnit) {
        this.saleUnit = saleUnit;
    }

    public String getExtSkuId() {
        return extSkuId;
    }

    public void setExtSkuId(String extSkuId) {
        this.extSkuId = extSkuId;
    }

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuModelNo() {
        return skuModelNo;
    }

    public void setSkuModelNo(String skuModelNo) {
        this.skuModelNo = skuModelNo;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSkuOrderNo() {
        return skuOrderNo;
    }

    public void setSkuOrderNo(String skuOrderNo) {
        this.skuOrderNo = skuOrderNo;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public BigDecimal getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(BigDecimal expectedQty) {
        this.expectedQty = expectedQty;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Integer getShelfLifeUnit() {
        return shelfLifeUnit;
    }

    public void setShelfLifeUnit(Integer shelfLifeUnit) {
        this.shelfLifeUnit = shelfLifeUnit;
    }


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getAsnRefNo() {
        return asnRefNo;
    }

    public void setAsnRefNo(String asnRefNo) {
        this.asnRefNo = asnRefNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isShelfLifeSup() {
        return isShelfLifeSup;
    }

    public void setShelfLifeSup(boolean shelfLifeSup) {
        isShelfLifeSup = shelfLifeSup;
    }

    public BigDecimal getUpperLimitReceived() {
        return upperLimitReceived;
    }

    public void setUpperLimitReceived(BigDecimal upperLimitReceived) {
        this.upperLimitReceived = upperLimitReceived;
    }
}
