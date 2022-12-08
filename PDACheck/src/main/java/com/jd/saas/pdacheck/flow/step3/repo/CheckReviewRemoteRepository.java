package com.jd.saas.pdacheck.flow.step3.repo;

import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSubmitOrderParam;
import com.jd.saas.pdacheck.net.CheckCommonParamWrapper;
import com.jd.saas.pdacheck.net.CheckListApi;
import com.jd.saas.pdacheck.net.CheckPagedResp;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;

import java.util.List;

public class CheckReviewRemoteRepository extends CheckReviewRepository {
    private final CheckListApi mApi = ApiMgr.getApi(CheckListApi.class);

    @Override
    public void getSkuList(CheckReviewSkuListParam param, BaseObserver<CheckPagedResp<CheckReviewSkuResult>> observer) {
        request(mApi.getReviewSkuList(new CheckCommonParamWrapper<>(param)), observer);
    }

    @Override
    public void getPreOrderList(CheckReviewPreOrderListParam param, BaseObserver<List<CheckReviewPreOrderResult>> observer) {
        request(mApi.getReviewSkuPreCheckOrderList(new CheckCommonParamWrapper<>(param)), observer);
    }

    @Override
    public void submitPreOrderList(List<CheckReviewSubmitOrderParam> param, BaseObserver<Boolean> observer) {
        request(mApi.submitReviewSkuPreOrderList(new CheckCommonParamWrapper<>(param)), observer);
    }

    @Override
    public void getQtyTypedCnt(CheckReviewQtyTypedCntParam param, BaseObserver<CheckReviewQtyTypedCntResult> observer) {
        request(mApi.getQtyTypedCnt(new CheckCommonParamWrapper<>(param)), observer);
    }
}
