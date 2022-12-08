package com.jd.saas.padinventory.adjustment.detail;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/17
 */
public class InventoryAdjustmentDetailAddBean {
    private String storeId;//店铺id
    private String skuId;//商品编号
    private String profitOrLossCode;//损益编号
    private String locId;//库位
    private String proLossNum;//损益数量
    private String reasonCode;//损益原因
    private String reason;//损益原因

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getProfitOrLossCode() {
        return profitOrLossCode;
    }

    public void setProfitOrLossCode(String profitOrLossCode) {
        this.profitOrLossCode = profitOrLossCode;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getProLossNum() {
        return proLossNum;
    }

    public void setProLossNum(String proLossNum) {
        this.proLossNum = proLossNum;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}
