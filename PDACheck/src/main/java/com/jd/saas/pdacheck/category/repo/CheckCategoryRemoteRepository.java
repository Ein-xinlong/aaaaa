package com.jd.saas.pdacheck.category.repo;

import com.jd.saas.pdacheck.category.CheckCategoryConvertor;
import com.jd.saas.pdacheck.category.bean.CheckCategoryListReqParam;
import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacheck.category.bean.CheckCategoryRespItem;
import com.jd.saas.pdacheck.net.CheckCommonParamWrapper;
import com.jd.saas.pdacheck.net.CheckListApi;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckCategoryRemoteRepository extends CheckCategoryRepository {

    private final CheckListApi mApi = ApiMgr.getApi(CheckListApi.class);


    @Override
    public Observable<BaseResponse<List<CheckCategoryNode>>> getCategoryList(CheckCategoryListReqParam param) {
        return mApi.queryCategoryList(new CheckCommonParamWrapper<>(param.getData()))
                .subscribeOn(Schedulers.io())
                .map(listBaseResponse -> {
                    BaseResponse<List<CheckCategoryNode>> baseResponse = new BaseResponse<>();
                    baseResponse.setCode(listBaseResponse.getCode());
                    baseResponse.setMsg(listBaseResponse.getMsg());
                    List<CheckCategoryRespItem> data = listBaseResponse.getData();
                    if (data != null) {
                        baseResponse.setData(ListUtils.map(data, CheckCategoryConvertor::covert));
                    } else {
                        baseResponse.setData(new ArrayList<>());
                    }
                    return baseResponse;
                });
    }
}
