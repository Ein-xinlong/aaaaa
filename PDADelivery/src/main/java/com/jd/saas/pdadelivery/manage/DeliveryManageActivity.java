package com.jd.saas.pdadelivery.manage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.manage.ui.DeliveryManageFragment;
import com.jd.saas.pdadelivery.router.DeliveryRouterConfig;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 收货
 *
 * @author ext.mengmeng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = DeliveryRouterConfig.MODULE_NAME, path = DeliveryRouterConfig.PAGE_MANAGER_NAME)
public class DeliveryManageActivity extends SimpleActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setSimpleTitleBar(0).setTitle(R.string.delivery_name).setDividerVisible(true);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return DeliveryManageFragment.newInstance();
    }


}