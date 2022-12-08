package com.jd.saas.padinventory.create;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 新建调整单Router
 *
 * @author ext.mengmeng
 */
public class InventoryCreateRouterPath {
    public static final String CREATE_MAIN = "CreateModule";
    public static final String CREATE_ACTIVITY_PATH = "/InventoryCreatePage";
    public static final String CREATE_PATH_MAIN = SCHAME + "://" + NATIVE_TYPE + CREATE_MAIN + CREATE_ACTIVITY_PATH;

    public static final int REQUEST_CODE_SCAN = 100;
}
