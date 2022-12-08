package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.utils.UUIDUtils;

public class Param<T> {
    //    private final String serialVersionUID
    private final String requestId = UUIDUtils.v4();
    private final String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private final String pin = UserManager.getInstance().getUserData().getUserPin();
    private final ClientInfoParam clientInfo = new ClientInfoParam();
    private final T data;

    public Param(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}