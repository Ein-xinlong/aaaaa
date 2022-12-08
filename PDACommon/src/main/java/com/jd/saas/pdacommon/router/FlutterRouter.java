package com.jd.saas.pdacommon.router;

import android.content.Context;

import com.jd.saas.pdacommon.BuildConfig;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.sp.SPUtils;
import com.jd.saas.pdacommon.upgrade.JdAvatar;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.utils.DeviceUtil;
import com.jdshare.jdf_container_plugin.components.router.api.JDFRouterHelper;
import com.jingdong.sdk.baseinfo.BaseInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cdliuerqiang on 2021/8/25.
 * <p>
 * 跳转flutter的所有路由
 */
public class FlutterRouter {

    // 线上or预发
    public static final String PARAM_ENV = "env";
    public static final String PARAM_BASE_URL = "baseUrl";

    private static final String ENV_PROD = "prod";//prod
    private static final String ENV_PRE = "pre";//uat
    private static final String ENV_DEV = "dev";//dev

    // 请求通用参数
    public static final String PARAM_PIN = "pin";
    public static final String PARAM_TENANT_ID = "tenantId";
    public static final String PARAM_STORE_ID = "storeId";
    public static final String PARAM_STORE_NAME = "storeName";//门店/仓名称
    public static final String PARAM_BIZ_CODE = "bizCode";// 门店/仓
    public static final String PARAM_NEGATIVE_STOCK_CONFIG = "stock";// 负库存配置
    public static final String PARAM_SHOW_PURCHASE_PRICE = "showPurchasePrice";// 是否显示进货价配置

    // header通用参数
    public static final String HEADER_TOKEN = "token";

    // flutter代理
    public static final String PARAM_FLUTTER_PROXY = "proxy";

    private static final String PARAM_APP_KEY_FOR_ANDROID = "appKeyForAndroid";//dev
    private static final String PARAM_DEVICE_ID = "deviceId";//dev
    private static final String PARAM_VERSION_NAME = "versionName";//dev
    private static final String PARAM_VERSION_CODE = "versionCode";//dev
    private static final String PARAM_CHANNEL = "channel";//dev

    private static void addCommonParam(Map<String, String> param) {
        param.put(PARAM_ENV, BuildConfig.DEBUG ? ENV_DEV : ENV_PROD);
        param.put(PARAM_BASE_URL,BuildConfig.API_HOST);
        param.put(PARAM_PIN, UserManager.getInstance().getUserData().getUserPin());
        param.put(PARAM_TENANT_ID, UserManager.getInstance().getUserData().getTenantId());
        param.put(PARAM_STORE_ID, UserManager.getInstance().getUserData().getShopId());
        param.put(PARAM_STORE_NAME, UserManager.getInstance().getUserData().getShopName());
        param.put(PARAM_BIZ_CODE, AppTypeUtil.getAppType() + "");
        param.put(PARAM_NEGATIVE_STOCK_CONFIG,UserManager.getInstance().getUserData().getUserTenantConfigBean().getNegativeStockSale());
        param.put(PARAM_SHOW_PURCHASE_PRICE,UserManager.getInstance().getUserData().getUserTenantConfigBean().isShowPurchasePrice() + "");
        param.put(HEADER_TOKEN, UserManager.getInstance().getUserData().getToken());
        param.put(PARAM_FLUTTER_PROXY, SPUtils.getString("FlutterProxy", ""));

        param.put(PARAM_APP_KEY_FOR_ANDROID, JdAvatar.getAppKey());
        param.put(PARAM_DEVICE_ID, DeviceUtil.readDeviceUUID(MyApplication.getInstance()));
        param.put(PARAM_VERSION_NAME, BaseInfo.getAppVersionName());
        param.put(PARAM_VERSION_CODE, BaseInfo.getAppVersionCode() + "");
        param.put(PARAM_CHANNEL, AppTypeUtil.getChannel());
    }


    public static void open(Context context, String router, Map<String, String> param) {
        if (param == null) {
            param = new HashMap<>();
        }
        addCommonParam(param);
        JDFRouterHelper.openFlutterPage(context, router, param,0);
    }

    public static void openPdaPickingPlugin(Context context, Map<String, String> param) {
        open(context, "flutter://pda_picking/main", param);
    }
}
