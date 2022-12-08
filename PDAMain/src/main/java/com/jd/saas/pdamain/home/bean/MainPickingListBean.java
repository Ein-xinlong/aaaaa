package com.jd.saas.pdamain.home.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * 获取「拣货」模块列表
 *
 * @author majiheng
 * */
public class MainPickingListBean {

    // 共多少条
    private int total;
    // 分页大小
    private int pageSize;
    // 当前页数
    private int pageNo;
    // 商品列表条目
    private List<ItemBean> itemList;

    public List<ItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemBean> itemList) {
        this.itemList = itemList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 列表条目bean
     * */
    public static class ItemBean {

        // 订单编号
        String refNo;
        // 订单渠道，美团-5、饿了么-7、京东到家-4、小程序-3
        int orderSource;
        // 期望开始时间
        String expectedArriveStartDate;
        // 期望结束时间
        String expectedArriveEndDate;
        // 配送方式，自提-1，配送-2
        int deliveryType;
        // 图片列表
        List<String> imgList;
        // 共几种商品
        int speQty;
        // 待出库-0、已取消-90、已完成80
        int doStatus;
        // 取消时间/出库时间
        String doUpdateDate;
        // 实际出库数量
        int actualSpeQty;
        // 剩余时间or超时时间
        String remindTime;
        // 0剩余时间，1超时时间
        int remindType;
        // 「预计自提时间」和「预计送达时间」
        String expectedArriveDateStr;

        public String getRefNo() {
            if(TextUtils.isEmpty(refNo)) {
                refNo = "";
            }
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }

        public int getOrderSource() {
            return orderSource;
        }

        public void setOrderSource(int orderSource) {
            this.orderSource = orderSource;
        }

        public String getExpectedArriveStartDate() {
            if(TextUtils.isEmpty(expectedArriveStartDate)) {
                expectedArriveStartDate = "";
            }
            return expectedArriveStartDate;
        }

        public void setExpectedArriveStartDate(String expectedArriveStartDate) {
            this.expectedArriveStartDate = expectedArriveStartDate;
        }

        public String getExpectedArriveEndDate() {
            if(TextUtils.isEmpty(expectedArriveEndDate)) {
                expectedArriveEndDate = "";
            }
            return expectedArriveEndDate;
        }

        public void setExpectedArriveEndDate(String expectedArriveEndDate) {
            this.expectedArriveEndDate = expectedArriveEndDate;
        }

        public int getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(int deliveryType) {
            this.deliveryType = deliveryType;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public int getSpeQty() {
            return speQty;
        }

        public void setSpeQty(int speQty) {
            this.speQty = speQty;
        }

        public int getDoStatus() {
            return doStatus;
        }

        public void setDoStatus(int doStatus) {
            this.doStatus = doStatus;
        }

        public String getDoUpdateDate() {
            if(TextUtils.isEmpty(doUpdateDate)) {
                doUpdateDate = "";
            }
            return doUpdateDate;
        }

        public void setDoUpdateDate(String doUpdateDate) {
            this.doUpdateDate = doUpdateDate;
        }

        public int getActualSpeQty() {
            return actualSpeQty;
        }

        public void setActualSpeQty(int actualSpeQty) {
            this.actualSpeQty = actualSpeQty;
        }

        public String getRemindTime() {
            if(TextUtils.isEmpty(remindTime)) {
                remindTime = "";
            }
            return remindTime;
        }

        public void setRemindTime(String remindTime) {
            this.remindTime = remindTime;
        }

        public int getRemindType() {
            return remindType;
        }

        public void setRemindType(int remindType) {
            this.remindType = remindType;
        }

        public String getExpectedArriveDateStr() {
            if(TextUtils.isEmpty(expectedArriveDateStr)) {
                expectedArriveDateStr = "";
            }
            return expectedArriveDateStr;
        }

        public void setExpectedArriveDateStr(String expectedArriveDateStr) {
            this.expectedArriveDateStr = expectedArriveDateStr;
        }
    }
}
