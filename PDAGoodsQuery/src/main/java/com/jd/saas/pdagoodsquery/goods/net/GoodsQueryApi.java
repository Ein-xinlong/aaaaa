package com.jd.saas.pdagoodsquery.goods.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdagoodsquery.goods.bean.QueryGoodsDetailBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GoodsQueryApi {

    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<List<Object>>> getQueryGoods(@Body HashMap<String,Object> body);

    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> obtainQueryGoods(@Body HashMap<String,Object> body);

    @POST("/app/api/sku/queryByCode")
    Observable<BaseResponse<SearchResultBean>> getQueryGoodsInfo(@Body HashMap<String,Object> body);
}
