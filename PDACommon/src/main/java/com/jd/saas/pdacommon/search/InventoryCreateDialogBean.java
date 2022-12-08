package com.jd.saas.pdacommon.search;

import java.io.Serializable;

/**
 *
 * @author: ext.anxinlong
 * @date: 2021/6/30
 */
public class InventoryCreateDialogBean implements Serializable {

    private String locCode;
    private String locName;
    private String locType;
    private int locId;
    private String qtyAvailable;
    private String qtyTotal;

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(String qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public String getQtyTotal() {
        return qtyTotal;
    }

    public void setQtyTotal(String qtyTotal) {
        this.qtyTotal = qtyTotal;
    }
}
