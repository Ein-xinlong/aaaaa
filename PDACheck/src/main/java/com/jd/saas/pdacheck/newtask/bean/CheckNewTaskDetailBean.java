package com.jd.saas.pdacheck.newtask.bean;

import java.util.ArrayList;

/**
 * 查看盘点任务反参
 */
public class CheckNewTaskDetailBean {

    private String taskNo;
    private int tenantId;
    private int whId;
    private int taskModel;
    private int taskScope;
    private String taskScopeStr;
    private int skuType;
    private String skuTypeStr;
    private String createDate;
    private String startDate;
    private String endDate;
    private Object deptCode;
    private String createBy;
    private String updateBy;
    private int currentNode;
    private String currentNodeStr;
    private Object step;
    private Object taskSchema;
    private Object taskSchemaStr;
    private Object randomRatio;
    private Object singleCheck;
    private Object onlyLoss;
    private Object lockStk;
    private int showStock;
    private int missRule;
    private ArrayList<String> catList;
    private String startEndDateStr;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getWhId() {
        return whId;
    }

    public void setWhId(int whId) {
        this.whId = whId;
    }

    public int getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(int taskModel) {
        this.taskModel = taskModel;
    }

    public int getTaskScope() {
        return taskScope;
    }

    public void setTaskScope(int taskScope) {
        this.taskScope = taskScope;
    }

    public String getTaskScopeStr() {
        return taskScopeStr;
    }

    public void setTaskScopeStr(String taskScopeStr) {
        this.taskScopeStr = taskScopeStr;
    }

    public int getSkuType() {
        return skuType;
    }

    public void setSkuType(int skuType) {
        this.skuType = skuType;
    }

    public String getSkuTypeStr() {
        return skuTypeStr;
    }

    public void setSkuTypeStr(String skuTypeStr) {
        this.skuTypeStr = skuTypeStr;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public Object getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(Object deptCode) {
        this.deptCode = deptCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public int getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
    }

    public String getCurrentNodeStr() {
        return currentNodeStr;
    }

    public void setCurrentNodeStr(String currentNodeStr) {
        this.currentNodeStr = currentNodeStr;
    }

    public Object getStep() {
        return step;
    }

    public void setStep(Object step) {
        this.step = step;
    }

    public Object getTaskSchema() {
        return taskSchema;
    }

    public void setTaskSchema(Object taskSchema) {
        this.taskSchema = taskSchema;
    }

    public Object getTaskSchemaStr() {
        return taskSchemaStr;
    }

    public void setTaskSchemaStr(Object taskSchemaStr) {
        this.taskSchemaStr = taskSchemaStr;
    }

    public Object getRandomRatio() {
        return randomRatio;
    }

    public void setRandomRatio(Object randomRatio) {
        this.randomRatio = randomRatio;
    }

    public Object getSingleCheck() {
        return singleCheck;
    }

    public void setSingleCheck(Object singleCheck) {
        this.singleCheck = singleCheck;
    }

    public Object getOnlyLoss() {
        return onlyLoss;
    }

    public void setOnlyLoss(Object onlyLoss) {
        this.onlyLoss = onlyLoss;
    }

    public Object getLockStk() {
        return lockStk;
    }

    public void setLockStk(Object lockStk) {
        this.lockStk = lockStk;
    }

    public int getShowStock() {
        return showStock;
    }

    public void setShowStock(int showStock) {
        this.showStock = showStock;
    }

    public int getMissRule() {
        return missRule;
    }

    public void setMissRule(int missRule) {
        this.missRule = missRule;
    }

    public ArrayList<String> getCatList() {
        return catList;
    }

    public void setCatList(ArrayList<String> catList) {
        this.catList = catList;
    }

    public String getStartEndDateStr() {
        return startEndDateStr;
    }

    public void setStartEndDateStr(String startEndDateStr) {
        this.startEndDateStr = startEndDateStr;
    }
}
