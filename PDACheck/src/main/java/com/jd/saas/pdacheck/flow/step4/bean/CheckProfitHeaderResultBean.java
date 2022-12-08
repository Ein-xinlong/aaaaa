package com.jd.saas.pdacheck.flow.step4.bean;

import android.text.TextUtils;

import com.jd.saas.pdacheck.util.CheckFormatter;

import java.math.BigDecimal;

/**
 * 损益单单头详情返回体
 *
 * @author majiheng
 */
public class CheckProfitHeaderResultBean {

    // 盘盈总条数
    private String gainCouCount;
    // 盘亏总条数
    private String lossCouCount;
    // 无差异总条数
    private String noDiffCount;
    // 盘损总金额
    private String lossAmount;
    // 盘盈总金额
    private String gainAmount;
    // 损益总金额
    private String totalGalPrice;

    public String getLossAmount() {
        if(TextUtils.isEmpty(lossAmount)) {
            lossAmount = "0";
        }
        return lossAmount;
    }

    public void setLossAmount(String lossAmount) {
        this.lossAmount = lossAmount;
    }

    public String getGainAmount() {
        if(TextUtils.isEmpty(gainAmount)) {
            gainAmount = "0";
        }
        return gainAmount;
    }

    public void setGainAmount(String gainAmount) {
        this.gainAmount = gainAmount;
    }

    public String getTotalGalPrice() {
        if(TextUtils.isEmpty(totalGalPrice)) {
            totalGalPrice = "0";
        }
        return totalGalPrice;
    }

    public void setTotalGalPrice(String totalGalPrice) {
        this.totalGalPrice = totalGalPrice;
    }

    public String getGainCouCount() {
        if(TextUtils.isEmpty(gainCouCount)) {
            gainCouCount = "0";
        }
        return gainCouCount;
    }

    public void setGainCouCount(String gainCouCount) {
        this.gainCouCount = gainCouCount;
    }

    public String getLossCouCount() {
        if(TextUtils.isEmpty(lossCouCount)) {
            lossCouCount = "0";
        }
        return lossCouCount;
    }

    public void setLossCouCount(String lossCouCount) {
        this.lossCouCount = lossCouCount;
    }

    public String getNoDiffCount() {
        if(TextUtils.isEmpty(noDiffCount)) {
            noDiffCount = "0";
        }
        return noDiffCount;
    }

    public void setNoDiffCount(String noDiffCount) {
        this.noDiffCount = noDiffCount;
    }

}
