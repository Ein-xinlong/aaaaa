package com.jd.saas.padinventory.create;

/**
 * 新建页面库位入参
 * @author: ext.anxinlong
 * @date: 2021/6/11
 */
public class CreateStorageRepostBean  {
    private String tenantId;
    private String warehouseId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
}
