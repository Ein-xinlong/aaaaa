package com.jd.saas.pdadelivery.net.result;

import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.net.enums.AsnTypeEnum;

import java.math.BigDecimal;

public class StockItemResult {
    private String tenantId;
    private String warehouseId;
    /**
     * 入库单编码(ASN单号)
     */
    private String asnNo;
    /**
     * [:INTEGER]入库单类型，参考{@link AsnTypeEnum}
     */
    private int asnType;
    /**
     * 第三方主关联单号（如采购单号）
     */
    private String asnRefNo;
    /**
     * [:INTEGER]入库单状态，参考{@link AsnStatusEnum}
     */
    private int status;
    /**
     * [:BIGDECIMAL]预期收货数量
     */
    private BigDecimal expectedQty;
    /**
     * [:BIGDECIMAL]实际收货数量
     */
    private BigDecimal actualQty;
    private String supplierCode;
    private String supplierName;
    /**
     * [:DATE]预计到货时间
     */
    private String expectArrivalTime;
    /**
     * [:DATE]开始收货时间
     */
    private String startReceiveTime;
    /**
     * [:DATE]完成收货时间
     */
    private String closeTime;
    private String createDate;
    private String createBy;
    private String updateDate;
    private String updateBy;
    /**
     * 交易类型
     */
    private String transType;
    /**
     * 负责人
     */
    private String contactName;
    /**
     * 负责人电话
     */
    private String contactTelephone;

    /**
     * [:INTEGER]收货种类
     */
    private int rcvTypeSize;
    /**
     * [:INTEGER]已收齐
     */
    private int rcvFinished;
    /**
     * [:INTEGER]未收齐
     */
    private int rcvSome;
    /**
     * [:INTEGER]未收货
     */
    private int rcvNone;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
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

    public String getAsnRefNo() {
        return asnRefNo;
    }

    public void setAsnRefNo(String asnRefNo) {
        this.asnRefNo = asnRefNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(BigDecimal expectedQty) {
        this.expectedQty = expectedQty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getExpectArrivalTime() {
        return expectArrivalTime;
    }

    public void setExpectArrivalTime(String expectArrivalTime) {
        this.expectArrivalTime = expectArrivalTime;
    }

    public String getStartReceiveTime() {
        return startReceiveTime;
    }

    public void setStartReceiveTime(String startReceiveTime) {
        this.startReceiveTime = startReceiveTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public int getRcvTypeSize() {
        return rcvTypeSize;
    }

    public void setRcvTypeSize(int rcvTypeSize) {
        this.rcvTypeSize = rcvTypeSize;
    }

    public int getRcvFinished() {
        return rcvFinished;
    }

    public void setRcvFinished(int rcvFinished) {
        this.rcvFinished = rcvFinished;
    }

    public int getRcvSome() {
        return rcvSome;
    }

    public void setRcvSome(int rcvSome) {
        this.rcvSome = rcvSome;
    }

    public int getRcvNone() {
        return rcvNone;
    }

    public void setRcvNone(int rcvNone) {
        this.rcvNone = rcvNone;
    }
}
