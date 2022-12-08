package com.jd.saas.padinventory.adjustment;

import androidx.fragment.app.Fragment;

import com.jd.saas.padinventory.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 调整单Activity
 *
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = InventoryAdjustmentRouterPath.HOST_Inventory, path = InventoryAdjustmentRouterPath.Inventory_Adjustment_ACTIVITY_PATH)
public class InventoryAdjustmentActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.inventory_adjustment_title)
                .setBackButtonVisible(true);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return InventoryAdjustmentFragment.newInstance();
    }
}