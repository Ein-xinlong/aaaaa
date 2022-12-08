package com.jd.saas.pdacheck.flow.step4.bean;

/**
 * 撤销损益入参
 *
 * @author majiheng
 */
public class CheckProfitRequestBean {

    private String tenantId;
    private String whId;
    private String pin;
    // 盘点编号
    private String taskNo;
    // 当前节点
    private String currentNode;
    // 损益单号
    private String galNo;
    // 过滤条件 1-大于0，0-等于0，-1-小于0，2-不等于0，null-全部
    private int qtyFlag;
    // 页码
    private int pageNo;
    // 一页多少条
    private int pageSize;
    // 搜索条件：1-差异金额、2-差异数量
    private String searchKey;
    // 搜索条件：1-倒序、2-正序
    private String searchValue;
    // 1-复盘查询  2-损益查询
    private int selectCountType;

    public int getSelectCountType() {
        return selectCountType;
    }

    public void setSelectCountType(int selectCountType) {
        this.selectCountType = selectCountType;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getQtyFlag() {
        return qtyFlag;
    }

    public void setQtyFlag(int qtyFlag) {
        this.qtyFlag = qtyFlag;
    }

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getGalNo() {
        return galNo;
    }

    public void setGalNo(String galNo) {
        this.galNo = galNo;
    }
}
