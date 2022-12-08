package com.jd.saas.pdagoodsquery.sale.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQueryReceipt;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQuerySale;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GoodsQuerySaleApi {

    @POST("/api/goodsjxc")
    Observable<BaseResponse<List<GoodsQuerySale>>> getStockSale(@Body HashMap<String,Object> body);

    @POST("/api/goods/purchase")
    Observable<BaseResponse<List<GoodsQueryReceipt>>> getReceiptDetails(@Body HashMap<String,Object> body);
}
