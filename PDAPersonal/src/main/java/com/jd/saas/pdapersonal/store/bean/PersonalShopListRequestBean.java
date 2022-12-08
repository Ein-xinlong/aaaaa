package com.jd.saas.pdapersonal.store.bean;

/**
 * 获取门店列表的入参bean
 *
 * @author majiheng
 */
public class PersonalShopListRequestBean {

    // 租户id
    private String tenantId;
    // 用户pin
    private String pin;
    // 该字段可传可不传，后端为了优化速度用
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

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
