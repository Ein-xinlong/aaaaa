package com.jd.saas.pdamain.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.user.UserTenantConfigBean;
import com.jd.saas.pdamain.home.bean.MainMenuItemBean;
import com.jd.saas.pdamain.home.bean.MainPickingListBean;
import com.jd.saas.pdamain.home.bean.MainShopListBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 首页模块网络请求
 *
 * @author majiheng
 */
public interface MainApi {

    @POST("/app/api/store/queryShopKeyListByPin")
    Observable<BaseResponse<List<MainShopListBean>>> getShopList(@Body HashMap<String,Object> body);

    @POST("/app/api/menu/newList")
    Observable<BaseResponse<List<MainMenuItemBean>>> getMenus(@Body HashMap<String,Object> body);

    @POST("/app/api/pick/list")
    Observable<BaseResponse<MainPickingListBean>> getPickingList(@Body HashMap<String,Object> body);

    @POST("/app/api/config/getConfig")
    Observable<BaseResponse<UserTenantConfigBean>> getConfig(@Body HashMap<String,Object> body);
}
