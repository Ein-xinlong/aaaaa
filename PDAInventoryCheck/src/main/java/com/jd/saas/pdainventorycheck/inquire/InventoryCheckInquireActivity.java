package com.jd.saas.pdainventorycheck.inquire;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.router.InventoryCheckRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 库存查询页面
 *
 * @author: ext.anxinlong
 * @date: 2021/7/13
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = InventoryCheckRouterPath.HOST_INVENTORY_CHECK, path = InventoryCheckRouterPath.INVENTORY_CHECK_ACTIVITY_PATH)
public class InventoryCheckInquireActivity extends SimpleActivity {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.inventory_check_title);

    }

    @Override
    protected Fragment maybeCreateFragment() {
        return InventoryCheckInquireFragment.getInstance();
    }
}
