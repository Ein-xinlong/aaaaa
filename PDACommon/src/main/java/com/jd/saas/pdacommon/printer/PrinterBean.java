package com.jd.saas.pdacommon.printer;

import android.text.TextUtils;

import java.util.List;

/**
 * 小票打印模版
 *
 * @author majiheng
 */
public class PrinterBean {

    // 订单来源
    private String orderSource;
    // #美团订单# 等订单来源
    private String orderSourceDesc;
    // 门店名称
    private String whName;
    // 订单编号
    private String refNo;
    // 平台订单号-条码
    private String extNo;
    // 预计自提/送达时间
    private String expectedArriveTime;
    // 客户姓名
    private String consigneeName;
    // 联系电话
    private String mobile;
    // 客户地址 or 自提点名称
    private String address;
    // 数量合计
    private String skuNum;
    // 金额总计
    private String amountTotal;
    // 客户备注
    private String remark;
    // 门店客服电话
    private String storePhone;
    // 自提-1、配送-2
    private int deliveryType = 1;
    // 数量合计
    private String totalQty;
    // 商品金额
    private String promotionPrice;
    // 配送费
    private String shippingFee;
    // 打包费
    private String packIngFee;
    // 打印时间
    private String printDateStr;
    // 优惠总计
    private String totalDiscount;
    // 实付总计
    private String receivableAmount;
    // 应付总计
    private String totalAmount;
    // 商品列表
    private List<GoodDetail> detailVOList;

    // 单个商品内容
    class GoodDetail {

        // 商品名称
        private String skuName;
        // 单价
        private String price;
        // 数量
        private String pickQty;
        // 总价
        private String productAmount;
        // upc码
        private String upcCode;
        // sku码
        private String skuId;

        public String getGoodCode() {
            if(!TextUtils.isEmpty(getUpcCode())) {
                return getUpcCode();
            }else {
                return getSkuId();
            }
        }

        public String getSkuId() {
            if(TextUtils.isEmpty(skuId)) {
                skuId = "";
            }
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getUpcCode() {
            if(TextUtils.isEmpty(upcCode)) {
                upcCode = "";
            }
            return upcCode;
        }

        public void setUpcCode(String upcCode) {
            this.upcCode = upcCode;
        }

        public String getSkuName() {
            if(TextUtils.isEmpty(skuName)) {
                skuName = "";
            }
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public String getPrice() {
            if(TextUtils.isEmpty(price)) {
                price = "";
            }
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPickQty() {
            if(TextUtils.isEmpty(pickQty)) {
                pickQty = "";
            }
            return pickQty;
        }

        public void setPickQty(String pickQty) {
            this.pickQty = pickQty;
        }

        public String getProductAmount() {
            if(TextUtils.isEmpty(productAmount)) {
                productAmount = "";
            }
            return productAmount;
        }

        public void setProductAmount(String productAmount) {
            this.productAmount = productAmount;
        }
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalDiscount() {
        if(TextUtils.isEmpty(totalDiscount)) {
            totalDiscount = "";
        }
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getTotalQty() {
        if(TextUtils.isEmpty(totalQty)) {
            totalQty = "";
        }
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getPromotionPrice() {
        if(TextUtils.isEmpty(promotionPrice)) {
            promotionPrice = "";
        }
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getShippingFee() {
        if(TextUtils.isEmpty(shippingFee)) {
            shippingFee = "";
        }
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getPackIngFee() {
        if(TextUtils.isEmpty(packIngFee)) {
            packIngFee = "";
        }
        return packIngFee;
    }

    public void setPackIngFee(String packIngFee) {
        this.packIngFee = packIngFee;
    }

    public String getPrintDateStr() {
        if(TextUtils.isEmpty(printDateStr)) {
            printDateStr = "";
        }
        return printDateStr;
    }

    public void setPrintDateStr(String printDateStr) {
        this.printDateStr = printDateStr;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<GoodDetail> getDetailVOList() {
        return detailVOList;
    }

    public void setDetailVOList(List<GoodDetail> detailVOList) {
        this.detailVOList = detailVOList;
    }

    public String getStorePhone() {
        if(TextUtils.isEmpty(storePhone)) {
            storePhone = "";
        }
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getOrderSource() {
        if(TextUtils.isEmpty(orderSource)) {
            orderSource = "";
        }
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderSourceDesc() {
        if(TextUtils.isEmpty(orderSourceDesc)) {
            orderSourceDesc = "";
        }
        return orderSourceDesc;
    }

    public void setOrderSourceDesc(String orderSourceDesc) {
        this.orderSourceDesc = orderSourceDesc;
    }

    public String getWhName() {
        if(TextUtils.isEmpty(whName)) {
            whName = "";
        }
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getRefNo() {
        if(TextUtils.isEmpty(refNo)) {
            refNo = "";
        }
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getExtNo() {
        if(TextUtils.isEmpty(extNo)) {
            extNo = "";
        }
        return extNo;
    }

    public void setExtNo(String extNo) {
        this.extNo = extNo;
    }

    public String getExpectedArriveTime() {
        if(TextUtils.isEmpty(expectedArriveTime)) {
            expectedArriveTime = "";
        }
        return expectedArriveTime;
    }

    public void setExpectedArriveTime(String expectedArriveTime) {
        this.expectedArriveTime = expectedArriveTime;
    }

    public String getConsigneeName() {
        if(TextUtils.isEmpty(consigneeName)) {
            consigneeName = "无";
        }
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getMobile() {
        if(TextUtils.isEmpty(mobile)) {
            mobile = "无";
        }
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        if(TextUtils.isEmpty(address)) {
            address = "无";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkuNum() {
        if(TextUtils.isEmpty(skuNum)) {
            skuNum = "";
        }
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public String getAmountTotal() {
        if(TextUtils.isEmpty(amountTotal)) {
            amountTotal = "";
        }
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getReceivableAmount() {
        if(TextUtils.isEmpty(receivableAmount)) {
            receivableAmount = "";
        }
        return receivableAmount;
    }

    public void setReceivableAmount(String receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getRemark() {
        if(TextUtils.isEmpty(remark)) {
            remark = "无";
        }
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
