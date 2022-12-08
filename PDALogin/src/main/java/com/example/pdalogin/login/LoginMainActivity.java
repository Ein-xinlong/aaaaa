package com.example.pdalogin.login;

import androidx.fragment.app.Fragment;

import com.example.pdalogin.router.LoginRouterPath;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 登录activity
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME,host = LoginRouterPath.HOST_LOGIN,path = LoginRouterPath.LOGIN_ACTIVITY_PATH)
public class LoginMainActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(0)
                .setBackButtonVisible(false)
                .setToolBarVisible(false);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return LoginMainFragment.newInstance();
    }
}