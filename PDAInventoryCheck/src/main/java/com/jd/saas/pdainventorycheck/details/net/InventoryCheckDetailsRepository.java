package com.jd.saas.pdainventorycheck.details.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckFlowResponse;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckLocationResponse;

import java.util.HashMap;

/**
 * 库存流水请求
 * @author ext.mengmeng
 */
public class InventoryCheckDetailsRepository extends BaseRepository {

    InventoryCheckDetailsApi api = ApiMgr.getApi(InventoryCheckDetailsApi.class);

    public void getQueryFlow(HashMap<String,Object> body, BaseObserver<InventoryCheckFlowResponse> observer){
        request(api.getQueryFlow(body),observer);
    }

    public void getLocation(HashMap<String,Object> body, BaseObserver<InventoryCheckLocationResponse> observer){
        request(api.getLocation(body),observer);
    }


}
