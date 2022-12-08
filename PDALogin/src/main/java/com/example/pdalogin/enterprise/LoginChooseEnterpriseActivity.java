package com.example.pdalogin.enterprise;

import androidx.fragment.app.Fragment;

import com.example.pdalogin.R;
import com.example.pdalogin.router.LoginRouterPath;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;
/**
 * 选择企业activity
 * */
@JDRouteUri(scheme = RouterBasePath.SCHAME,host = LoginRouterPath.HOST_LOGIN,path = LoginRouterPath.LOGIN_CHOOSE_ENTERPRISE_ACTIVITY_PATH)
public class LoginChooseEnterpriseActivity extends SimpleActivity {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.login_choose_company_title).setBackButtonVisible(false);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return LoginChooseEnterpriseFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        // 屏蔽Android返回按钮
    }
}
