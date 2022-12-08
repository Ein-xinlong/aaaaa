package com.jd.saas.pdacommon.webview;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * PDA通用网页容器路由信息
 *
 * @author majiheng
 */
public class PDAWebViewRouterPath {
    public static final String HOST_WEB_VIEW = "CommonModule";
    public static final String PDA_WEB_VIEW_ACTIVITY_PATH = "/WebViewPage";
    public static final String HOST_PATH_PDA_WEB_VIEW = SCHAME + "://" + NATIVE_TYPE + HOST_WEB_VIEW + PDA_WEB_VIEW_ACTIVITY_PATH;

    public static final String WEB_CONFIG = "data";
}
