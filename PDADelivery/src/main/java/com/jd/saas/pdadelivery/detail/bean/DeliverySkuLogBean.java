package com.jd.saas.pdadelivery.detail.bean;

import com.jd.saas.pdadelivery.util.Formatter;

import java.math.BigDecimal;
import java.util.Date;

public class DeliverySkuLogBean {
    private Date operateTime;
    private BigDecimal qty;
    private String operator;

    public String getQtyStr() {
        return Formatter.format(qty);
    }

    public String getOperateTimeStr() {
        return Formatter.format(operateTime);
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
