package com.jd.saas.pdagoodsquery.sale.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQueryReceipt;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQuerySale;

import java.util.HashMap;
import java.util.List;

/**
 * 进销存请求
 * @author ext.mengmeng
 */
public class GoodsQuerySaleRepository extends BaseRepository {

    GoodsQuerySaleApi api = ApiMgr.getApi(GoodsQuerySaleApi.class);

    public void getStockSale(HashMap<String,Object> body, BaseObserver<List<GoodsQuerySale>> observer){
        request(api.getStockSale(body),observer);
    }
    /*
    单据明细-请求
     */
    public void getReceiptDetails(HashMap<String,Object> body, BaseObserver<List<GoodsQueryReceipt>> observer){
        request(api.getReceiptDetails(body),observer);
    }

}
