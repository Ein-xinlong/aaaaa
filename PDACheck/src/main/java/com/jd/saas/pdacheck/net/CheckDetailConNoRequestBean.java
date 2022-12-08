package com.jd.saas.pdacheck.net;

/**
 * 获取预盘点单号
 *
 * @author majiheng
 */
public class CheckDetailConNoRequestBean {

    private String tenantId;
    private String whId;
    private String deptCode;
    private String skuType;

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}
