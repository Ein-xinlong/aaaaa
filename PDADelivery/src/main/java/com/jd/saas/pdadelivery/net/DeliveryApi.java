package com.jd.saas.pdadelivery.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryPrintParam;
import com.jd.saas.pdadelivery.net.param.AsnListDataParam;
import com.jd.saas.pdadelivery.net.param.QueryDiffReasonParam;
import com.jd.saas.pdadelivery.net.param.Param;
import com.jd.saas.pdadelivery.net.param.QueryExpiryDateGoodsStatusDataParam;
import com.jd.saas.pdadelivery.net.param.QueryLocationsDataParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvDiffListParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvSkuStockDataParam;
import com.jd.saas.pdadelivery.net.param.ReceiveAsnFinishDataParam;
import com.jd.saas.pdadelivery.net.param.StockExchangeParam;
import com.jd.saas.pdadelivery.net.param.SubmitDataParam;
import com.jd.saas.pdadelivery.net.result.QueryDiffReasonResult;
import com.jd.saas.pdadelivery.net.result.PagedResult;
import com.jd.saas.pdadelivery.net.result.QueryLocationsResult;
import com.jd.saas.pdadelivery.net.result.QueryRcvDiffListResult;
import com.jd.saas.pdadelivery.net.result.StockDetailResult;
import com.jd.saas.pdadelivery.net.result.StockExchangeResult;
import com.jd.saas.pdadelivery.net.result.StockItemResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DeliveryApi {
    /**
     * 查询入库单列表
     */
    @POST("/app/api/stockGal/asnList")
    Observable<BaseResponse<PagedResult<StockItemResult>>> asnList(@Body Param<AsnListDataParam> param);

    /**
     * 查询入库单详情
     */
    @POST("/app/api/stockGal/queryRcvSkuStock")
    Observable<BaseResponse<StockDetailResult>> queryRcvSkuStock(@Body Param<QueryRcvSkuStockDataParam> param);

    /**
     * 提交收货
     */
    @POST("/app/api/stockGal/submitRcv")
    Observable<BaseResponse<String>> submitRcv(@Body Param<SubmitDataParam> param);

    /**
     * 关单
     */
    @POST("/app/api/stockGal/receiveAsnFinish")
    Observable<BaseResponse<String>> receiveAsnFinish(@Body Param<ReceiveAsnFinishDataParam> param);

    /**
     * 关单 含有差异单
     */
    @POST("/app/api/stockGal/receiveAsnFinishByDiff")
    Observable<BaseResponse<String>> receiveAsnFinishByDiff(@Body Param<ReceiveAsnFinishDataParam> param);

    /**
     * 库位信息
     */
    @POST("/app/api/Inventory/queryLocationsByPage")
    Observable<BaseResponse<PagedResult<QueryLocationsResult>>> queryLocationsByPage(@Body Param<QueryLocationsDataParam> body);

    /**
     * 库存流水
     */
    @POST("/app/api/Inventory/pagingQueryStockExchangeByLot")
    Observable<BaseResponse<PagedResult<StockExchangeResult>>> pagingQueryStockExchangeByLot(@Body Param<StockExchangeParam> body);

    /**
     * 根据sku&生产日期查询商品效期状态
     */
    @POST("/app/api/stockGal/queryExpiryDateGoodsStatus")
    Observable<BaseResponse<Integer>> queryExpiryDateGoodsStatus(@Body Param<QueryExpiryDateGoodsStatusDataParam> body);


    /**
     * 查询收货差异
     */
    @POST("/app/api/receiveDiff/queryRcvDiffList")
    Observable<BaseResponse<PagedResult<QueryRcvDiffListResult>>> queryRcvDiffList(@Body Param<QueryRcvDiffListParam> body);

    /**
     * 查询收货差异原因
     */
    @POST("/app/api/receiveDiff/queryDiffReason")
    Observable<BaseResponse<List<QueryDiffReasonResult>>> queryDiffReason(@Body Param<QueryDiffReasonParam> body);

    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> getGoodsList(@Body Param<SearchGoodsQueryBean> body);

    @POST("/app/api/stockPrint/queryPoPrintInfo")
    Observable<BaseResponse<DirectDeliveryOrderBean>> queryPoPrintInfo(@Body Param<DeliveryPrintParam> body);
}

