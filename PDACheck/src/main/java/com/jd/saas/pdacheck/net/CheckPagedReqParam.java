package com.jd.saas.pdacheck.net;

public class CheckPagedReqParam {
    private int pageNo;
    private int pageSize;
//    private int offset;

    public CheckPagedReqParam(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
