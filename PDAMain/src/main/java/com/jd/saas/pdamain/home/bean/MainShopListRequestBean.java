package com.jd.saas.pdamain.home.bean;

/**
 * 获取门店列表的入参bean
 *
 * @author majiheng
 */
public class MainShopListRequestBean {

    // 租户id
    private String tenantId;
    // 用户pin
    private String pin;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
