package com.jd.saas.pdagoodsquery.flow.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdagoodsquery.flow.model.GoodsQueryFlowResponse;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface QueryFlowApi {

    @POST("/app/api/Inventory/pagingQueryStockExchangeByLot")
    Observable<BaseResponse<GoodsQueryFlowResponse>> getQueryFlow(@Body HashMap<String,Object> body);
}
