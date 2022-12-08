package com.jd.saas.pdacheck.flow.step2.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.util.CheckFormatter;
import com.jd.saas.pdacommon.application.MyApplication;

import java.math.BigDecimal;

/**
 * 漏盘商品+库位信息
 */
public class CheckMissedSkuBean implements Parcelable {
    private String skuName;
    private String skuId;
    private int skuType;
    private String[] upcCodes;
    /** 销售单位 */
    private String uom;
    private String stockQty;
    /** 库位类型名称 */
    private String locName;
    /** 库位类型的编码 */
    private String locCode;
    /** 库位类型id */
    private String locNo;
    /** 1计件 2称重 */
    private int saleMode;

    /**
     * 普通商品的本次收货数量
     */
    @Nullable
    private BigDecimal inputQty;

    public CheckMissedSkuBean() {
    }

    protected CheckMissedSkuBean(Parcel in) {
        skuName = in.readString();
        skuId = in.readString();
        skuType = in.readInt();
        upcCodes = in.createStringArray();
        uom = in.readString();
        stockQty = in.readString();
        locName = in.readString();
        locCode = in.readString();
        locNo = in.readString();
        saleMode = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(skuName);
        dest.writeString(skuId);
        dest.writeInt(skuType);
        dest.writeStringArray(upcCodes);
        dest.writeString(uom);
        dest.writeString(stockQty);
        dest.writeString(locName);
        dest.writeString(locCode);
        dest.writeString(locNo);
        dest.writeInt(saleMode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckMissedSkuBean> CREATOR = new Creator<CheckMissedSkuBean>() {
        @Override
        public CheckMissedSkuBean createFromParcel(Parcel in) {
            return new CheckMissedSkuBean(in);
        }

        @Override
        public CheckMissedSkuBean[] newArray(int size) {
            return new CheckMissedSkuBean[size];
        }
    };

    //=========显示用的计算属性 ===============//
    public String getStockQtyStr() {
        return CheckFormatter.formatEmpty(stockQty);
    }

    public String getUpcCode() {
        if (upcCodes != null && upcCodes.length > 0) {
            return upcCodes[0];
        } else {
            return null;
        }
    }

    public int getUpcMoreBtnVisibility() {
        if (upcCodes != null && upcCodes.length > 1) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public String getArrowBtnText() {
        if (inputQty == null) {
            return MyApplication.getInstance().getString(R.string.check_missed_uncheck);
        }
        return CheckFormatter.format(inputQty);
    }

    @ColorInt
    public int getArrowBtnTextColor() {
        if (inputQty == null) {
            return MyApplication.getInstance().getResources().getColor(com.jd.saas.pdacommon.R.color.color_text_grey);
        }
        return MyApplication.getInstance().getResources().getColor(R.color.toolbar_menu_color);
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getSkuType() {
        return skuType;
    }

    public void setSkuType(int skuType) {
        this.skuType = skuType;
    }

    public String[] getUpcCodes() {
        return upcCodes;
    }

    public void setUpcCodes(String[] upcCodes) {
        this.upcCodes = upcCodes;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getStockQty() {
        return stockQty;
    }

    public void setStockQty(String stockQty) {
        this.stockQty = stockQty;
    }


    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

    public String getLocCode() {
        return locCode;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    @Nullable
    public BigDecimal getInputQty() {
        return inputQty;
    }

    public void setInputQty(@Nullable BigDecimal inputQty) {
        this.inputQty = inputQty;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

    public boolean isSameBean(CheckMissedSkuBean skuBean) {
        if (skuBean == null) {
            return false;
        }
        boolean equalsId = skuId.equals(skuBean.getSkuId());
        boolean equalsLoc;
        if (locCode == null) {
            equalsLoc = skuBean.locCode == null;
        } else {
            equalsLoc = locCode.equals(skuBean.getLocCode());
        }
        return equalsId && equalsLoc;
    }

}
