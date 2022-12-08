package com.jd.saas.pdacheck.newtask.bean;
/**
 * 查看盘点任务入参
 * */
public class CheckNewTaskUpdateRequestData {
    private String tenantId;
    private String whId;
    private String taskNo;
    private int flag;


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

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
