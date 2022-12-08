package com.jd.saas.pdacheck.flow.step2.repo;

import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuListReqParam;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuResp;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;

import java.util.List;

/**
 * 漏盘商品列表数据仓库
 */
public abstract class CheckMissedRepository extends BaseRepository {

    /**
     * 获取漏盘商品列表
     */
    public abstract void getSkuList(CheckMissedSkuListReqParam param, BaseObserver<List<CheckMissedSkuResp>> observer);

    /**
     * 提交漏盘商品信息
     */
    public abstract void submit(String taskNo, List<CheckMissedSkuBean> submitList, BaseObserver<Boolean> observer);
}

