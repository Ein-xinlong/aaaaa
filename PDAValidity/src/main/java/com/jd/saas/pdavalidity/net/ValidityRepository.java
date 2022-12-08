package com.jd.saas.pdavalidity.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdavalidity.detail.bean.ValidityAdjustDateResponseBean;

import java.util.HashMap;

/**
 * 效期调整模块repository
 *
 * @author majiheng
 */
public class ValidityRepository extends BaseRepository {

    private final ValidityApi mValidityApi = ApiMgr.getApi(ValidityApi.class);

    /**
     * 模糊搜索商品列表
     */
    public void getGoodsList(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer) {
        request(mValidityApi.getGoodsList(body),observer);
    }

    /**
     * 获取效期详情
     */
    public void getDetail(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer) {
        request(mValidityApi.getDetail(body),observer);
    }

    /**
     * 根据sku&生产日期查询商品效期状态
     */
    public void getDate(HashMap<String,Object> body, BaseObserver<ValidityAdjustDateResponseBean> observer) {
        request(mValidityApi.getDate(body),observer);
    }

    /**
     * 修改提交接口
     */
    public void commit(HashMap<String,Object> body, BaseObserver<Boolean> observer) {
        request(mValidityApi.commit(body),observer);
    }
}
