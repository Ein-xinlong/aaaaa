package com.jd.saas.pdadelivery.detail.bean;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.net.enums.ShelfLifeStateEnum;
import com.jd.saas.pdadelivery.util.Formatter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/5
 */
public class DeliveryShelfLifeInfoBean {
    private final long localId = System.nanoTime();
    private Date createDate;
    private String locName;
    private String locId;
    private String locType;
    private String inputQtyStr = "0";
    /**
     * 效期状态
     * 0全部1正常2提示3预警4失效
     */
    private Integer state = ShelfLifeStateEnum.ALL.getValue();

    public String getExpiryTips() {
        if (state == null) {
            return "";
        } else if (state == ShelfLifeStateEnum.EXPIRE.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_detail_expiry_state_4_tip);
        } else if (state == ShelfLifeStateEnum.WARN.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_detail_expiry_state_3_tip);
        } else if (state == ShelfLifeStateEnum.TIP.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_detail_expiry_state_2_tip);
        } else {
            return "";
        }
    }

    public BigDecimal getInputQty() {
        if (".".equals(inputQtyStr)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(inputQtyStr);
    }

    public String getCreateDateStr() {
        return Formatter.formatYMDDate(createDate);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public void setInputQtyStr(String inputQtyStr) {
        this.inputQtyStr = inputQtyStr;
    }

    public String getInputQtyStr() {
        return inputQtyStr;
    }

    public String getLocId() {
        return locId;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public long getLocalId() {
        return localId;
    }
}
