package com.jd.saas.pdagoodsquery.goods.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdagoodsquery.goods.bean.QueryGoodsDetailBean;

import java.util.HashMap;
import java.util.List;

public class GoodsQueryRepository extends BaseRepository {
    private final GoodsQueryApi api = ApiMgr.getApi(GoodsQueryApi.class);

    /*
    根据商品名称、编码 获取商品名称
     */
    public void getQueryGoods(HashMap<String,Object> body, BaseObserver<List<Object>> observer){
        request(api.getQueryGoods(body),observer);
    }
    /*
   根据商品名称、编码 获取商品名称
    */
    public void obtainQueryGoods(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer){
        request(api.obtainQueryGoods(body),observer);
    }
    /*
  根据商品skuId获取商品sku
   */
    public void getGoodsById(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer){
        request(api.getQueryGoodsInfo(body),observer);
    }
}
