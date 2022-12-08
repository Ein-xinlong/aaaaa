package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.net.enums.AsnTypeEnum;

import java.util.List;

public class AsnListDataParam {
    /**
     * 租户ID
     */
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    /**
     * 门店ID
     */
    private final String warehouseId = UserManager.getInstance().getUserData().getShopId();
    /**
     * 用户pin
     */
    private final String pin = UserManager.getInstance().getUserData().getUserPin();
    /**
     * 单据号
     */
    private String docNo;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 单据状态
     *
     * @see AsnStatusEnum
     */
    private List<Integer> statusEnumsList;
    /**
     * 开始时间
     */
    private String createStartTime;
    /**
     * 创建结束时间
     */
    private String createEndTime;
    /**
     * 入库单类型
     *
     * @see AsnTypeEnum
     */
    private String asnType = null;
    /**
     * 交易类型
     */
    private String transType = null;
    /**
     * 分页参数
     */
    private PageQueryParam pageQuery;


    public void setPageQuery(PageQueryParam pageQuery) {
        this.pageQuery = pageQuery;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setStatusEnumsList(List<Integer> statusEnumsList) {
        this.statusEnumsList = statusEnumsList;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public void setAsnType(String asnType) {
        this.asnType = asnType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public List<Integer> getStatusEnumsList() {
        return statusEnumsList;
    }
}
