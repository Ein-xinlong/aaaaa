package com.jd.saas.pdadelivery.net.param;

public class QueryDiffReasonParam {
    private String parentCode;
    private final String bizCode = "RECEIVE";

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
