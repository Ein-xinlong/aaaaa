package com.example.pdalogin.net;

import com.example.pdalogin.bean.LoginChoseEnterpriseBean;
import com.example.pdalogin.bean.LoginJnosTokenResponseUserAccountBean;
import com.jd.saas.pdacommon.net.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 登录模块网络请求
 *
 * @author majiheng
 */
public interface LoginApi {

    @POST("/app/api/tenant/queryAll")
    Observable<BaseResponse<List<LoginChoseEnterpriseBean>>> select(@Body HashMap<String,Object> body);

    @POST("/app/api/tenant/covert")
    Observable<BaseResponse<String>> convert(@Body HashMap<String,Object> body);

    @POST("/app/api/account/getAccountByToken")
    Observable<BaseResponse<LoginJnosTokenResponseUserAccountBean>> getAccountByJNOSToken(@Body HashMap<String,Object> body);
}
