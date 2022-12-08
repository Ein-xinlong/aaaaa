package com.jd.saas.pdainventorycheck.details.model;

import java.util.List;

/**
 * 库位返回
 * @author ext.mengmeng
 */

public class InventoryCheckLocationResponse {
    private int total;
    private String pageNo;
    private String pageSize;
    private int totalPage;
    private List<InventoryCheckLocationBean> itemList;

    public int getTotal() {
        return total;
    }

    public String getPageNo() {
        return pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<InventoryCheckLocationBean> getItemList() {
        return itemList;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setItemList(List<InventoryCheckLocationBean> itemList) {
        this.itemList = itemList;
    }
}
