package com.jd.saas.pdainventorycheck.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.router.InventoryCheckRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

@JDRouteUri(
        scheme = RouterBasePath.SCHAME,
        host = InventoryCheckRouterPath.HOST_INVENTORY_CHECK,
        path = InventoryCheckRouterPath.INVENTORY_CHECK_DETAILS_ACTIVITY
)
public class InventoryCheckDetailActivity extends SimpleActivity {


    @Override
    protected void init() {

        setSimpleTitleBar(R.string.inventory_check_details).setBackButtonVisible(true);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return InventoryCheckDetailFragment.newInstance();
    }
}