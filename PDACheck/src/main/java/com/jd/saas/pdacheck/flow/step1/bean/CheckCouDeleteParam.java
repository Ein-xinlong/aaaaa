package com.jd.saas.pdacheck.flow.step1.bean;

import com.jd.saas.pdacommon.user.UserManager;

/**
 * 删除预盘单的入参
 *
 * @author gouhetong
 */
public class CheckCouDeleteParam {
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String whId = UserManager.getInstance().getUserData().getShopId();
    private final String pin = UserManager.getInstance().getUserData().getUserPin();
    private final String couNo;


    public CheckCouDeleteParam(String couNo) {
        this.couNo = couNo;
    }
}
