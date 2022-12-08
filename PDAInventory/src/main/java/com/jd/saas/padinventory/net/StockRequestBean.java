package com.jd.saas.padinventory.net;

import java.util.Date;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/10
 */
public class StockRequestBean {
    private String tenantId;
    private String pin;
    private String warehouseId;
    private String galNo;
    private String createStartTime;
    private String createEndTime;
    private String status;
    private int statusList[];
    private String galBizType;

    public String getGalBizType() {
        return galBizType;
    }

    public void setGalBizType(String galBizType) {
        this.galBizType = galBizType;
    }

    public int[] getStatusList() {
        return statusList;
    }

    public void setStatusList(int[] statusList) {
        this.statusList = statusList;
    }

    // 当前分页
    private String page;
    // 分页一页x条
    private String pageSize;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getGalNo() {
        return galNo;
    }

    public void setGalNo(String galNo) {
        this.galNo = galNo;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
