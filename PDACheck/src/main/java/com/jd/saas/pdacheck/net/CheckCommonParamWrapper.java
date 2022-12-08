package com.jd.saas.pdacheck.net;

import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.utils.UUIDUtils;

public class CheckCommonParamWrapper<T> {
    //    private final String serialVersionUID
    private final String requestId = UUIDUtils.v4();
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String pin = UserManager.getInstance().getUserData().getUserPin();
    private final CheckClientInfoParam clientInfo = new CheckClientInfoParam();
    private final T data;

    public CheckCommonParamWrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}