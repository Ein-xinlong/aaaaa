package com.jd.saas.padinventory.adjustment;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 商品详情页路由管理
 *
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
public class InventoryAdjustmentRouterPath {
    public static final String HOST_Inventory = "InventoryModule";
    public static final String Inventory_Adjustment_ACTIVITY_PATH = "/AdjustmentNewPage";
    public static final String HOST_PATH_LOGIN = SCHAME + "://" + NATIVE_TYPE + HOST_Inventory + Inventory_Adjustment_ACTIVITY_PATH;

}
