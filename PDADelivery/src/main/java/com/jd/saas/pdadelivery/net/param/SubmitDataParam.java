package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdacommon.user.UserManager;

import java.util.ArrayList;

public class SubmitDataParam {
    private String warehouseId = UserManager.getInstance().getUserData().getShopId();
    private String asnNo;
    private ArrayList<SubmitRcvSkuParam> submitRcvSkuDtos;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public ArrayList<SubmitRcvSkuParam> getSubmitRcvSkuDtos() {
        return submitRcvSkuDtos;
    }

    public void setSubmitRcvSkuDtos(ArrayList<SubmitRcvSkuParam> submitRcvSkuDtos) {
        this.submitRcvSkuDtos = submitRcvSkuDtos;
    }
}
