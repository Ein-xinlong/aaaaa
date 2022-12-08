package com.jd.saas.pdamain.home.bean;

/**
 * 「拣货」列表
 *
 * @author majiheng
 */
public class MainPickingRequestBean {

    // 搜索订单id
    private String refNo;
    // 门店id
    private String whId;
    // 传1
    private int tab;
    // 传1
    private int doType;
    // 传1
    private int sortType;
    // 传1
    private int page;
    // 传1
    private int pageSize;

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public int getDoType() {
        return doType;
    }

    public void setDoType(int doType) {
        this.doType = doType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
