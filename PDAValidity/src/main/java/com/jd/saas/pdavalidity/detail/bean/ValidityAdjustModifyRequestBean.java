package com.jd.saas.pdavalidity.detail.bean;

/**
 * 效期调整修改提交接口
 *
 * @author majiheng
 */
public class ValidityAdjustModifyRequestBean {

    public String storeId;
    private String skuId;
    private String upcCode;
    // 生产日期
    private String produceDate;
    // 失效日期
    private String expireDate;
    // 调整数量
    private String adjustQty;
    // 调整后的供应商编码
    private String supplierCode;
    // 待调整批次编号
    private String lotNo;
    // 待调整的库位
    private String locNo;
    private String pin;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getAdjustQty() {
        return adjustQty;
    }

    public void setAdjustQty(String adjustQty) {
        this.adjustQty = adjustQty;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
}
