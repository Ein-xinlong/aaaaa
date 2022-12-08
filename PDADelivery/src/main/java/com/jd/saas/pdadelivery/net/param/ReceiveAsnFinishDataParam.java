package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;

import java.util.List;

public class ReceiveAsnFinishDataParam {
    private String tenantId = UserManager.getInstance().getUserData().getTenantId();
    private String warehouseId = UserManager.getInstance().getUserData().getShopId();
    private String asnNo;
    private String operator = UserManager.getInstance().getUserData().getUserPin();

    private List<DiffInfoParam> diffResultDTOS;

    public ReceiveAsnFinishDataParam(String asnNo) {
        this.asnNo = asnNo;
    }

    public void setDiffResultDTOS(List<DiffInfoParam> diffResultDTOS) {
        this.diffResultDTOS = diffResultDTOS;
    }
}
