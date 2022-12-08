package com.jd.saas.pdacheck.net;

import java.util.List;

public class CheckPagedResp<E> {
    private int total;
    private int totalPage;
    private int pageNo;
    private int pageSize;
    private List<E> itemList;

    public int getTotal() {
        return total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<E> getItemList() {
        return itemList;
    }

    public void setItemList(List<E> itemList) {
        this.itemList = itemList;
    }
}
