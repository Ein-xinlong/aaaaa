package com.jd.saas.pdagoodsquery.sale.model;

import java.util.Date;

/**
 * 单据明细-dialog
 * {
 *         "skuId": "10000000015402",
 *             "skuName": "测试商品图片",
 *             "date": "2021-08-20 11:25:59",
 *             "poCode": "AO000000035325",
 *             "poNum": 800.0,
 *             "poAmt": 2400.0,
 *             "frmLossNum": null
 *     },
 */
public class GoodsQueryReceipt {

    private String skuId;
    private String skuName;
    private String name;
    private Date date;
    private String poCode;
    private String poNum;
    private String poAmt;
    private String frmLossNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    public void setPoAmt(String poAmt) {
        this.poAmt = poAmt;
    }

    public void setFrmLossNum(String frmLossNum) {
        this.frmLossNum = frmLossNum;
    }

    public String getSkuId() {
        return skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public Date getDate() {
        return date;
    }

    public String getPoCode() {
        return poCode;
    }

    public String getPoNum() {
        return poNum;
    }

    public String getPoAmt() {
        return poAmt;
    }

    public String getFrmLossNum() {
        return frmLossNum;
    }
}
