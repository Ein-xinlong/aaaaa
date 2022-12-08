package com.jd.saas.pdadelivery.detail.bean;

import androidx.annotation.NonNull;

import com.jd.saas.pdadelivery.util.Formatter;

import java.math.BigDecimal;

public class DeliveryBoxProductBean {
    public DeliveryBoxProductBean() {
    }

    /**
     * 组合关系id
     */
    private String boxSkuId;
    /**
     * 该子商品skuId
     */
    private String skuId;
    /**
     * 组合系数
     */
    private BigDecimal boxNum;
    /**
     * 该子商品名称
     */
    private String skuName;
    /**
     * 该子商品条码
     */
    private String upcCode;

    // ============ 以下为本地添加的属性 =================//
    private int skuType;
    /**
     * [:BIGDECIMAL]实际历史收货数量
     */
    private BigDecimal actualQty = BigDecimal.ZERO;
    /**
     * [:BIGDECIMAL]预期收货数量
     */
    private BigDecimal expectedQty = BigDecimal.ZERO;
    /**
     * 本次收货数量 显示用的 由外部写入
     */
    @NonNull
    private BigDecimal inputQty = BigDecimal.ZERO;

    /**
     * 本次的收货限制数量 每次重新计算
     */
    private BigDecimal limit = BigDecimal.ZERO;
    /**
     * 最大收货数量
     */
    private BigDecimal upperLimitReceived;
    /**
     * 普通商品的本次收货库位类型id
     */
    private String locId;
    /**
     * 普通商品的本次收货库位类型名称
     */
    private String locName;
    /**
     * 普通商品的本次收货库位类型的类型
     */
    private String locType;

    //=========显示用的计算属性 ===============//
    public String getInputQtyStr() {
        return Formatter.format(inputQty);
    }

    public String getActualQtyStr() {
        return Formatter.format(actualQty);
    }

    public String getExpectedQtyStr() {
        return Formatter.format(expectedQty);
    }

    /**
     * 待收数量 不可变
     */
    public String getUnreceivedQtyStr() {
        if (expectedQty == null) {
            return "0";
        }
        if (actualQty != null) {
            return Formatter.format(expectedQty.subtract(actualQty));
        } else {
            return "0";
        }
    }

    public String getBoxNumStr() {
        return Formatter.format(boxNum);
    }

    public String getBoxSkuId() {
        return boxSkuId;
    }

    public void setBoxSkuId(String boxSkuId) {
        this.boxSkuId = boxSkuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(BigDecimal boxNum) {
        this.boxNum = boxNum;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(BigDecimal expectedQty) {
        this.expectedQty = expectedQty;
    }

    @NonNull
    public BigDecimal getInputQty() {
        return inputQty;
    }

    public void setInputQty(@NonNull BigDecimal inputQty) {
        this.inputQty = inputQty;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
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

    public BigDecimal getUpperLimitReceived() {
        return upperLimitReceived;
    }

    public void setUpperLimitReceived(BigDecimal upperLimitReceived) {
        this.upperLimitReceived = upperLimitReceived;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public int getSkuType() {
        return skuType;
    }

    public void setSkuType(int skuType) {
        this.skuType = skuType;
    }
}
