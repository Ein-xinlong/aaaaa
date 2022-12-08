package com.jd.saas.pdagoodsquery.goods.bean;

public class GoodsQueryBean {

    String name;
    String skuID;
    String upcCode;
    String skuName;
    String logo;

    public void setName(String name) {
        this.name = name;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public String getSkuID() {
        return skuID;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public String getLogo() {
        return logo;
    }
}
