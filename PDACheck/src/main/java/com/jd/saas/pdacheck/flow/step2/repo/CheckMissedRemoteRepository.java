package com.jd.saas.pdacheck.flow.step2.repo;

import com.jd.saas.pdacheck.flow.step2.CheckMissedConvertor;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedCreateCouNoParam;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuListReqParam;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuResp;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSubmitParam;
import com.jd.saas.pdacheck.net.CheckCommonParamWrapper;
import com.jd.saas.pdacheck.net.CheckListApi;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetCodeConstant;
import com.jd.saas.pdacommon.net.exception.GateWayException;
import com.jd.saas.pdacommon.utils.ListUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckMissedRemoteRepository extends CheckMissedRepository {
    private final CheckListApi api = ApiMgr.getApi(CheckListApi.class);

    @Override
    public void getSkuList(CheckMissedSkuListReqParam param, BaseObserver<List<CheckMissedSkuResp>> observer) {
        request(api.getMissedSkuList(new CheckCommonParamWrapper<>(param)), observer);
    }

    @Override
    public void submit(final String taskNo, final List<CheckMissedSkuBean> submitList, BaseObserver<Boolean> observer) {
        api.createSubmittedCouNo(new CheckCommonParamWrapper<>(new CheckMissedCreateCouNoParam(taskNo)))
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    if (response.getCode() == NetCodeConstant.OK_200) {
                        return response.getData();
                    }
                    throw new GateWayException(response);
                })
                .map(couNo -> new CheckMissedSubmitParam(taskNo, couNo, ListUtils.map(submitList, from -> CheckMissedConvertor.convert(taskNo, from))))
                .flatMap(param -> api.submitMissedSkuList(new CheckCommonParamWrapper<>(param)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
