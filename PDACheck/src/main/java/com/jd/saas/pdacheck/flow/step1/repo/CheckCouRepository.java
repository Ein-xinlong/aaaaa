package com.jd.saas.pdacheck.flow.step1.repo;

import com.jd.saas.pdacheck.flow.step1.bean.CheckCouListParam;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouResult;
import com.jd.saas.pdacheck.net.CheckPagedResp;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;

/**
 * 预盘单列表数据仓库
 * 包含数据逻辑
 *
 * @author gouhetong
 */
public abstract class CheckCouRepository extends BaseRepository {

    /**
     * 获取预盘单列表
     */
    public abstract void getQueryCouHeaderByPage(CheckCouListParam param, BaseObserver<CheckPagedResp<CheckCouResult>> observer);

    /**
     * 删除预盘单
     */
    public abstract void getDelCouHeader(String couNo, BaseObserver<Boolean> observer);
}

