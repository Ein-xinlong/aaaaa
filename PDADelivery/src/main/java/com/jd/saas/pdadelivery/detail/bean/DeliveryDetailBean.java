package com.jd.saas.pdadelivery.detail.bean;

import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.util.AsnEnumUtils;
import com.jd.saas.pdadelivery.util.Formatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/4
 */
public class DeliveryDetailBean {
    private String asnNo;
    private int asnType;
    private int status;
    private String supplierName;
    private Date createDate;
    private Date closeTime;

    /**
     * [:INTEGER]收货种类
     */
    private int rcvTypeSize;
    /**
     * [:BIGDECIMAL]实际收货数量
     */
    private BigDecimal actualQty = BigDecimal.ZERO;
    /**
     * [:BIGDECIMAL]预期收货数量
     */
    private BigDecimal expectedQty;

    private ArrayList<DeliverySkuBean> skuList;

    private String asnRefNo;

    public String getTypeStr() {
        return AsnEnumUtils.getAsnTypeName(asnType);
    }

    public int getStampRes() {
        if (status == AsnStatusEnum.RECEIVED.getValue() || status == AsnStatusEnum.DIFF_AUDIT.getValue()) {
            return R.drawable.delivery_detail_ellipse;
        } else if (status == AsnStatusEnum.PART_RECEIVE.getValue()) {
            return R.drawable.delivery_deital_receiving;
        } else if (status == AsnStatusEnum.INITIAL.getValue()) {
            return R.drawable.delivery_detail_waiting_stock;
        } else {
            return 0;
        }
    }

    public String getSkuListSizeStr() {
        return Formatter.format(skuList == null ? 0 : skuList.size());
    }

    public String getRcvTypeSizeStr() {
        return Formatter.format(rcvTypeSize);
    }

    public String getActualQtyStr() {
        return Formatter.format(actualQty);
    }

    public String getExpectedQtyStr() {
        return Formatter.format(expectedQty);
    }

    public String getCreateDateStr() {
        return Formatter.format(createDate);
    }

    public String getCloseTimeStr() {
        return Formatter.format(closeTime);
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public int getAsnType() {
        return asnType;
    }

    public void setAsnType(int asnType) {
        this.asnType = asnType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public int getRcvTypeSize() {
        return rcvTypeSize;
    }

    public void setRcvTypeSize(int rcvTypeSize) {
        this.rcvTypeSize = rcvTypeSize;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(BigDecimal expectedQty) {
        this.expectedQty = expectedQty;
    }

    public void setSkuList(ArrayList<DeliverySkuBean> skuList) {
        this.skuList = skuList;
    }

    public ArrayList<DeliverySkuBean> getSkuList() {
        return skuList;
    }

    public String getAsnRefNo() {
        return asnRefNo;
    }

    public void setAsnRefNo(String asnRefNo) {
        this.asnRefNo = asnRefNo;
    }
}
