package com.jd.saas.pdadelivery.net;

import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryPrintParam;
import com.jd.saas.pdadelivery.net.enums.AsnTypeEnum;
import com.jd.saas.pdadelivery.net.enums.LocTypeEnum;
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
import com.jd.saas.pdadelivery.net.result.QueryDiffReasonResult;
import com.jd.saas.pdadelivery.net.result.QueryLocationsResult;
import com.jd.saas.pdadelivery.net.result.QueryRcvDiffListResult;
import com.jd.saas.pdadelivery.net.result.RcvHeaderInfoResult;
import com.jd.saas.pdadelivery.net.result.RcvStockInfoResult;
import com.jd.saas.pdadelivery.net.result.StockDetailResult;
import com.jd.saas.pdadelivery.net.result.StockExchangeResult;
import com.jd.saas.pdadelivery.net.result.StockItemResult;
import com.jd.saas.pdadelivery.util.Formatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

public class MockDeliveryRepository implements DeliveryRepository {
    private final DeliveryApi api;

    public MockDeliveryRepository(DeliveryApi api) {
        this.api = api;
    }

    @Override
    public void asnList(Param<AsnListDataParam> param, BaseObserver<PagedResult<StockItemResult>> observer) {
        List<StockItemResult> beans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            StockItemResult bean = new StockItemResult();
            bean.setAsnNo(param.getData().getStatusEnumsList() + "");
            bean.setAsnRefNo("MOCK外部单号PO" + i);
            bean.setAsnType(AsnTypeEnum.PURCHASE_ORDER.getValue());
            bean.setSupplierCode("30");
            bean.setSupplierName("MOCK供应商");
            bean.setContactName("MOCK负责人");
            bean.setContactTelephone("MOCK负责人电话");
            bean.setCreateDate(Formatter.formatParam(new Date()));
            bean.setUpdateDate(Formatter.formatParam(new Date()));
            bean.setStatus(param.getData().getStatusEnumsList().get(0));
            beans.add(bean);
        }
        PagedResult<StockItemResult> pagedResult = new PagedResult<>();
        pagedResult.setItemList(beans);
        BaseResponse<PagedResult<StockItemResult>> baseResponse = new BaseResponse<>();
        baseResponse.setData(pagedResult);
        Observable.just(baseResponse).subscribe(observer);
    }

    @Override
    public void queryRcvSkuStock(Param<QueryRcvSkuStockDataParam> param, BaseObserver<StockDetailResult> observer) {
        ArrayList<RcvStockInfoResult> beans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            RcvStockInfoResult bean = new RcvStockInfoResult();
            bean.setSkuId(i + "");

            bean.setUom("MOCK单位");
            if (i == 1) {
                bean.setSkuName("MOCK多码商品" + i);
                bean.setUpcCodes("MOCK条码1;MOCK条码2");
            } else {
                bean.setSkuName("MOCK商品" + i);
                bean.setUpcCodes("MOCK条码");
            }
            bean.setShelfLifeSup(i == 2);
            bean.setSkuType(i % 3 + 1);
            bean.setShelfLife(180);
            bean.setExpectedQty(BigDecimal.TEN);
            beans.add(bean);
        }
        StockDetailResult stockDetailResult = new StockDetailResult();
        RcvHeaderInfoResult rcvHeaderInfoDto = new RcvHeaderInfoResult();
        rcvHeaderInfoDto.setAsnType(AsnTypeEnum.PURCHASE_ORDER.getValue());
        rcvHeaderInfoDto.setStatus(Integer.parseInt(param.getData().getDocNo()));
        rcvHeaderInfoDto.setAsnNo(param.getData().getDocNo());
        rcvHeaderInfoDto.setCreateDate(Formatter.formatParam(new Date()));
        rcvHeaderInfoDto.setCloseTime(Formatter.formatParam(new Date()));
        stockDetailResult.setRcvHeaderInfoDto(rcvHeaderInfoDto);
        stockDetailResult.setRcvStockInfoDtos(beans);
        BaseResponse<StockDetailResult> baseResponse = new BaseResponse<>();
        baseResponse.setData(stockDetailResult);
        Observable.just(baseResponse).subscribe(observer);
    }

    @Override
    public void submitRcv(Param<SubmitDataParam> param, BaseObserver<String> observer) {

    }

    @Override
    public void receiveAsnFinish(Param<ReceiveAsnFinishDataParam> param, BaseObserver<String> observer) {

    }

    @Override
    public void receiveAsnFinishByDiff(Param<ReceiveAsnFinishDataParam> param, BaseObserver<String> observer) {

    }

    @Override
    public void getGoodsList(Param<SearchGoodsQueryBean> body, BaseObserver<SearchResultBean> observer) {

    }

    @Override
    public void queryPoPrintInfo(Param<DeliveryPrintParam> body, BaseObserver<DirectDeliveryOrderBean> observer) {

    }

    @Override
    public void queryLocationsByPage(Param<QueryLocationsDataParam> param, BaseObserver<PagedResult<QueryLocationsResult>> observer) {
//        api.queryLocationsByPage(param)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
        ArrayList<QueryLocationsResult> beans = new ArrayList<>();
        LocTypeEnum[] locTypeEnums = LocTypeEnum.values();
        for (int i = 0; i < locTypeEnums.length; i++) {
            LocTypeEnum item = locTypeEnums[i];
            QueryLocationsResult bean = new QueryLocationsResult();
            bean.setLocName(item.name());
            bean.setLocCode(item.name());
            bean.setLocId(i);
            bean.setLocType(item.getValue());
            beans.add(bean);
        }
        BaseResponse<PagedResult<QueryLocationsResult>> baseResponse = new BaseResponse<>();
        PagedResult<QueryLocationsResult> pagedResult = new PagedResult<>();
        pagedResult.setItemList(beans);
        baseResponse.setData(pagedResult);
        Observable.just(baseResponse).subscribe(observer);
    }

    @Override
    public void pagingQueryStockExchangeByLot(Param<StockExchangeParam> param, BaseObserver<PagedResult<StockExchangeResult>> observer) {

    }

    @Override
    public void queryExpiryDateGoodsStatus(Param<QueryExpiryDateGoodsStatusDataParam> param, BaseObserver<Integer> observer) {

    }

    @Override
    public void queryRcvDiffList(Param<QueryRcvDiffListParam> param, BaseObserver<DiffListAndDiffReasonResult> observer) {
        BaseResponse<DiffListAndDiffReasonResult> baseResponse = new BaseResponse<>();
        PagedResult<QueryRcvDiffListResult> pagedResult = new PagedResult<>();
        ArrayList<QueryRcvDiffListResult> itemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            QueryRcvDiffListResult result = new QueryRcvDiffListResult();
            result.setSkuId(i + "");
//            result.setUom("MOCK单位");
            if (i == 1) {
                result.setSkuName("MOCK多码商品" + i);
                result.setUpcCodes("MOCK条码1;MOCK条码2");
            } else {
                result.setSkuName("MOCK商品" + i);
                result.setUpcCodes("MOCK条码");
            }
            result.setActualQty(BigDecimal.TEN);
            result.setExpectedQty(BigDecimal.ZERO);
            result.setDiffQty(BigDecimal.ONE);
//            result.setReasonCode(result.getReasonCode());
//            result.setReasonDesc(result.getReasonDesc());
            result.setAsnDetailId(0L);
            result.setDiffType(i % 2 + 1);
            itemList.add(result);
        }
        pagedResult.setItemList(itemList);
        baseResponse.setData(new DiffListAndDiffReasonResult(pagedResult, makeDiffReasonResults(), makeDiffReasonResults()));
        Observable.just(baseResponse).subscribe(observer);
    }


    private List<QueryDiffReasonResult> makeDiffReasonResults() {
        //        PagedResult<QueryDiffReasonResult> pagedResult = new PagedResult<>();
//        pagedResult.setItemList(itemList);
        ArrayList<QueryDiffReasonResult> itemList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            QueryDiffReasonResult data = new QueryDiffReasonResult();
            data.setDictCode(i + "");
            data.setDictName("MOCK 原因" + i);
            itemList.add(data);
        }
        return itemList;
    }
}

