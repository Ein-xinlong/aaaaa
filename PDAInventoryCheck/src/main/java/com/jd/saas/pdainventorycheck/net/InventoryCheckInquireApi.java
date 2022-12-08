package com.jd.saas.pdainventorycheck.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdainventorycheck.inquire.bean.InventoryCheckGoodsBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 库存查询api
 * @author: ext.anxinlong
 * @date: 2021/7/13
 */
public interface InventoryCheckInquireApi {
    /**
     * 模糊搜索
     * */
    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> getGoodsList(@Body HashMap<String,Object> body);


    /**
     * 模糊搜索
     * */
    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> getGoods(@Body HashMap<String,Object> body);
}
