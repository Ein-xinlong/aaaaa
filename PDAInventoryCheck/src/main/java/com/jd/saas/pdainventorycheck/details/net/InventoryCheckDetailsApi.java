package com.jd.saas.pdainventorycheck.details.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckFlowResponse;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckLocationResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InventoryCheckDetailsApi {

    @POST("/app/api/Inventory/pagingQueryStockExchangeByLot")
    Observable<BaseResponse<InventoryCheckFlowResponse>> getQueryFlow(@Body HashMap<String,Object> body);

    @POST("/app/api/StockQuery/pagingQueryStockByLocation")
    Observable<BaseResponse<InventoryCheckLocationResponse>> getLocation(@Body HashMap<String,Object> body);
}
