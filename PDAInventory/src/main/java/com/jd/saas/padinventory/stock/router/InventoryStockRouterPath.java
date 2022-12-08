package com.jd.saas.padinventory.stock.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 「库存调整」路由地址
 *
 * @author majiheng
 */
public class InventoryStockRouterPath {
    public static final String HOST_INVENTORY = "InventoryModule";
    public static final String INVENTORY_STOCK_ACTIVITY_PATH = "/StockNewPage";
    public static final String HOST_PATH_STOCK = SCHAME + "://" + NATIVE_TYPE + HOST_INVENTORY + INVENTORY_STOCK_ACTIVITY_PATH;

    public static final int REQUEST_CODE_SCAN =100;
}
