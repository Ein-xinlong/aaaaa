package com.jd.saas.padinventory.net;

import com.jd.saas.padinventory.adjustment.InventoryAdjustmentItemBean;
import com.jd.saas.padinventory.create.InventoryCreateCauseRequestBean;
import com.jd.saas.padinventory.create.InventoryCreateStorageResponseBean;
import com.jd.saas.padinventory.create.InventoryGoodsBean;
import com.jd.saas.padinventory.stock.InventoryStockRepostBean;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.search.SearchResultBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/10
 */
public interface AdjustmentApi {
    /**
     * 损益单列表
     * */
    @POST("/app/api/list/queryHeadersByPage")
    Observable<BaseResponse<InventoryStockRepostBean>> stock(@Body HashMap<String, Object> body);


    /**
     *库位信息
     */

    @POST("/app/api/Inventory/queryLocationsByPage")
    Observable<BaseResponse<InventoryCreateStorageResponseBean>> storage(@Body HashMap<String,Object> body);


    /**
     *新建原因
     */

    @POST("/app/api/stockAfc/searchReason")
    Observable<BaseResponse<List<String>>> cause(@Body HashMap<String,Object> body);


    /**
     *根据损益单id查询损益单详情
     */
    @POST("/app/api/stockGal/query")
    Observable<BaseResponse<InventoryAdjustmentItemBean>> detail(@Body HashMap<String,Object> body);



    /**
     * 模糊搜索
     * */
    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> getGoodsList(@Body HashMap<String,Object> body);


    /**
     * 商品详情
     * */
    @POST("/app/api/sku/queryByCode")
    Observable<BaseResponse<SearchResultBean>> Goods(@Body HashMap<String,Object> body);

    /**
     * 创建批量商品损益单
     * */
    @POST("/app/api/stockGal/add")
    Observable<BaseResponse<Boolean>> add(@Body HashMap<String,Object> body);
}
