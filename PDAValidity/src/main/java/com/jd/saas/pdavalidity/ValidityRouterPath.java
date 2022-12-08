package com.jd.saas.pdavalidity;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 效期调整路由地址
 *
 * @author majiheng
 */
public class ValidityRouterPath {
    public static final String HOST_VALIDITY = "ValidityModule";
    public static final String VALIDITY_ADJUSTMENT_LIST_ACTIVITY_PATH = "/ValidityAdjustmentListNewPage";
    public static final String HOST_PATH_LOGIN = SCHAME + "://" + NATIVE_TYPE + HOST_VALIDITY + VALIDITY_ADJUSTMENT_LIST_ACTIVITY_PATH;

    public static final String VALIDITY_ADJUST_DETAIL_ACTIVITY_PATH = "/ValidityAdjustDetailPage";
    public static final String HOST_PATH_VALIDITY_ADJUST_DETAIL = SCHAME + "://" + NATIVE_TYPE + HOST_VALIDITY + VALIDITY_ADJUST_DETAIL_ACTIVITY_PATH;
}
