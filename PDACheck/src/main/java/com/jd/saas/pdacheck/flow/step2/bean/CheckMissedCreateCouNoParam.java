package com.jd.saas.pdacheck.flow.step2.bean;

import com.jd.saas.pdacommon.user.UserManager;

public class CheckMissedCreateCouNoParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    private final String taskNo;

    public CheckMissedCreateCouNoParam(String taskNo) {
        this.taskNo = taskNo;
    }
}
