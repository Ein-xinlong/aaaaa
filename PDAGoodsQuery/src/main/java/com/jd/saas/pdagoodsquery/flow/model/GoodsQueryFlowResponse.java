package com.jd.saas.pdagoodsquery.flow.model;

import java.util.List;

/**
 * 库存 响应数据
 * @author ext.mengmeng
 */
public class GoodsQueryFlowResponse {

    private int total;
    private String pageNo;
    private String pageSize;
    private int totalPage;
    private List<GoodsQueryFlowBean> itemList;



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

    public void setItemList(List<GoodsQueryFlowBean> itemList) {
        this.itemList = itemList;
    }

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

    public List<GoodsQueryFlowBean> getItemList() {
        return itemList;
    }
}
