package com.jd.saas.pdacommon.printer_ble.bean;

import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

public class DirectDeliveryOrderBean implements Serializable {
    private String poCode;//订单号
    private String roCode;//订单号
    private String purchaseName;//门店名称

    private String supplierCode;//供应商号
    private String supplierName;//供应商
    private List<DetailListbean> detailList;

    private String totalActualReceiveUntaxedAmt;//合计总金额
    private String totalNum;//合计数量
    private String tenantName;//租户名称
    private String createDate;//制单时间
    private String printDate;//打印时间
    private String orderTypeName;//单据类型

    /// 合计总金额
    private double totalSupplierActualReceiveAmt;

    public String getPoCode() {

        return poCode;

    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getPurchaseName() {
        if (purchaseName != null) {
            return purchaseName;
        }
        return "--";
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }


    public String getSupplierCode() {
        if (supplierCode != null) {
            return supplierCode;
        }
        return "--";
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        if (supplierName != null) {
            return supplierName;
        }
        return "--";
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<DetailListbean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListbean> detailList) {
        this.detailList = detailList;
    }


    public String getTotalActualReceiveUntaxedAmt() {
        if (totalActualReceiveUntaxedAmt != null) {
            return totalActualReceiveUntaxedAmt;
        }
        return "--";
    }

    public void setTotalActualReceiveUntaxedAmt(String totalActualReceiveUntaxedAmt) {
        this.totalActualReceiveUntaxedAmt = totalActualReceiveUntaxedAmt;
    }


    public String getTotalNum() {
        if (totalNum != null) {
            return totalNum;
        }
        return "--";
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getTenantName() {
        if (tenantName != null) {
            return tenantName;
        }
        return "--";
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }


    public String getCreateDate() {
        if (createDate != null) {
            return createDate;
        }
        return "--";
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPrintDate() {
        if (printDate != null) {
            return printDate;
        }
        return "--";
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getOrderTypeName() {
        if (orderTypeName != null) {
            return orderTypeName;
        }
        return "--";
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getTotalSupplierActualReceiveAmt() {
        DecimalFormat df = new DecimalFormat("#0.00");
        if(df.format(totalSupplierActualReceiveAmt).equals("0.00")){
            return "0";
        }else{
            return df.format(totalSupplierActualReceiveAmt);
        }

    }

    public void setTotalSupplierActualReceiveAmt(double totalSupplierActualReceiveAmt) {
        this.totalSupplierActualReceiveAmt = totalSupplierActualReceiveAmt;
    }

    public String getRoCode() {

        return roCode;

    }

    public void setRoCode(String roCode) {
        this.roCode = roCode;
    }

    public static class DetailListbean implements Serializable {
        private String skuId;
        private String skuName;
        private String upcCode;
        private String sendNum;//数量
        private String skuPrice;//单价
        private String amt;//含税金额


        /// 供应商发生单价
        private double supplierSkuPrice;

        /// 供应商商品实收含税总金额
        private double supplierActualReceiveAmt;


        public String getSkuId() {
            if (skuId != null) {
                return skuId;
            }
            return "--";
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getSkuName() {
            if (skuName != null) {
                return skuName;
            }
            return "--";
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public String getUpcCode() {
            if (upcCode != null) {
                return upcCode;
            }
            return "--";
        }

        public void setUpcCode(String upcCode) {
            this.upcCode = upcCode;
        }


        public String getSkuPrice() {
            if (skuPrice != null) {
                return skuPrice;
            }
            return "--";
        }

        public void setSkuPrice(String skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getAmt() {
            if (amt != null) {
                return amt;
            }
            return "--";
        }

        public void setAmt(String amt) {
            this.amt = amt;
        }


        public String getSendNum() {
            if (sendNum != null) {
                return sendNum;
            }
            return "--";
        }

        public void setSendNum(String sendNum) {
            this.sendNum = sendNum;
        }

        public String getSupplierSkuPrice() {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(supplierSkuPrice);
        }

        public void setSupplierSkuPrice(double supplierSkuPrice) {
            this.supplierSkuPrice = supplierSkuPrice;
        }

        public String getSupplierActualReceiveAmt() {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(supplierActualReceiveAmt);
        }

        public void setSupplierActualReceiveAmt(double supplierActualReceiveAmt) {
            this.supplierActualReceiveAmt = supplierActualReceiveAmt;
        }
    }
}
