package com.jd.saas.pdapersonal.store;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.router.PersonalRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

@JDRouteUri(scheme = RouterBasePath.SCHAME, host = PersonalRouterPath.HOST_PERSONAL, path = PersonalRouterPath.PRIVACY_ACTIVITY_STORE)
public class PersonalStoreActivity  extends SimpleActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setSimpleTitleBar(AppTypeUtil.getAppType() == 1 ?R.string.personal_store_title:R.string.personal_store_title_cang);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return PersonalStoreFragment.newInstance();
    }
}
