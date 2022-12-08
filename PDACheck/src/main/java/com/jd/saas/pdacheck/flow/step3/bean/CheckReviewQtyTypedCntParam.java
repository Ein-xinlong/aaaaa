package com.jd.saas.pdacheck.flow.step3.bean;

import com.jd.saas.pdacommon.user.UserManager;

public class CheckReviewQtyTypedCntParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    private final String taskNo;

    public CheckReviewQtyTypedCntParam(String taskNo) {
        this.taskNo = taskNo;
    }
}
