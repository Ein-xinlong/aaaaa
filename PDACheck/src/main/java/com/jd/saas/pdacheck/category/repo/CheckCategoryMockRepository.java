package com.jd.saas.pdacheck.category.repo;

import com.jd.saas.pdacheck.category.bean.CheckCategoryListReqParam;
import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jingdong.jdma.common.utils.LogUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CheckCategoryMockRepository extends CheckCategoryRepository {


    @Override
    public Observable<BaseResponse<List<CheckCategoryNode>>> getCategoryList(CheckCategoryListReqParam param) {

        return Observable.just(CheckCategoryTreeUtil.mockLevel)
                .subscribeOn(Schedulers.io())
                .map(mockLevel -> {
                    BaseResponse<List<CheckCategoryNode>> baseResponse = new BaseResponse<>();
                    baseResponse.setCode(0);
                    long startTime = System.currentTimeMillis();
                    baseResponse.setData(CheckCategoryTreeUtil.generateResp(mockLevel, CheckCategoryTreeUtil.mockCntPerLevel));
                    LogUtil.d("categoryTree", "generateResp耗时" + (System.currentTimeMillis() - startTime));
                    return baseResponse;
                });
    }
}
