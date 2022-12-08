package com.jd.saas.pdacheck.flow.step4.bean;

import java.util.List;

/**
 * 损益列表返回结果Bean
 *
 * @author majiheng
 */
public class CheckProfitListResultBean {

    // 当前页数
    private int pageNo;
    // 一页多少条数据
    private int pageSize;
    // 总共多少条数据
    private int total;
    // 列表
    private List<CheckProfitListItemBean> itemList;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CheckProfitListItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<CheckProfitListItemBean> itemList) {
        this.itemList = itemList;
    }
}
