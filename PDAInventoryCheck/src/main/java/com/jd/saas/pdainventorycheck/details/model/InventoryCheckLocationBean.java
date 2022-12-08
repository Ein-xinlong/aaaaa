package com.jd.saas.pdainventorycheck.details.model;

import java.util.Date;

/**
 * 库存流水列表adapter
 *
 * @author majiheng
 */
public class InventoryCheckLocationBean {

    private String locId;//库位ID-
    private String locCode;//库位编码
    private String locType;//库位类型
    private String locName;//库位类型
    private String qtyTotal;//总库存
    private String qtyAvailable;//可用库存
    private String qtyAllocated;//预占库存

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public void setQtyTotal(String qtyTotal) {
        this.qtyTotal = qtyTotal;
    }

    public void setQtyAvailable(String qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public void setQtyAllocated(String qtyAllocated) {
        this.qtyAllocated = qtyAllocated;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocName() {
        return locName;
    }

    public String getLocCode() {
        return locCode;
    }

    public String getLocType() {
        return locType;
    }

    public String getQtyTotal() {
        return qtyTotal;
    }

    public String getQtyAvailable() {
        return qtyAvailable;
    }

    public String getQtyAllocated() {
        return qtyAllocated;
    }
}
