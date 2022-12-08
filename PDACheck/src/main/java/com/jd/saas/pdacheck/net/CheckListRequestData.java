package com.jd.saas.pdacheck.net;

/**
 * 盘点列表入参bean
 *
 * @author majiheng
 */
public class CheckListRequestData {

    private String tenantId;
    private String whId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }
}
