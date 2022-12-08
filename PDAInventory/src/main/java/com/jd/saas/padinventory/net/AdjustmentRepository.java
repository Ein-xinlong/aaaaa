package com.jd.saas.padinventory.net;

import com.jd.saas.padinventory.adjustment.InventoryAdjustmentItemBean;
import com.jd.saas.padinventory.create.InventoryCreateCauseRequestBean;
import com.jd.saas.padinventory.create.InventoryCreateStorageResponseBean;
import com.jd.saas.padinventory.create.InventoryGoodsBean;
import com.jd.saas.padinventory.stock.InventoryStockRepostBean;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.search.SearchResultBean;

import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.List;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/10
 */
public class AdjustmentRepository extends BaseRepository {

    private final AdjustmentApi mAdjustmentApi= ApiMgr.getApi(AdjustmentApi.class);


    /**
     * 损益单列表
     *
     * */
    public void stock(HashMap<String,Object> body, BaseObserver<InventoryStockRepostBean> observer){
        request(mAdjustmentApi.stock(body),observer);
    }


    /**
     * 库位
     *
     * */
    public void storage(HashMap<String,Object> body, BaseObserver<InventoryCreateStorageResponseBean> observer){
        request(mAdjustmentApi.storage(body),observer);
    }

    /**
     * 原因
     *
     * */
    public void cause(HashMap<String,Object> body, BaseObserver<List<String>> observer){
        request(mAdjustmentApi.cause(body),observer);
    }

    /**
     *
     *
     * */
    public void detail(HashMap<String,Object> body, BaseObserver<InventoryAdjustmentItemBean> observer){
        request(mAdjustmentApi.detail(body),observer);
    }

    /**
     * 模糊搜索商品列表
     */
    public void getGoodsList(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer) {
        request(mAdjustmentApi.getGoodsList(body),observer);
    }

    /**
     * 模糊搜索商品列表
     */
    public void Goods(HashMap<String,Object> body, BaseObserver<SearchResultBean> observer) {
        request(mAdjustmentApi.Goods(body),observer);
    }
    /**
     * 模糊搜索商品列表
     */
    public void add(HashMap<String,Object> body, BaseObserver<Boolean> observer) {
        request(mAdjustmentApi.add(body),observer);
    }



}
