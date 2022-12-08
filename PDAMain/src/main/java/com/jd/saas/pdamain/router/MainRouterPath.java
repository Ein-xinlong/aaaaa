package com.jd.saas.pdamain.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * Main Module的路由地址汇总
 *
 * @author majiheng
 */
public class MainRouterPath {

    public static final String HOST_MAIN = "MainModule";
    public static final String HOME_ACTIVITY_PATH = "/MainNewPage";
    public static final String HOST_PATH_MAIN = SCHAME + "://" + NATIVE_TYPE + HOST_MAIN + HOME_ACTIVITY_PATH;

    public static final String SPLASH_ACTIVITY_PATH = "/MainSplashPage";
    public static final String HOST_PATH_MAIN_SPLASH = SCHAME + "://" + NATIVE_TYPE + HOST_MAIN + SPLASH_ACTIVITY_PATH;

    public static final String HOST_MAIN_STORE = "MainStoreModule";
    public static final String HOME_STORE_ACTIVITY_PATH = "/MainStorePage";
    public static final String HOST_PATH_MAIN_STORE = SCHAME + "://" + NATIVE_TYPE + HOST_MAIN_STORE + HOME_STORE_ACTIVITY_PATH;
}
