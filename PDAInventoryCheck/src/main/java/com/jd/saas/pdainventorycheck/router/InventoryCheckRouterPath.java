package com.jd.saas.pdainventorycheck.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 库存查询地址管理
 * @author: ext.anxinlong
 * @date: 2021/7/13
 */
public class InventoryCheckRouterPath {
    public static final String HOST_INVENTORY_CHECK = "InventoryCheckModule";
    public static final String INVENTORY_CHECK_ACTIVITY_PATH = "/CheckNewPage";
    public static final String HOST_PATH_STOCK = SCHAME + "://" + NATIVE_TYPE + HOST_INVENTORY_CHECK + INVENTORY_CHECK_ACTIVITY_PATH;
    public static final int REQUEST_CODE_SCAN = 100;

    public static final String INVENTORY_CHECK_DETAILS_ACTIVITY = "/CheckDetailsPage";
    public static final String HOST_PATH_DETAILS = SCHAME + "://" + NATIVE_TYPE + HOST_INVENTORY_CHECK + INVENTORY_CHECK_DETAILS_ACTIVITY;


}
