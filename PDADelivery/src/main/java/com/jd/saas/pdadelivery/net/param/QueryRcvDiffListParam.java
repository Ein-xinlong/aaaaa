package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdadelivery.util.DeliveryConfigProvider;

public class QueryRcvDiffListParam {
    /**
     * 租户 ID
     */
    private String tenantId = UserManager.getInstance().getUserData().getTenantId();
    /**
     * 仓库编号，即门店 ID
     */
    private String warehouseId = UserManager.getInstance().getUserData().getShopId();

    /**
     * warehouse类型：1-门店 2-配送中心
     */
    private int warehouseType = DeliveryConfigProvider.getClientType().getValue();
    /**
     * ASN编号 必填
     */
    private String asnNo;

    /**
     * 分页参数
     */
    private PageQueryParam pageQuery;


    public QueryRcvDiffListParam(String asnNo,PageQueryParam pageQuery) {
        this.asnNo = asnNo;
        this.pageQuery =pageQuery;
    }

    public String getAsnNo() {
        return asnNo;
    }

}
