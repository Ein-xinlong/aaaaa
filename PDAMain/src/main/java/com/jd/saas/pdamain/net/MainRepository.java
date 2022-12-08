package com.jd.saas.pdamain.net;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.user.UserTenantConfigBean;
import com.jd.saas.pdamain.home.bean.MainMenuItemBean;
import com.jd.saas.pdamain.home.bean.MainPickingListBean;
import com.jd.saas.pdamain.home.bean.MainShopListBean;

import java.util.HashMap;
import java.util.List;

/**
 * 首页模块repository
 *
 * @author majiheng
 */
public class MainRepository extends BaseRepository {

    private final MainApi mMainApi = ApiMgr.getApi(MainApi.class);

    /**
     * 获取门店列表信息
     */
    public void getShopList(HashMap<String,Object> body, BaseObserver<List<MainShopListBean>> observer) {
        request(mMainApi.getShopList(body),observer);
    }

    /**
     * 获取菜单列表
     */
    public void getMenus(HashMap<String,Object> body, BaseObserver<List<MainMenuItemBean>> observer) {
        request(mMainApi.getMenus(body),observer);
    }

    /**
     * 获取拣货列表信息
     * */
    public void getPickingList(HashMap<String,Object> body, BaseObserver<MainPickingListBean> observer) {
        request(mMainApi.getPickingList(body),observer);
    }

    /**
     * 获取租户全局配置信息
     * */
    public void getConfig(HashMap<String,Object> body, BaseObserver<UserTenantConfigBean> observer) {
        request(mMainApi.getConfig(body),observer);
    }
}
