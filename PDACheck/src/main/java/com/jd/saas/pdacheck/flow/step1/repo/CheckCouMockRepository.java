package com.jd.saas.pdacheck.flow.step1.repo;

import com.jd.saas.pdacheck.flow.step1.bean.CheckCouListParam;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouResult;
import com.jd.saas.pdacheck.net.CheckPagedResp;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseResponse;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * 预盘单列表数据仓库
 * 本地数据仓库 仅调试使用
 *
 * @author gouhetong
 */
public class CheckCouMockRepository extends CheckCouRepository {

    @Override
    public void getQueryCouHeaderByPage(CheckCouListParam param, BaseObserver<CheckPagedResp<CheckCouResult>> observer) {
        BaseResponse<CheckPagedResp<CheckCouResult>> baseResponse = new BaseResponse<>();
        baseResponse.setCode(0);
        CheckPagedResp<CheckCouResult> item = new CheckPagedResp<>();
        ArrayList<CheckCouResult> itemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            itemList.add(new CheckCouResult());
        }
        item.setItemList(itemList);
        baseResponse.setData(item);
        Observable.just(baseResponse).subscribe(observer);
    }

    @Override
    public void getDelCouHeader(String couNo, BaseObserver<Boolean> observer) {
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setCode(0);
        baseResponse.setData(true);
        Observable.just(baseResponse).subscribe(observer);
    }
}
