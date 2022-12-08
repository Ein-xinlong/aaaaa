package com.jd.saas.pdadelivery.net;

import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryPrintParam;
import com.jd.saas.pdadelivery.net.param.AsnListDataParam;
import com.jd.saas.pdadelivery.net.param.Param;
import com.jd.saas.pdadelivery.net.param.QueryExpiryDateGoodsStatusDataParam;
import com.jd.saas.pdadelivery.net.param.QueryLocationsDataParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvDiffListParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvSkuStockDataParam;
import com.jd.saas.pdadelivery.net.param.ReceiveAsnFinishDataParam;
import com.jd.saas.pdadelivery.net.param.StockExchangeParam;
import com.jd.saas.pdadelivery.net.param.SubmitDataParam;
import com.jd.saas.pdadelivery.net.result.DiffListAndDiffReasonResult;
import com.jd.saas.pdadelivery.net.result.PagedResult;
import com.jd.saas.pdadelivery.net.result.QueryLocationsResult;
import com.jd.saas.pdadelivery.net.result.StockDetailResult;
import com.jd.saas.pdadelivery.net.result.StockExchangeResult;
import com.jd.saas.pdadelivery.net.result.StockItemResult;

public interface DeliveryRepository {
    void asnList(Param<AsnListDataParam> param, BaseObserver<PagedResult<StockItemResult>> observer);

    void queryRcvSkuStock(Param<QueryRcvSkuStockDataParam> param, BaseObserver<StockDetailResult> observer);

    void submitRcv(Param<SubmitDataParam> param, BaseObserver<String> observer);

    void receiveAsnFinish(Param<ReceiveAsnFinishDataParam> param, BaseObserver<String> observer);

    void queryLocationsByPage(Param<QueryLocationsDataParam> param, BaseObserver<PagedResult<QueryLocationsResult>> observer);

    void pagingQueryStockExchangeByLot(Param<StockExchangeParam> param, BaseObserver<PagedResult<StockExchangeResult>> observer);

    void queryExpiryDateGoodsStatus(Param<QueryExpiryDateGoodsStatusDataParam> param, BaseObserver<Integer> observer);

    void queryRcvDiffList(Param<QueryRcvDiffListParam> param, BaseObserver<DiffListAndDiffReasonResult> observer);

    void receiveAsnFinishByDiff(Param<ReceiveAsnFinishDataParam> param, BaseObserver<String> observer);

    void getGoodsList(Param<SearchGoodsQueryBean> body, BaseObserver<SearchResultBean> observer);

    void queryPoPrintInfo(Param<DeliveryPrintParam> body, BaseObserver<DirectDeliveryOrderBean> observer);
}

