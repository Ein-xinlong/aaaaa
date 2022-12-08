package com.jd.saas.pdadelivery.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * Main Module的路由地址汇总
 *
 * @author mengmeng
 */
public class DeliveryRouterConfig {
    /**
     * 路由的模块名
     */
    public static final String MODULE_NAME = "DeliveryModule";

    /**
     * 列表页面名称
     */
    public static final String PAGE_MANAGER_NAME = "/ManagerPage";
    public static final String PATH_MANAGE = SCHAME + "://" + NATIVE_TYPE + MODULE_NAME + PAGE_MANAGER_NAME;

    /**
     * 详情页面名称
     */
    public static final String PAGE_DETAIL_NAME = "/Detail";
    public static final String PATH_DETAIL = SCHAME + "://" + NATIVE_TYPE + MODULE_NAME + PAGE_DETAIL_NAME;

    /**
     * 入库单编号key
     */
    public static final String PARAM_DELIVERY_BEAN = "param_delivery_bean";
    public static final int REQUEST_SCAN_CODE = 100;
    public static final int REQUEST_DETAIL_EDIT_RESULT = 101;
}
