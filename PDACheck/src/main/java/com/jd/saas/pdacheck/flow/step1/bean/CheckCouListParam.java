package com.jd.saas.pdacheck.flow.step1.bean;

import com.jd.saas.pdacommon.user.UserManager;

/**
 * 请求预盘单列表的入参
 *
 * @author gouhetong
 */
public class CheckCouListParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    private final String pin = UserManager.getInstance().getUserData().getUserPin();
    /**
     * 盘点任务号
     */
    private final String taskNo;
    private final int pageNo;
    private final int pageSize;

    public CheckCouListParam(String taskNo, int pageNo, int pageSize) {
        this.taskNo = taskNo;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
