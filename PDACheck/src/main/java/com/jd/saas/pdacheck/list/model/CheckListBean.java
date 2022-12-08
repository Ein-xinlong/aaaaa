package com.jd.saas.pdacheck.list.model;

import android.text.TextUtils;

import com.jd.saas.pdacheck.R;

import java.io.Serializable;

/**
 * 库存盘点Bean
 *
 * @author ext.mengmeng
 */
public class CheckListBean implements Serializable {

    // 任务号
    private String taskNo;
    private String skuType;
    // 创建人
    private String createBy;
    // 盘点时间
    private String createDate;
    // 任务状态：10未开始、20盘点中、30已完成
    private int status = 0;
    // 审批流专用字段：0审核中
    private int flowStatus = -1;
    // 审批拒绝原因
    private String flowMsg;
    // 预盘点单号
    private String conNo;
    // 盘点范围
    private String taskScopeStr;
    //盘点范围 3是指定类目的  5是整库的
    private int taskScope;
    // 当前盘点进度：10：创建未开始、20：漏判校对、30：差异处理、40：复盘修改、50：损益单申请、60：提交待审核、80：完成
    private int currentNode = 0;
    // 是否显示库存快照：0-不显示、1-显示
    private int showStock;
    // 审批流实例Id
    private String instanceId;

    public String getFlowMsg() {
        if(flowMsg.isEmpty()) {
            flowMsg = "";
        }
        return flowMsg;
    }

    public void setFlowMsg(String flowMsg) {
        this.flowMsg = flowMsg;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getShowStock() {
        return showStock;
    }

    public void setShowStock(int showStock) {
        this.showStock = showStock;
    }

    public int getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
    }

    public int getStatusStr() {
        if(flowStatus == 0) {
            // 审核中
            return R.string.check_status_review;
        } else if(flowStatus == 2) {
            // 被驳回：将文案状态设置为「继续盘点」，此时列表条目没有「向右」的箭头
            return R.string.check_continue;
        } else {
            if(status == 10) {
                return R.string.check_start;
            }else if(status == 20) {
                return R.string.check_continue;
            }else if(status == 30) {
                return R.string.check_status_done;
            }else {
                return R.string.check_status_unknown;
            }
        }
    }

    public String getTaskScopeStr() {
        return taskScopeStr;
    }

    public void setTaskScopeStr(String taskScopeStr) {
        this.taskScopeStr = taskScopeStr;
    }

    public String getConNo() {
        return conNo;
    }

    public void setConNo(String conNo) {
        this.conNo = conNo;
    }

    public String getTaskNo() {
        if(TextUtils.isEmpty(taskNo)) {
            taskNo = "";
        }
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        if(TextUtils.isEmpty(createDate)) {
            createDate = "";
        }
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }

    public int getTaskScope() {
        return taskScope;
    }

    public void setTaskScope(int taskScope) {
        this.taskScope = taskScope;
    }
}
