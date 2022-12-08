package com.example.pdalogin.net;

import com.example.pdalogin.bean.LoginChoseEnterpriseBean;
import com.example.pdalogin.bean.LoginJnosTokenResponseUserAccountBean;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;

import java.util.HashMap;
import java.util.List;

/**
 * 登录模块repository
 *
 * @author majiheng
 */
public class LoginRepository extends BaseRepository {

    private final LoginApi mLoginApi = ApiMgr.getApi(LoginApi.class);

    /**
     * 租户选择
     * */
    public void select(HashMap<String,Object> body,BaseObserver<List<LoginChoseEnterpriseBean>> observer){
        request(mLoginApi.select(body),observer);
    }

    /**
     * 租户id转accountId or accountId转租户id
     * */
    public void convert(HashMap<String,Object> body,BaseObserver<String> observer){
        request(mLoginApi.convert(body),observer);
    }

    /**
     * 根据JNOS SDK返回的token，获取用户信息
     */
    public void getAccountByJNOSToken(HashMap<String,Object> body,BaseObserver<LoginJnosTokenResponseUserAccountBean> observer) {
        request(mLoginApi.getAccountByJNOSToken(body),observer);
    }
}
