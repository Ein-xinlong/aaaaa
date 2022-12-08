package com.jd.saas.pdacheck.flow.step4.bean;

import android.text.TextUtils;

/**
 * 损益单列表条目bean
 *
 * @author majiheng
 */
public class CheckProfitListItemBean {

    // 当前条目序号
    private String num;
    // 商品名称
    private String skuName;
    // 商品编码
    private String upcCodes;
    // 库位名称
    private String locName;
    // 预盘单
    private String galNo;
    // 盘点人
    private String createBy;
    // 库存数量
    private String stockQty;
    // 盘点数量
    private String actualQty;
    // 差异数量
    private String qty;
    // 加权平均数量
    private String avgPrice;
    // 差异金额
    private String galPrice;

    public String getActualQty() {
        if(TextUtils.isEmpty(actualQty)) {
            actualQty = "0";
        }
        return actualQty;
    }

    public void setActualQty(String actualQty) {
        this.actualQty = actualQty;
    }

    public String getQty() {
        if(TextUtils.isEmpty(qty)) {
            qty = "0";
        }
        return qty;
    }

    public void setQty(String diffQty) {
        this.qty = diffQty;
    }

    public String getAvgPrice() {
        if(TextUtils.isEmpty(avgPrice)) {
            avgPrice = "0";
        }
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getGalPrice() {
        if(TextUtils.isEmpty(galPrice)) {
            galPrice = "0";
        }
        return galPrice;
    }

    public void setGalPrice(String galPrice) {
        this.galPrice = galPrice;
    }

    public String getGalNo() {
        if(TextUtils.isEmpty(galNo)) {
            galNo = "--";
        }
        return galNo;
    }

    public void setGalNo(String galNo) {
        this.galNo = galNo;
    }

    public String getCreateBy() {
        if(TextUtils.isEmpty(createBy)) {
            createBy = "--";
        }
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getStockQty() {
        if(TextUtils.isEmpty(stockQty)) {
            stockQty = "0";
        }
        return stockQty;
    }

    public void setStockQty(String stockQty) {
        this.stockQty = stockQty;
    }

    public String getLocName() {
        if(TextUtils.isEmpty(locName)) {
            locName = "--";
        }
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSkuName() {
        if(TextUtils.isEmpty(skuName)) {
            skuName = "--";
        }
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getUpcCodes() {
        if(TextUtils.isEmpty(upcCodes)) {
            upcCodes = "--";
        }
        return upcCodes;
    }

    public void setUpcCodes(String upcCodes) {
        this.upcCodes = upcCodes;
    }
}
