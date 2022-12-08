package com.jd.saas.padinventory.adjustment.detail;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 商品详情页路由管理
 *
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
public class InventoryAdjustmentDetailRouterPath {
    public static final String HOST_ADJUSTMENT_DETAIL = "InventoryModule";
    public static final String Inventory_ADJUSTMENT_DETAIL_PATH = "/AdjustmentDetailNewPage";
    public static final String HOST_PATH_LOGIN = SCHAME + "://" + NATIVE_TYPE + HOST_ADJUSTMENT_DETAIL + Inventory_ADJUSTMENT_DETAIL_PATH;


}
