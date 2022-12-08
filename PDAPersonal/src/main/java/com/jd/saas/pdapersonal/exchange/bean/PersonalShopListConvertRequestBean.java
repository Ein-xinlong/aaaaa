package com.jd.saas.pdapersonal.exchange.bean;

/**
 * 租户id转accountId接口入参
 *
 * @author majiheng
 */
public class PersonalShopListConvertRequestBean {

    // 租户id或accountId
    private String accountIdOrTenantId;
    // 转换类型，1：租户Id 转 jnos AccountId  2：jnos AccountId 转 租户Id
    private int type;

    public String getAccountIdOrTenantId() {
        return accountIdOrTenantId;
    }

    public void setAccountIdOrTenantId(String accountIdOrTenantId) {
        this.accountIdOrTenantId = accountIdOrTenantId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
