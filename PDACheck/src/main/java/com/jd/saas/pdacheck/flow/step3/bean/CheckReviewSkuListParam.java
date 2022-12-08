package com.jd.saas.pdacheck.flow.step3.bean;

import com.jd.saas.pdacommon.user.UserManager;

public class CheckReviewSkuListParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    private final String taskNo;

    private final Integer searchKey;
    private final Integer searchValue;
    private final int qtyFlag;
    private final int pageNo;
    private final int pageSize;

    public CheckReviewSkuListParam(String taskNo, int qtyFlag, Integer searchKey, Integer searchValue, int pageNo, int pageSize) {
        this.taskNo = taskNo;
        this.searchKey = searchKey;
        this.searchValue = searchValue;
        this.qtyFlag = qtyFlag;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getTaskNo() {
        return taskNo;
    }
}
