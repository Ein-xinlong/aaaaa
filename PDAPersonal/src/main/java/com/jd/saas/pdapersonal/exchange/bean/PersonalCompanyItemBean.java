package com.jd.saas.pdapersonal.exchange.bean;

import java.io.Serializable;

/**
 * 企业列表条目bean
 *
 * @author majiheng
 */
public class PersonalCompanyItemBean implements Serializable {

    private int tenantId;
    private String tenantName;
    private String accountId;
    private int bgType;

    public int getBgType() {
        return bgType;
    }

    public void setBgType(int bgType) {
        this.bgType = bgType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
