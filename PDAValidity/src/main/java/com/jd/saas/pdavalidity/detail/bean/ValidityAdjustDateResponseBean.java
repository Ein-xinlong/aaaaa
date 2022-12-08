package com.jd.saas.pdavalidity.detail.bean;

/**
 * 获取效期时间返回体
 *
 * @author majiheng
 */
public class ValidityAdjustDateResponseBean {

    // 效期状态：0：全部 1：正常 2：提示 3：预警 4：失效
    private String periodState;
    // 预警日期
    private String warnDate;
    // 提示日期
    private String hintDate;
    // 失效日期
    private String expireDate;

    public String getPeriodState() {
        return periodState;
    }

    public void setPeriodState(String periodState) {
        this.periodState = periodState;
    }

    public String getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(String warnDate) {
        this.warnDate = warnDate;
    }

    public String getHintDate() {
        return hintDate;
    }

    public void setHintDate(String hintDate) {
        this.hintDate = hintDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

}
