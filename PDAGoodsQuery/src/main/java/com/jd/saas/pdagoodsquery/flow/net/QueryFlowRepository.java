package com.jd.saas.pdagoodsquery.flow.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdagoodsquery.flow.model.GoodsQueryFlowResponse;

import java.util.HashMap;

/**
 * 库存流水请求
 * @author ext.mengmeng
 */
public class QueryFlowRepository extends BaseRepository {

    QueryFlowApi api = ApiMgr.getApi(QueryFlowApi.class);

    public void getQueryFlow(HashMap<String,Object> body, BaseObserver<GoodsQueryFlowResponse> observer){
        request(api.getQueryFlow(body),observer);
    }

}
