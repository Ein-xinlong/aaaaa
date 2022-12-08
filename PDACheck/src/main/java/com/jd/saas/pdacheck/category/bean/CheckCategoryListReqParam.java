package com.jd.saas.pdacheck.category.bean;

import com.jd.saas.pdacommon.user.UserManager;

/**
 * 全类目信息请求入参
 */
public class CheckCategoryListReqParam {
    private final String data = UserManager.getInstance().getUserData().getUserPin();

    public String getData() {
        return data;
    }
}
