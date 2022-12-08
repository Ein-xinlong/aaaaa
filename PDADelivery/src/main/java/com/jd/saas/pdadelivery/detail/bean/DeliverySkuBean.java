package com.jd.saas.pdadelivery.detail.bean;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.util.Formatter;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class DeliverySkuBean {
    private String skuId;
    private int skuType;
    private String skuName;
    private String[] upcCodes;
    /**
     * 销售单位
     */
    private String uom;
    private String logo;
    /**
     * [:BIGDECIMAL]实际收货数量
     */
    private BigDecimal actualQty = BigDecimal.ZERO;
    /**
     * [:BIGDECIMAL]预期收货数量
     */
    private BigDecimal expectedQty = BigDecimal.ZERO;


    private int shelfLife;

    /**
     * 天（day:1），月（month:2），年（year:3），小时（hour:4）
     */
    private int shelfLifeUnit;
    /**
     * 天（day:1），月（month:2），年（year:3），小时（hour:4）
     */
    private String shelfLifeUnitDesc;

    private boolean isShelfLifeSup;
    /**
     * 最大收货数量
     */
    private BigDecimal upperLimitReceived;
    private int saleMode;//1计件 2称重
    // ============ 以下为本地添加的属性 =================//
    /**
     * 收货单状态 进行中的收货单可以进行收货 修改临时属性
     */
    private int asnStatus;
    /**
     * 普通商品的本次收货数量
     */
    @NonNull
    private BigDecimal inputQty = BigDecimal.ZERO;
    /**
     * 因组合商品收货而收货的数量
     */
    @NonNull
    private BigDecimal combinedInputQty = BigDecimal.ZERO;
    /**
     * 普通商品的本次收货库位类型id
     */
    private String locId;
    /**
     * 普通商品的本次收货库位类型名称
     */
    private String locName;
    /**
     * 普通商品的本次收货库位类型的类型
     */
    private String locType;
    /**
     * 效期商品的本次收货信息
     */
    private List<DeliveryShelfLifeInfoBean> shelfLifeInfoList;
    /**
     * 是否可以折叠和展开
     */
    private boolean canFold = false;

    /**
     * 组合（箱规）商品
     */
    private List<DeliveryBoxProductBean> boxProducts;

    // ============ 以下展示用的临时属性不需要序列化 =================//
    private boolean isFold = true;
    private List<DeliverySkuLogBean> logBeans;

    public DeliverySkuBean() {
    }

    //=========显示用的计算属性 ===============//
    public String getInputQtyStr() {
        return Formatter.format(inputQty);

    }

    public String getActualQtyStr() {
        return Formatter.format(actualQty);
    }

    public String getExpectedQtyStr() {
        return Formatter.format(expectedQty);
    }

    public String getUnreceivedQtyStr() {
        if (expectedQty == null) {
            return "0";
        }
        if (actualQty != null) {
            return Formatter.format(expectedQty.subtract(actualQty));
        } else {
            return "0";
        }
    }

    public String getCombinedInputQtyStr() {
        return Formatter.format(combinedInputQty);
    }

    public int getCombinedInputQtyLayoutVisibility() {
        if (combinedInputQty.compareTo(BigDecimal.ZERO) > 0) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public int getEditLayoutVisibility() {
        if (asnStatus == AsnStatusEnum.RECEIVED.getValue() || asnStatus == AsnStatusEnum.DIFF_AUDIT.getValue()) {
            return View.GONE;
        } else {
            return View.VISIBLE;
        }
    }

    public int getNumLayoutVisibility() {
        if (asnStatus == AsnStatusEnum.RECEIVED.getValue() || asnStatus == AsnStatusEnum.DIFF_AUDIT.getValue()) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
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

    public int getArrowBtnVisibility() {
        if (canFold) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public Drawable getArrowRes() {
        if (isFold) {
            return ContextCompat.getDrawable(MyApplication.getInstance(), R.drawable.delivery_detail_down_clipper);
        } else {
            return ContextCompat.getDrawable(MyApplication.getInstance(), R.drawable.delivery_detail_up_clipper);
        }
    }

    @StringRes
    public int getFoldRes() {
        if (isFold) {
            return R.string.delivery_detail_look_detail;
        } else {
            return R.string.delivery_fold;
        }
    }

    public String getShelfLifeStr() {
        return shelfLife + shelfLifeUnitDesc;
    }

    public boolean isCombined() {
        return boxProducts != null && !boxProducts.isEmpty();
    }

    // ========================模版方法===========================//
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(BigDecimal expectedQty) {
        this.expectedQty = expectedQty;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public boolean isFold() {
        return isFold;
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }

    public List<DeliverySkuLogBean> getLogBeans() {
        return logBeans;
    }

    public void setLogBeans(List<DeliverySkuLogBean> logBeans) {
        this.logBeans = logBeans;
    }

    public void setAsnStatus(int asnStatus) {
        this.asnStatus = asnStatus;
    }

    public void setInputQty(@NonNull BigDecimal inputQty) {
        this.inputQty = inputQty;
    }

    @NotNull
    public BigDecimal getInputQty() {
        return inputQty;
    }

    public void setShelfLifeSup(boolean shelfLifeSup) {
        isShelfLifeSup = shelfLifeSup;
    }

    public int getShelfLifeUnit() {
        return shelfLifeUnit;
    }

    public void setShelfLifeUnit(int shelfLifeUnit) {
        this.shelfLifeUnit = shelfLifeUnit;
    }

    public String getShelfLifeUnitDesc() {
        return shelfLifeUnitDesc;
    }

    public void setShelfLifeUnitDesc(String shelfLifeUnitDesc) {
        this.shelfLifeUnitDesc = shelfLifeUnitDesc;
    }

    public boolean isShelfLifeSup() {
        return isShelfLifeSup;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getAsnStatus() {
        return asnStatus;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public List<DeliveryShelfLifeInfoBean> getShelfLifeInfoList() {
        return shelfLifeInfoList;
    }

    public void setShelfLifeInfoList(List<DeliveryShelfLifeInfoBean> shelfLifeInfoList) {
        this.shelfLifeInfoList = shelfLifeInfoList;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public BigDecimal getUpperLimitReceived() {
        return upperLimitReceived;
    }

    public void setUpperLimitReceived(BigDecimal upperLimitReceived) {
        this.upperLimitReceived = upperLimitReceived;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public int getSkuType() {
        return skuType;
    }

    public void setSkuType(int skuType) {
        this.skuType = skuType;
    }

    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

    public boolean isCanFold() {
        return canFold;
    }

    public void setCanFold(boolean canFold) {
        this.canFold = canFold;
    }

    public List<DeliveryBoxProductBean> getBoxProducts() {
        return boxProducts;
    }

    public void setBoxProducts(List<DeliveryBoxProductBean> boxProducts) {
        this.boxProducts = boxProducts;
    }

    @NonNull
    public BigDecimal getCombinedInputQty() {
        return combinedInputQty;
    }

    public void setCombinedInputQty(@NonNull BigDecimal combinedInputQty) {
        this.combinedInputQty = combinedInputQty;
    }
}
