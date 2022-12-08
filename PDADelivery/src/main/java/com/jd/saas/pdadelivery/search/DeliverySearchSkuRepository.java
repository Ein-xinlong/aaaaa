package com.jd.saas.pdadelivery.search;

import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdadelivery.net.param.Param;

import java.util.HashMap;

public class DeliverySearchSkuRepository extends BaseRepository {
    private final DeliverySearchApi api;

    public DeliverySearchSkuRepository(DeliverySearchApi api) {
        this.api = api;
    }

    public void getGoodsList(Param<SearchGoodsQueryBean> body, BaseObserver<SearchResultBean> observer) {
        request(api.getGoodsList(body), observer);
    }
}
