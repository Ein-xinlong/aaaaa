package com.jd.saas.padinventory.stock;

import androidx.fragment.app.Fragment;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.stock.router.InventoryStockRouterPath;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 库存调整ui-activity
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = InventoryStockRouterPath.HOST_INVENTORY, path = InventoryStockRouterPath.INVENTORY_STOCK_ACTIVITY_PATH)
public class InventoryStockActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.inventory_stock);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return InventoryStockFragment.newInstance();
    }
}
