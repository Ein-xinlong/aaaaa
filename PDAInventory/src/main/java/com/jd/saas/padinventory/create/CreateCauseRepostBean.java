package com.jd.saas.padinventory.create;

/**
 *
 * 新建原因入参data
 * @author: ext.anxinlong
 * @date: 2021/6/11
 */
public class CreateCauseRepostBean {
    private String tenantId;
    private String reasonCode;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}
