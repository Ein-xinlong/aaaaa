package com.jd.saas.pdagoodsquery.goods.bean;

import java.util.List;

public class QueryGoodsResponseData {
    int page;
    int pageSize;
    String total;
    List<GoodsQueryBean> data;

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setData(List<GoodsQueryBean> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getTotal() {
        return total;
    }

    public List<GoodsQueryBean> getData() {
        return data;
    }
}
