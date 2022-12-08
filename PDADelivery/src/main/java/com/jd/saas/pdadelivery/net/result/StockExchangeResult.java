package com.jd.saas.pdadelivery.net.result;

import java.math.BigDecimal;

public class StockExchangeResult {
    // 状态
    private String stockType;//库存类型
    // 单据号
    private String docNo;//单据号
    // 个数
    private BigDecimal qty;//变更数量
    // 创建人
    private String operator;//操作人
    // 时间
    private String operateTime;//操作时间

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getStockType() {
        return stockType;
    }

    public String getDocNo() {
        return docNo;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperateTime() {
        return operateTime;
    }
}
