package com.jd.saas.pdacheck.newtask.bean;

import java.util.ArrayList;

/**
 * 新建盘点任务入参
 * */
public class CheckNewTaskRequestData {
    private String tenantId;//租户ID
    private String whId;//门店Id
    private String taskScope;//盘点范围
    private String startDate;//盘点开始时间
    private String endDate;//盘点结束时间
    private ArrayList<String> catList;//指定类目结合
    private String taskSchema;//新的盘点模式
    private String showStock;//是否显示库存快照：0-不显示，1-显示
    private String missRule;//漏盘规则：0-默认库存为0，1-默认当前库存
    private String taskNo;//跟新盘点用 盘点编号


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

    public String getTaskScope() {
        return taskScope;
    }

    public void setTaskScope(String taskScope) {
        this.taskScope = taskScope;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getCatList() {
        return catList;
    }

    public void setCatList(ArrayList<String> catList) {
        this.catList = catList;
    }

    public String getTaskSchema() {
        return taskSchema;
    }

    public void setTaskSchema(String taskSchema) {
        this.taskSchema = taskSchema;
    }

    public String getShowStock() {
        return showStock;
    }

    public void setShowStock(String showStock) {
        this.showStock = showStock;
    }

    public String getMissRule() {
        return missRule;
    }

    public void setMissRule(String missRule) {
        this.missRule = missRule;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }
}
