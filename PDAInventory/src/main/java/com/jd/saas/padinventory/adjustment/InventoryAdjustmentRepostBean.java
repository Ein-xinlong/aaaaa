package com.jd.saas.padinventory.adjustment;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/15
 */
public class InventoryAdjustmentRepostBean {
    private String galNo;//损益单id
    private String storeId;//店铺id

    public String getGalNo() {
        return galNo;
    }

    public void setGalNo(String galNo) {
        this.galNo = galNo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
