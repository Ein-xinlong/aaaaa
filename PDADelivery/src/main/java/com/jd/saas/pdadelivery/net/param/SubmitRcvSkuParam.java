package com.jd.saas.pdadelivery.net.param;

import java.math.BigDecimal;
import java.util.Date;

public class SubmitRcvSkuParam {
    private String skuId;
    private int skuType;
    private int skuNature;
    private BigDecimal qty;
    private String locId;
    private String produceDate;
    private String expireDate;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getSkuType() {
        return skuType;
    }

    public void setSkuType(int skuType) {
        this.skuType = skuType;
    }

    public int getSkuNature() {
        return skuNature;
    }

    public void setSkuNature(int skuNature) {
        this.skuNature = skuNature;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
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
}
