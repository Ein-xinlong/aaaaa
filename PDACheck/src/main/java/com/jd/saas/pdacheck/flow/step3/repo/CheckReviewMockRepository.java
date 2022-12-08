package com.jd.saas.pdacheck.flow.step3.repo;

import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSubmitOrderParam;
import com.jd.saas.pdacheck.net.CheckPagedResp;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

public class CheckReviewMockRepository extends CheckReviewRepository {
    @Override
    public void getSkuList(CheckReviewSkuListParam asnListDataParam, BaseObserver<CheckPagedResp<CheckReviewSkuResult>> observer) {
        BaseResponse<CheckPagedResp<CheckReviewSkuResult>> baseResponse = new BaseResponse<>();
        baseResponse.setCode(0);
        CheckPagedResp<CheckReviewSkuResult> item = new CheckPagedResp<>();
        ArrayList<CheckReviewSkuResult> itemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CheckReviewSkuResult skuResult = new CheckReviewSkuResult();
            skuResult.setSkuId(new Date().toString());
            skuResult.setSkuName("这个是测试商品名称");
            skuResult.setUpcCodes("124134314");
            skuResult.setLocName("便利店好品库位");
            skuResult.setOperator("U_13392040333");
            skuResult.setQty("10");
            skuResult.setActualQty("1");
            skuResult.setDiffAmount("10");
            skuResult.setDiffQty("10");
            skuResult.setInPrice("10");
            itemList.add(skuResult);
        }
        item.setItemList(itemList);
        baseResponse.setData(item);
        Observable.just(baseResponse).subscribe(observer);
    }

    @Override
    public void getPreOrderList(CheckReviewPreOrderListParam param, BaseObserver<List<CheckReviewPreOrderResult>> observer) {
        BaseResponse<List<CheckReviewPreOrderResult>> baseResponse = new BaseResponse<>();
        baseResponse.setCode(0);
        ArrayList<CheckReviewPreOrderResult> itemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CheckReviewPreOrderResult orderResult = new CheckReviewPreOrderResult();
            orderResult.setId(i + "");
            orderResult.setCouHNo("CUO1111111");
            orderResult.setActualQty("10");
            itemList.add(orderResult);
        }
        baseResponse.setData(itemList);
        Observable.just(baseResponse).subscribe(observer);
    }

    @Override
    public void submitPreOrderList(List<CheckReviewSubmitOrderParam> param, BaseObserver<Boolean> observer) {

    }

    @Override
    public void getQtyTypedCnt(CheckReviewQtyTypedCntParam param, BaseObserver<CheckReviewQtyTypedCntResult> observer) {
        BaseResponse<CheckReviewQtyTypedCntResult> baseResponse = new BaseResponse<>();
        baseResponse.setCode(0);
        CheckReviewQtyTypedCntResult data = new CheckReviewQtyTypedCntResult();
        data.setGainCouCount(111);
        data.setLossCouCount(111);
        data.setNoDiffCount(111);
        baseResponse.setData(data);
        Observable.just(baseResponse).subscribe(observer);
    }
}
