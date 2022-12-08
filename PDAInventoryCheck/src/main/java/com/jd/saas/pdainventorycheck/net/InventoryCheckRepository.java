package com.jd.saas.pdainventorycheck.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdainventorycheck.inquire.bean.InventoryCheckGoodsBean;

import java.util.HashMap;

/**
 * 库存查询api管理
 * @author: ext.anxinlong
 * @date: 2021/7/14
 */
public class InventoryCheckRepository extends BaseRepository {
    private final InventoryCheckInquireApi mInventoryCheckInquireApi= ApiMgr.getApi(InventoryCheckInquireApi.class);
    /**
     * 模糊搜索商品列表
     */
    public void getGoodsList(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer) {
        request(mInventoryCheckInquireApi.getGoodsList(body),observer);
    }

    /**
     * 模糊搜索商品列表
     */
    public void Goods(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer) {
        request(mInventoryCheckInquireApi.getGoods(body),observer);
    }
}
