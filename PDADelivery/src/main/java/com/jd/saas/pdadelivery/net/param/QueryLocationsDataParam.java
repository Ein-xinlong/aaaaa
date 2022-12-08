package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;

public class QueryLocationsDataParam {
    private String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private String warehouseId = UserManager.getInstance().getUserData().getShopId();

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
