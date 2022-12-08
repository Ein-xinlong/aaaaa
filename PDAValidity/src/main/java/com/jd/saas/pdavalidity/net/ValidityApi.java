package com.jd.saas.pdavalidity.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdavalidity.detail.bean.ValidityAdjustDateResponseBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 效期调整模块网络请求
 *
 * @author majiheng
 */
public interface ValidityApi {

    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> getGoodsList(@Body HashMap<String,Object> body);

    @POST("/app/api/stockGal/queryExpiryDateInfo")
    Observable<BaseResponse<SearchResultBean>> getDetail(@Body HashMap<String,Object> body);

    @POST("/app/api/stockGal/queryExpiryDateGoodsStatusAndGoodsDate")
    Observable<BaseResponse<ValidityAdjustDateResponseBean>> getDate(@Body HashMap<String,Object> body);

    @POST("/app/api/stockGal/modifyExpiryDate")
    Observable<BaseResponse<Boolean>> commit(@Body HashMap<String,Object> body);

}
