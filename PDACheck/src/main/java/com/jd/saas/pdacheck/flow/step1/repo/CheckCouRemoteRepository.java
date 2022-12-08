package com.jd.saas.pdacheck.flow.step1.repo;

import com.jd.saas.pdacheck.flow.step1.bean.CheckCouDeleteParam;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouListParam;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouResult;
import com.jd.saas.pdacheck.net.CheckCommonParamWrapper;
import com.jd.saas.pdacheck.net.CheckListApi;
import com.jd.saas.pdacheck.net.CheckPagedResp;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;

/**
 * 预盘单列表数据仓库
 * 远程数据仓库 实际发送网络请求与后端交互
 *
 * @author gouhetong
 */
public class CheckCouRemoteRepository extends CheckCouRepository {
    private final CheckListApi mApi = ApiMgr.getApi(CheckListApi.class);


    @Override
    public void getQueryCouHeaderByPage(CheckCouListParam param, BaseObserver<CheckPagedResp<CheckCouResult>> observer) {
        request(mApi.getQueryCouHeaderByPage(new CheckCommonParamWrapper<>(param)), observer);
    }

    /**
     * 获取损益明细列表
     */
    public void getDelCouHeader(String couNo, BaseObserver<Boolean> observer) {
        request(mApi.delCouHeader(new CheckCommonParamWrapper<>(new CheckCouDeleteParam(couNo))), observer);
    }
}
