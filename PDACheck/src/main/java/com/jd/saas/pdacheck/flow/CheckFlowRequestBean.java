package com.jd.saas.pdacheck.flow;

/**
 * 盘点流程接口请求入参bean
 *
 * @author majiheng
 */
public class CheckFlowRequestBean {

    private String tenantId;
    private String whId;
    // 盘点任务号
    private String taskNo;
    // 目标节点
    private int step;
    // 当前节点
    private int currentNode;
    private String pin;

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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
