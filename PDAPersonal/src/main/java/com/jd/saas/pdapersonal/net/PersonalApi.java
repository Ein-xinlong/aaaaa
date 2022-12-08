package com.jd.saas.pdapersonal.net;

import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdapersonal.exchange.bean.PersonalCompanyItemBean;
import com.jd.saas.pdapersonal.store.bean.PersonalNewShopListBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 个人中心模块网络请求
 *
 * @author majiheng
 */
public interface PersonalApi {

    @POST("/app/api/tenant/queryAll")
    Observable<BaseResponse<List<PersonalCompanyItemBean>>> select(@Body HashMap<String,Object> body);

    @POST("/app/api/store/queryShopKeyListByPin")
    Observable<BaseResponse<List<PersonalNewShopListBean>>> getShopList(@Body HashMap<String,Object> body);

    @POST("/app/api/tenant/covert")
    Observable<BaseResponse<String>> convert(@Body HashMap<String,Object> body);
}
