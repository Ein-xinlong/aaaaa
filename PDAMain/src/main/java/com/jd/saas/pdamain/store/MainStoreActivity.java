package com.jd.saas.pdamain.store;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.router.MainRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

@JDRouteUri(scheme = RouterBasePath.SCHAME, host = MainRouterPath.HOST_MAIN_STORE, path = MainRouterPath.HOME_STORE_ACTIVITY_PATH)
public class MainStoreActivity extends SimpleActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setSimpleTitleBar(AppTypeUtil.getAppType() == 1 ? R.string.main_shop_dialog_title : R.string.main_cang_dialog_title);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return MainStoreFragment.newInstance();
    }
}
