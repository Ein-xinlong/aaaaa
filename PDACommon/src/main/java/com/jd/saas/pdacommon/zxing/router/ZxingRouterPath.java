package com.jd.saas.pdacommon.zxing.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/7
 */
public class ZxingRouterPath {
    public static final String HOST_ZXING = "CommonModule";
    public static final String PAGE_ZXING = "/ScanPage";
    public static final String PATH_ZXING = SCHAME + "://" + NATIVE_TYPE + HOST_ZXING + PAGE_ZXING;
}
