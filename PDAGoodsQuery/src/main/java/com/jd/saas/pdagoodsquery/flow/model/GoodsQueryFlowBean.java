package com.jd.saas.pdagoodsquery.flow.model;

import java.util.Date;

/**
 * 库存流水列表adapter
 *
 * @author majiheng
 */
public class GoodsQueryFlowBean {

    // 状态
    private String transType;//库存类型
    private String lotCode;//库存类型

    // 状态
    private String stockType;//
    // 单据号
    private String docNo;//单据号
    private String docRefNo;//业务单据号
    // 个数
    private String qty;//变更数量
    // 创建人
    private String operator;//操作人
    // 时间
    private Date operateTime;//操作时间
    private String transTypeName;//库存类型名称
    private String transChildType;//库存子类型
    private String transChildTypeName;//库存子类型名称

    public String getDocRefNo() {
        return docRefNo;
    }

    public void setDocRefNo(String docRefNo) {
        this.docRefNo = docRefNo;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public void setTransChildType(String transChildType) {
        this.transChildType = transChildType;
    }

    public void setTransChildTypeName(String transChildTypeName) {
        this.transChildTypeName = transChildTypeName;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public String getTransChildType() {
        return transChildType;
    }

    public String getTransChildTypeName() {
        return transChildTypeName;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getStockType() {
        return stockType;
    }

    public String getDocNo() {
        return docNo;
    }

    public String getQty() {
        return qty;
    }

    public String getOperator() {
        return operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * 条目点击
     */
    public void itemClick() {

    }
}
