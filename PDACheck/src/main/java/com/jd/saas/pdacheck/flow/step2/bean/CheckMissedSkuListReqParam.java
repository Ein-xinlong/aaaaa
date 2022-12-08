package com.jd.saas.pdacheck.flow.step2.bean;

import androidx.annotation.NonNull;

import com.jd.saas.pdacheck.net.CheckPagedReqParam;
import com.jd.saas.pdacommon.user.UserManager;

/**
 * 漏盘商品列表的请求入参
 */
public class CheckMissedSkuListReqParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    /**
     * 盘点任务ID
     */
    @NonNull
    private final String taskNo;

    private final Integer qtyFlag;


    public CheckMissedSkuListReqParam(@NonNull String taskNo, Integer qtyFlag) {
        this.taskNo = taskNo;
        this.qtyFlag = qtyFlag;
    }
}
