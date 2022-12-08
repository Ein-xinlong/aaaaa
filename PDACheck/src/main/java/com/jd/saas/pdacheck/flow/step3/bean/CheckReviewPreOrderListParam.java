package com.jd.saas.pdacheck.flow.step3.bean;

import com.jd.saas.pdacommon.user.UserManager;

public class CheckReviewPreOrderListParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    private final String taskNo;
    private final String skuId;
    private final String locNo;


    public CheckReviewPreOrderListParam(String taskNo, String skuId, String locNo) {
        this.taskNo = taskNo;
        this.skuId = skuId;
        this.locNo = locNo;
    }
}
