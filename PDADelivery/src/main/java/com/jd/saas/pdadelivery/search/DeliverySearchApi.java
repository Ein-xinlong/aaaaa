package com.jd.saas.pdadelivery.search;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdadelivery.net.param.Param;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DeliverySearchApi {
    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> getGoodsList(@Body Param<SearchGoodsQueryBean> body);
}
