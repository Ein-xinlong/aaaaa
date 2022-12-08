package com.jd.saas.pdapersonal.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * Main Module的路由地址汇总
 *
 * @author majiheng
 */
public class PersonalRouterPath {

    public static final String HOST_PERSONAL = "PersonalModule";
    public static final String HOME_ACTIVITY_PATH = "/PersonalHomePage";
    public static final String HOST_PATH_PERSONAL = SCHAME + "://" + NATIVE_TYPE + HOST_PERSONAL + HOME_ACTIVITY_PATH;

    public static final String COMPANY_EXCHANGE_ACTIVITY_PATH = "/CompanyExchangePage";
    public static final String HOST_PATH_COMPANY_EXCHANGE = SCHAME + "://" + NATIVE_TYPE + HOST_PERSONAL + COMPANY_EXCHANGE_ACTIVITY_PATH;

    public static final String FLUTTER_PROXY_ACTIVITY_PATH = "/FlutterProxyPage";
    public static final String HOST_PATH_FLUTTER_PROXY = SCHAME + "://" + NATIVE_TYPE + HOST_PERSONAL + FLUTTER_PROXY_ACTIVITY_PATH;

    public static final String PRIVACY_ACTIVITY_STORE = "/newPageStore";
    public static final String HOST_PATH_STORE = SCHAME + "://" + NATIVE_TYPE + HOST_PERSONAL + PRIVACY_ACTIVITY_STORE;
}
