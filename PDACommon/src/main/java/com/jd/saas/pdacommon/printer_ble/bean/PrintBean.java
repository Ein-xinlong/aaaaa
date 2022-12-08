package com.jd.saas.pdacommon.printer_ble.bean;

public class PrintBean {
    private String skuName;//商品名称
    private String skuDate;//商品日期
    private String skuPrice;//商品价格
    private String skuCode;//商品条码
    private String skuFormat;//商品条码

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDate() {
        return skuDate;
    }

    public void setSkuDate(String skuDate) {
        this.skuDate = skuDate;
    }

    public String getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(String skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuFormat() {
        return skuFormat;
    }

    public void setSkuFormat(String skuFormat) {
        this.skuFormat = skuFormat;
    }
}
