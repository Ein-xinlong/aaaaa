package com.jd.saas.pdacheck.flow.step2.bean;

import com.jd.saas.pdacommon.user.UserManager;

import java.util.List;

public class CheckMissedSubmitParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    private final String pin = UserManager.getInstance().getUserData().getUserPin();
    private final String taskNo;
    private final String couNo;
    private final List<CheckMissedSubmitSkuParam> couDetailList;


    public CheckMissedSubmitParam(String taskNo, String couNo, List<CheckMissedSubmitSkuParam> couDetailList) {
        this.taskNo = taskNo;
        this.couNo = couNo;
        this.couDetailList = couDetailList;
    }
}
