package com.jd.saas.pdacheck.flow.step3.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.jd.saas.pdacheck.util.CheckFormatter;

import java.util.Arrays;

public class CheckReviewSkuBean implements Parcelable {
    private int serial;
    private String skuName;
    private String skuId;
    private int skuType;
    private String[] upcCodes;

    public CheckReviewSkuBean() {
    }

    protected CheckReviewSkuBean(Parcel in) {
        serial = in.readInt();
        skuName = in.readString();
        skuId = in.readString();
        skuType = in.readInt();
        upcCodes = in.createStringArray();
        uom = in.readString();
        stockQty = in.readString();
        operator = in.readString();
        checkQty = in.readString();
        diffQty = in.readString();
        diffAmount = in.readString();
        avgAmount = in.readString();
        locNo = in.readString();
        locName = in.readString();
        locType = in.readString();
        saleMode = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(serial);
        dest.writeString(skuName);
        dest.writeString(skuId);
        dest.writeInt(skuType);
        dest.writeStringArray(upcCodes);
        dest.writeString(uom);
        dest.writeString(stockQty);
        dest.writeString(operator);
        dest.writeString(checkQty);
        dest.writeString(diffQty);
        dest.writeString(diffAmount);
        dest.writeString(avgAmount);
        dest.writeString(locNo);
        dest.writeString(locName);
        dest.writeString(locType);
        dest.writeInt(saleMode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckReviewSkuBean> CREATOR = new Creator<CheckReviewSkuBean>() {
        @Override
        public CheckReviewSkuBean createFromParcel(Parcel in) {
            return new CheckReviewSkuBean(in);
        }

        @Override
        public CheckReviewSkuBean[] newArray(int size) {
            return new CheckReviewSkuBean[size];
        }
    };

    public String getUpcCode() {
        if (upcCodes != null && upcCodes.length > 0) {
            return upcCodes[0];
        } else {
            return null;
        }
    }
    //=========显示用的计算属性 ===============//
    public String getStockQtyStr() {
        return CheckFormatter.formatEmpty(stockQty);
    }

    public String getCheckQtyStr() {
        return CheckFormatter.formatEmpty(checkQty);
    }

    public String getDiffQtyStr() {
        return CheckFormatter.formatEmpty(diffQty);
    }

    public String getAvgAmountStr() {
        return CheckFormatter.formatEmpty(avgAmount);
    }

    public String getDiffAmountStr() {
        return CheckFormatter.formatEmpty(diffAmount);
    }
    /** 销售单位 */
    private String uom;
    /** 库存数量 */
    private String stockQty;

    /** 盘点人 */
    private String operator;

    /** 盘点数量 */
    private String checkQty;
    /** 差异数量 */
    private String diffQty;
    /** 差异金额 */
    private String diffAmount;
    /** 加权平均数量 */
    private String avgAmount;
    /**
     * 普通商品的本次收货库位类型id
     */
    private String locNo;
    /**
     * 普通商品的本次收货库位类型名称
     */
    private String locName;
    /**
     * 普通商品的本次收货库位类型的类型
     */
    private String locType;
    /**
     * 1计件 2称重
     */
    private int saleMode;


    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCheckQty() {
        return checkQty;
    }

    public void setCheckQty(String checkQty) {
        this.checkQty = checkQty;
    }

    public String getDiffQty() {
        return diffQty;
    }

    public void setDiffQty(String diffQty) {
        this.diffQty = diffQty;
    }

    public String getDiffAmount() {
        return diffAmount;
    }

    public void setDiffAmount(String diffAmount) {
        this.diffAmount = diffAmount;
    }

    public String getAvgAmount() {
        return avgAmount;
    }

    public void setAvgAmount(String avgAmount) {
        this.avgAmount = avgAmount;
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
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

    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckReviewSkuBean that = (CheckReviewSkuBean) o;

        if (serial != that.serial) return false;
        if (skuType != that.skuType) return false;
        if (saleMode != that.saleMode) return false;
        if (skuName != null ? !skuName.equals(that.skuName) : that.skuName != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(upcCodes, that.upcCodes)) return false;
        if (uom != null ? !uom.equals(that.uom) : that.uom != null) return false;
        if (stockQty != null ? !stockQty.equals(that.stockQty) : that.stockQty != null)
            return false;
        if (operator != null ? !operator.equals(that.operator) : that.operator != null)
            return false;
        if (checkQty != null ? !checkQty.equals(that.checkQty) : that.checkQty != null)
            return false;
        if (diffQty != null ? !diffQty.equals(that.diffQty) : that.diffQty != null) return false;
        if (diffAmount != null ? !diffAmount.equals(that.diffAmount) : that.diffAmount != null)
            return false;
        if (avgAmount != null ? !avgAmount.equals(that.avgAmount) : that.avgAmount != null)
            return false;
        if (locNo != null ? !locNo.equals(that.locNo) : that.locNo != null) return false;
        if (locName != null ? !locName.equals(that.locName) : that.locName != null) return false;
        return locType != null ? locType.equals(that.locType) : that.locType == null;
    }

    @Override
    public int hashCode() {
        int result = serial;
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + skuType;
        result = 31 * result + Arrays.hashCode(upcCodes);
        result = 31 * result + (uom != null ? uom.hashCode() : 0);
        result = 31 * result + (stockQty != null ? stockQty.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (checkQty != null ? checkQty.hashCode() : 0);
        result = 31 * result + (diffQty != null ? diffQty.hashCode() : 0);
        result = 31 * result + (diffAmount != null ? diffAmount.hashCode() : 0);
        result = 31 * result + (avgAmount != null ? avgAmount.hashCode() : 0);
        result = 31 * result + (locNo != null ? locNo.hashCode() : 0);
        result = 31 * result + (locName != null ? locName.hashCode() : 0);
        result = 31 * result + (locType != null ? locType.hashCode() : 0);
        result = 31 * result + saleMode;
        return result;
    }
}
