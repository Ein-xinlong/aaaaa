package com.jd.saas.padinventory.adjustment.detail;

import androidx.fragment.app.Fragment;

import com.jd.saas.padinventory.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 库存调整明细
 *
 * @author: ext.anxinlong
 * @date: 2021/6/1
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = InventoryAdjustmentDetailRouterPath.HOST_ADJUSTMENT_DETAIL, path = InventoryAdjustmentDetailRouterPath.Inventory_ADJUSTMENT_DETAIL_PATH)
public class InventoryAdjustmentDetailActivity extends SimpleActivity {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.inventory_adjustment_title_detail)
                .setBackButtonVisible(true);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return InventoryAdjustmentDetailFragment.getInstance();
    }
}
