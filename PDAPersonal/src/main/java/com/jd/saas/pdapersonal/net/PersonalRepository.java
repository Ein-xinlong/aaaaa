package com.jd.saas.pdapersonal.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdapersonal.exchange.bean.PersonalCompanyItemBean;
import com.jd.saas.pdapersonal.store.bean.PersonalNewShopListBean;

import java.util.HashMap;
import java.util.List;

/**
 * 个人中心模块repository
 *
 * @author majiheng
 */
public class PersonalRepository extends BaseRepository {

    private final PersonalApi mPersonalApi = ApiMgr.getApi(PersonalApi.class);

    /**
     * 租户选择
     * */
    public void select(HashMap<String,Object> body,BaseObserver<List<PersonalCompanyItemBean>> observer){
        request(mPersonalApi.select(body),observer);
    }

    /**
     * 获取门店列表信息
     */
    public void getShopList(HashMap<String,Object> body, BaseObserver<List<PersonalNewShopListBean>> observer) {
        request(mPersonalApi.getShopList(body),observer);
    }

    /**
     * 租户id转accountId or accountId转租户id
     * */
    public void convert(HashMap<String,Object> body,BaseObserver<String> observer){
        request(mPersonalApi.convert(body),observer);
    }
}
