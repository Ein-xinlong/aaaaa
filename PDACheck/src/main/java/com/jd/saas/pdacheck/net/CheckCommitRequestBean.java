package com.jd.saas.pdacheck.net;

import android.text.TextUtils;

import com.jd.saas.pdacommon.imageloader.ImageUrlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 完成盘点接口入参bean
 *
 * @author majiheng
 */
public class CheckCommitRequestBean {

    private String tenantId;
    private String whId;
    private String source;
    private String createBy;
    private String createDate;
    private String couNo;
    private String taskNo;
    private String pin;
    private String skuType;
    private String deptCode;
    private List<PdaStockTakeInfoDetailBO> couDetailList;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCouNo() {
        return couNo;
    }

    public void setCouNo(String couNo) {
        this.couNo = couNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public List<PdaStockTakeInfoDetailBO> getCouDetailList() {
        return couDetailList;
    }

    public void setCouDetailList(List<PdaStockTakeInfoDetailBO> couDetailList) {
        this.couDetailList = couDetailList;
    }

    public static class PdaStockTakeInfoDetailBO {

        private String actualQty;
        private String skuType;
        private String skuId;
        private String locCode;
        private String locType;
        private String taskNo;
        // 箱柜组合商品
        private List<PdaStockTakeInfoDetailBO> boxProducts = new ArrayList<>();

        // ui元素
        private String icon;
        private String name;
        private String upcCode;
        private String unitName;
        private String locTypeName;

        public List<PdaStockTakeInfoDetailBO> getBoxProducts() {
            return boxProducts;
        }

        public void setBoxProducts(List<PdaStockTakeInfoDetailBO> boxProducts) {
            this.boxProducts = boxProducts;
        }

        public String getIcon() {
            if(!TextUtils.isEmpty(icon)) {
                return ImageUrlUtil.convertImageURL(icon);
            }else {
                return "";
            }
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUpcCode() {
            return upcCode;
        }

        public void setUpcCode(String upcCode) {
            this.upcCode = upcCode;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getLocTypeName() {
            return locTypeName;
        }

        public void setLocTypeName(String locTypeName) {
            this.locTypeName = locTypeName;
        }

        public String getActualQty() {
            return actualQty;
        }

        public void setActualQty(String actualQty) {
            this.actualQty = actualQty;
        }

        public String getSkuType() {
            return skuType;
        }

        public void setSkuType(String skuType) {
            this.skuType = skuType;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getLocCode() {
            return locCode;
        }

        public void setLocCode(String locCode) {
            this.locCode = locCode;
        }

        public String getLocType() {
            return locType;
        }

        public void setLocType(String locType) {
            this.locType = locType;
        }

        public String getTaskNo() {
            return taskNo;
        }

        public void setTaskNo(String taskNo) {
            this.taskNo = taskNo;
        }
    }
}
