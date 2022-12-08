package com.example.pdalogin.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * login 路由地址
 * */
public class LoginRouterPath {
    public static final String HOST_LOGIN = "LoginModule";

    public static final String LOGIN_ACTIVITY_PATH = "/LoginNewPage";
    public static final String LOGIN_CHOOSE_ENTERPRISE_ACTIVITY_PATH = "/ChooesenterpriseNewPage";
    public static final String HOST_PATH_LOGIN = SCHAME + "://" + NATIVE_TYPE + HOST_LOGIN + LOGIN_ACTIVITY_PATH;
    public static final String HOST_PATH_CHOOSE_ENTERPRISE = SCHAME + "://" + NATIVE_TYPE + HOST_LOGIN + LOGIN_CHOOSE_ENTERPRISE_ACTIVITY_PATH;
}
