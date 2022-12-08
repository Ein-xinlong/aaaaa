package com.jd.saas.pdacommon.search;

import android.text.TextUtils;

import com.jd.saas.pdacommon.imageloader.ImageUrlUtil;

import java.io.Serializable;

/**
 * 内部类-单个商品bean
 */
public class SearchGoodBean implements Serializable {

    private String skuId;
    private String upcCode;
    private String skuName;
    private String logo;
    private String saleUnit;

    public String getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit;
    }

    public String getSkuID() {
        return skuId;
    }

    public void setSkuID(String skuID) {
        this.skuId = skuID;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getLogo() {
        if(!TextUtils.isEmpty(logo)) {
            return ImageUrlUtil.convertImageURL(logo);
        }else {
            return "";
        }
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    // 新添加的商品字段
    private String tenantId;
    private String storeId;
    // 商品类目
    private String categoryId;
    private String categoryName;
    // 供应商
    private String supplierCode;
    // 保质期天数
    private String shelfLifeUnit;
    // 效期状态：1：正常；2：提示；3：预警 4：失效
    private int periodState;
    // 效期状态名称
    private String periodStateName;
    // 库存数量
    private String qty = "0";
    // 货位号
    private String locId;
    // 货位编码
    private String locCode;
    // 货位标记
    private String locFlag;
    // 批次编码
    private String lotCode;
    // 批次号
    private String lotId;
    // 生产日期
    private String produceDate;
    // 临期日期
    private String periodDate;
    // 失效日期
    private String expireDate;
    // 提示日期
    private String hintDate;
    // 预警日期
    private String warnDate;
    // 商品单位名
    private String saleUnitName;
    // 是否是效期商品
    private boolean isShelfLifeSup = false;
    // 商品销售方式：1-计件；2-称重
    private int saleMode = 1;

    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

    public boolean isShelfLifeSup() {
        return isShelfLifeSup;
    }

    public void setShelfLifeSup(boolean shelfLifeSup) {
        isShelfLifeSup = shelfLifeSup;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getShelfLifeUnit() {
        return shelfLifeUnit;
    }

    public void setShelfLifeUnit(String shelfLifeUnit) {
        this.shelfLifeUnit = shelfLifeUnit;
    }

    public int getPeriodState() {
        return periodState;
    }

    public void setPeriodState(int periodState) {
        this.periodState = periodState;
    }

    public String getPeriodStateName() {
        return periodStateName;
    }

    public void setPeriodStateName(String periodStateName) {
        this.periodStateName = periodStateName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocFlag() {
        return locFlag;
    }

    public void setLocFlag(String locFlag) {
        this.locFlag = locFlag;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getHintDate() {
        return hintDate;
    }

    public void setHintDate(String hintDate) {
        this.hintDate = hintDate;
    }

    public String getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(String warnDate) {
        this.warnDate = warnDate;
    }
}
