package com.jd.saas.pdagoodsquery.sale.model;

/**
 * 供销存bean
 */
public class GoodsQuerySale {
//  //{"skuId":"10000000027201","date":"2021.08.22","week":"周日","skuNum":2.0,"stock":11119.0,"purchaseNum":0,"frmLossNum":0}
    private String skuId;
    private String date;
    private String week;
    private String skuNum;//销售数量
    private String stock;//日结库存
    private String purchaseNum;//进货数量
    private String frmLossNum;//报损数量

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setFrmLossNum(String frmLossNum) {
        this.frmLossNum = frmLossNum;
    }

    public String getFrmLossNum() {
        return frmLossNum;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeek() {
        return week;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setPurchaseNum(String purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public String getDate() {
        return date;
    }

    public String getSkuNum() {
        return skuNum;
    }

    public String getStock() {
        return stock;
    }

    public String getPurchaseNum() {
        return purchaseNum;
    }
}
