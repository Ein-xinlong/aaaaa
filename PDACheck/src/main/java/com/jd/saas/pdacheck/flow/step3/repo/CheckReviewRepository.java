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
import com.jd.saas.pdacommon.net.BaseRepository;

import java.util.List;

/**
 * 复盘核对 数据仓库
 *
 * @author gouhetong
 */
public abstract class CheckReviewRepository extends BaseRepository {
    /**
     * 获取商品列表
     */
    public abstract void getSkuList(CheckReviewSkuListParam param, BaseObserver<CheckPagedResp<CheckReviewSkuResult>> observer);

    /**
     * 获取商品预盘单列表
     */
    public abstract void getPreOrderList(CheckReviewPreOrderListParam param, BaseObserver<List<CheckReviewPreOrderResult>> observer);

    /**
     * 提交商品预盘单列表
     */
    public abstract void submitPreOrderList(List<CheckReviewSubmitOrderParam> param, BaseObserver<Boolean> observer);

    /**
     * 查询各个分类下的商品数量
     */
    public abstract void getQtyTypedCnt(CheckReviewQtyTypedCntParam param, BaseObserver<CheckReviewQtyTypedCntResult> observer);

}
