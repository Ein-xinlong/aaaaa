package com.jd.saas.pdacheck.check.bean;

import java.util.List;

/**
 * 校验当前盘点商品的接口
 *
 * @author majiheng
 */
public class CheckGoodResultRequestBean {

    private String tenantId;
    private String whId;
    private String taskNo;
    private List<String> skuIds;
    private String locType;

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

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public List<String> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(List<String> skuIds) {
        this.skuIds = skuIds;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }
}
