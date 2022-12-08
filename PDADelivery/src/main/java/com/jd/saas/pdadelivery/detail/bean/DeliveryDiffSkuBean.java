package com.jd.saas.pdadelivery.detail.bean;

import android.view.View;

import androidx.annotation.StringRes;

import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.util.Formatter;

import java.math.BigDecimal;

public class DeliveryDiffSkuBean {
    /**
     * 入库明细id
     */
    private Long asnDetailId;
    /**
     * 差异类型 1-缺收 2-超收
     */
    private int diffType;
    private String skuId;
    private String skuName;
    private String[] upcCodes;
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

    private BigDecimal diffQty = BigDecimal.ZERO;
    /**
     * 原因编码
     */
    private String reasonCode;
    /**
     * 原因编码描述
     */
    private String reasonDesc;

    //=========显示用的计算属性 ===============//

    public String getActualQtyStr() {
        return Formatter.format(actualQty);
    }

    public String getExpectedQtyStr() {
        return Formatter.format(expectedQty);
    }

    public String getDiffQtyStr() {
        return Formatter.format(diffQty);
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

    @StringRes
    public int getDiffLabelRes() {
        if (diffType == 2) {
            return R.string.delivery_diff_over_received_label;
        } else {
            return R.string.delivery_diff_unreceived_label;
        }
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

    public BigDecimal getDiffQty() {
        return diffQty;
    }

    public void setDiffQty(BigDecimal diffQty) {
        this.diffQty = diffQty;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public Long getAsnDetailId() {
        return asnDetailId;
    }

    public void setAsnDetailId(Long asnDetailId) {
        this.asnDetailId = asnDetailId;
    }

    public int getDiffType() {
        return diffType;
    }

    public void setDiffType(int diffType) {
        this.diffType = diffType;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }
}
