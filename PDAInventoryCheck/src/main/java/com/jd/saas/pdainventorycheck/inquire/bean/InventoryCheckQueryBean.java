package com.jd.saas.pdainventorycheck.inquire.bean;

/**
 * 新建页面搜索入参data
 * @author: ext.anxinlong
 * @date: 2021/6/15
 */
public class InventoryCheckQueryBean {
    // 门店id
    private String storeId;
    // 搜索内容
    private String condition;
    // 用户pin
    private String pin;


    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
