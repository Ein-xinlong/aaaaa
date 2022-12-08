package com.jd.saas.pdacheck.flow.step3.bean;

public class CheckReviewSkuResult {
    private String skuId;
    private String skuName;
    private String skuType;
    /** 单位 */
    private String uom;
    private String upcCodes;
    /**
     * 普通商品的本次收货库位类型id
     */
    private String locNo;
    /**
     * 普通商品的本次收货库位类型名称
     */
    private String locName;
    /**
     * 普通商品的本次收货库位类型的类型
     */
    private String locType;
    /**
     * 1计件 2称重
     */
    private int saleMode;
    /** 库存数量:与库存保持一致（盘点存在，预盘点的时候为空） */
    private String qty;
    /** 实盘数量 */
    private String actualQty;

    /** 差异数量 */
    private String diffQty;

    /** 仓报价 */
    private String inPrice;

    /** 差异金额 */
    private String diffAmount;
    private String operator;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUpcCodes() {
        return upcCodes;
    }

    public void setUpcCodes(String upcCodes) {
        this.upcCodes = upcCodes;
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getActualQty() {
        return actualQty;
    }

    public void setActualQty(String actualQty) {
        this.actualQty = actualQty;
    }

    public String getDiffQty() {
        return diffQty;
    }

    public void setDiffQty(String diffQty) {
        this.diffQty = diffQty;
    }

    public String getInPrice() {
        return inPrice;
    }

    public void setInPrice(String inPrice) {
        this.inPrice = inPrice;
    }

    public String getDiffAmount() {
        return diffAmount;
    }

    public void setDiffAmount(String diffAmount) {
        this.diffAmount = diffAmount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
