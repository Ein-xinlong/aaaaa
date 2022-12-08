package com.jd.saas.pdapersonal.store.bean;

/**
 * 「仓」app的标示bean
 *
 * @author majiheng
 */
public class PersonalShopCangBean {

    // 门店：1，仓：2，默认门店
    private int bizCode = 1;

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }
}
