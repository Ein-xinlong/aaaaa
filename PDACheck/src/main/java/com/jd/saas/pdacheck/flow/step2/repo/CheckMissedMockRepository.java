package com.jd.saas.pdacheck.flow.step2.repo;

import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuListReqParam;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuResp;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.net.NetCodeConstant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class CheckMissedMockRepository extends CheckMissedRepository {
    @Override
    public void getSkuList(CheckMissedSkuListReqParam param, BaseObserver<List<CheckMissedSkuResp>> observer) {
        BaseResponse<List<CheckMissedSkuResp>> baseResponse = new BaseResponse<>();
        baseResponse.setCode(0);
        ArrayList<CheckMissedSkuResp> itemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CheckMissedSkuResp skuResp = new CheckMissedSkuResp();
            skuResp.setSkuId(String.valueOf(System.nanoTime()));
            skuResp.setSkuName("这个是测试商品名称");
            skuResp.setUpcCodes("124134314;124134314");
            skuResp.setLocName("便利店好品库位");
            skuResp.setQty("10");
            itemList.add(skuResp);
        }
        baseResponse.setData(itemList);
        Observable.just(baseResponse).subscribe(observer);
    }

    @Override
    public void submit(String taskNo, List<CheckMissedSkuBean> submitList, BaseObserver<Boolean> observer) {
        BaseResponse<Boolean> response = new BaseResponse<>();
        response.setCode(NetCodeConstant.OK_200);
        response.setData(true);
        Observable.just(response).subscribe(observer);
    }
}
