package com.jd.saas.pdamain.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdamain.router.MainRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * pda首页
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = MainRouterPath.HOST_MAIN, path = MainRouterPath.HOME_ACTIVITY_PATH)
public class MainHomeActivity extends SimpleActivity {


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setSimpleTitleBar(0).setToolBarVisible(false);

    }

    @Override
    protected Fragment maybeCreateFragment() {
        return MainHomeFragment.newInstance();
    }
}