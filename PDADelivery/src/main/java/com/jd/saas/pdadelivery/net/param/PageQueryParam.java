package com.jd.saas.pdadelivery.net.param;

public class PageQueryParam {
    private int pageNo;
    private int pageSize;
//    private int offset;

    public PageQueryParam(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
