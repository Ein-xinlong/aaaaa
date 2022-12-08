package com.jd.saas.pdacommon.upgrade;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.jd.saas.pdacommon.BuildConfig;
import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.enums.AppType;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.utils.DeviceUtil;
import com.jd.saas.pdacommon.utils.PackageUtil;
import com.jd.sentry.Sentry;
import com.jd.sentry.SentryConfig;
import com.jingdong.jdma.JDMA;
import com.jingdong.jdma.JDMAConfig;
import com.jingdong.jdma.minterface.JDMABaseInfo;
import com.jingdong.lib.operation.JdOMConfig;
import com.jingdong.lib.operation.JdOMSdk;
import com.jingdong.sdk.baseinfo.BaseInfo;
import com.jingdong.sdk.jdcrashreport.JDCrashReportConfig;
import com.jingdong.sdk.jdcrashreport.JdCrashReport;
import com.jingdong.sdk.jdupgrade.BaseInfoProvider;
import com.jingdong.sdk.jdupgrade.JDUpgrade;
import com.jingdong.sdk.jdupgrade.UpgradeConfig;

import java.util.List;

public class JdAvatar {
    public static String getAppKey() {
        return AppTypeUtil.getAppType() == AppType.STORE.getValue() ? BuildConfig.AVATER_APP_KEY : BuildConfig.AVATER_APP_CANG_KEY;
    }

    private static String getAppSecret() {
        return AppTypeUtil.getAppType() == AppType.STORE.getValue() ? BuildConfig.AVATER_APP_SECRET : BuildConfig.AVATER_APP_CANG_SECRET;
    }

    /**
     * 初始化版本升级SDK 需要基础信息SDK
     * http://docs.avatar.jd.com/gray/android-gray-publish/%E6%8E%A5%E5%85%A5%E6%96%87%E6%A1%A3-3.0.html
     */
    public static void initUpgrade(Application application) {
        final String channel = AppTypeUtil.getChannel();
        UpgradeConfig.Builder builder = new UpgradeConfig.Builder(getAppKey(), getAppSecret(), R.mipmap.ic_launcher)//    其中appIconDrawableId 是指 您应用的图标资源id，例如 R.drawable.logo
                .setLogEnable(false)//日志打印 默认关，非必须，查问题时可以设置为true，日志按upgrade过滤
                .setShowToast(true);//是否显示sdk内部toast，默认true, 非必须
        BaseInfoProvider baseInfoProvider = new BaseInfoProvider() {
            @Override
            public String getAppPackageName() {
                return BaseInfo.getAppPackageName();
            }

            @Override
            public String getAppUUID() {
                return DeviceUtil.readDeviceUUID(application);
            }

            @Override
            public String getAppVersionName() {
                return BaseInfo.getAppVersionName();
            }

            @Override
            public String getAppVersionCode() {
                return "" + BaseInfo.getAppVersionCode();
            }

            @Override
            public String getAppPartnerName() {
                return channel;
            }

            @Override
            public String getOsVersionName() {
                return Build.VERSION.RELEASE;
            }

            @Override
            public String getOsVersionCode() {
                return "" + Build.VERSION.SDK_INT;
            }

            @Override
            public String getDeviceBrandName() {
                return Build.BRAND;
            }

            @Override
            public String getDeviceModelName() {
                return Build.MODEL;
            }

            @Override
            public String getDeviceSupportedABIs() {
                String[] deviceABIs = BaseInfo.getDeviceSuppportedABIs();
                if (deviceABIs != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < deviceABIs.length; i++) {
                        stringBuilder.append(deviceABIs[i]);
                        if (i != deviceABIs.length - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    return stringBuilder.toString();
                }
                return "arm64-v8a";
            }

            @Override
            public String getNetWorkType() {
                return BaseInfo.getNetworkType();
            }

            @Override
            public List<ActivityManager.RunningServiceInfo> getRunningServices() {
                return BaseInfo.getRunningServices(Integer.MAX_VALUE);
            }

            @Override
            public String getAppUserID() {
                return UserManager.getInstance().getUserData().getTenantId();
            }
        };
        UpgradeConfig config = builder.build();
        JDUpgrade.init(application, config, baseInfoProvider);
//        autoCheck();// 屏蔽app启动时检查更新
    }

    /**
     * 初始化基础信息SDK 用于隐私合规 PDA应用不上架 设备默认全部开启
     * http://doc.jd.com/avatar/mobile-components/jdsdk/sdks/BaseInfo/Android.html
     */
    public static void initBaseInfoSDK(Application application) {
        try {
            // 初始化方法，必须调用
            BaseInfo.init(application);

            // 1.5.7 开始需要设置用户同意隐私协议回调。JDPrivacyHelper为主站实现类，可替换为自己应用的实现
            BaseInfo.setPrivacyCheckUtil(() -> {
                //PDA设备默认同意
                return true;
            });

            // 1.6.44 开始需要设置应用是否在前台的检查回调，不设置时敏感字段方法将不会返回数据
            BaseInfo.setBackForegroundCheckUtil(() -> {
                //应用自己实现前后台检查逻辑
                return true;
            });
        } catch (Throwable t) {
        }
    }

    /**
     * 初始化性能监控SDK 需要基础信息SDK
     * http://docs.avatar.jd.com/shooter/sdk-android/android-performance-sdk-2.html
     */
    public static void initShooter(Application application) {
        Sentry.initialize(SentryConfig.newBuilder(application)
                .setAppId(getAppKey())
                .setAccountIdConfig(() -> UserManager.getInstance().getUserData().getUserPin())
                .setUuid(DeviceUtil.readDeviceUUID(application))
                .setIsReportByRealTime(BuildConfig.DEBUG)
                .build());
    }

    /**
     * 初始化崩溃上报SDK
     * http://docs.avatar.jd.com/crash/sdk/android/guide.html
     */
    public static void initJDCrashReport(Application application) {
        final String channel = AppTypeUtil.getChannel();
        JDCrashReportConfig config = new JDCrashReportConfig
                .Builder()
                .setContext(application.getApplicationContext())
                .setEnableNative(false)
                .setDeviceUniqueId(DeviceUtil.readDeviceUUID(application))  //设备唯一标识，建议使用应用的UUID方案
                .setPartner(channel)  //渠道
                .setUserId(UserManager.getInstance().getUserData().getUserPin())//用户ID
                .setAppId(getAppKey())//阿凡达控制台应用 APP Key
                .build();
        JdCrashReport.init(config);
    }

    /**
     * 初始化移动统计 运营数据
     */
    public static void initOperationSdk(Context context) {
        JdOMSdk.init(new JdOMConfig.Builder(context)
                .setAppKey(getAppKey())
                .setUserId(UserManager.getInstance().getUserData().getUserPin())
                .setUUID(DeviceUtil.readDeviceUUID(context))
                .setDebug(BuildConfig.DEBUG)  // 是否开启sdk内部日志输出
                .setPartner(AppTypeUtil.getChannel())
                .build());
    }

    public static void updateUserId(String userId) {
        JdCrashReport.updateUserId(userId);
        JdOMSdk.getConfig().updateUserId(userId);
    }

    /**
     * 自动限制更新频率
     */
    public static void autoCheck() {
        JDUpgrade.limitedCheckAndPop(null);
    }

    /**
     * 每次都检查更新
     */
    public static void forceCheck() {
        JDUpgrade.unlimitedCheckAndPop(null);
    }

    /**
     * 初始化子午线埋点统计
     */
    public static void initEasyAnalytics(Context application) {
        JDMABaseInfo jdmaBaseInfo = new JDMABaseInfo() {
            @Override
            public String getAndroidId() {
                return BaseInfo.getAndroidId();
            }

            @Override
            public String getDeviceBrand() {
                return BaseInfo.getDeviceBrand();
            }

            @Override
            public String getDeviceModel() {
                return BaseInfo.getDeviceModel();
            }

            @Override
            public String getOsVersionName() {
                return BaseInfo.getAndroidVersion();
            }

            @Override
            public int getOsVersionInt() {
                return BaseInfo.getAndroidSDKVersion();
            }

            @Override
            public String getScreenSize() {
                return BaseInfo.getScreenHeight() + "*" + BaseInfo.getScreenWidth();
            }

            @Override
            public String getSimOperator() {
                return BaseInfo.getSimOperator();
            }
        };
        JDMAConfig config = new JDMAConfig.JDMAConfigBuilder()
                .JDMABaseInfo(jdmaBaseInfo)
                .uid(DeviceUtil.readDeviceUUID(application))   //唯一设备id
                .siteId(BuildConfig.AVATER_MARK_SITE_ID) // 站点编号
                .appDevice(JDMAConfig.ANDROID)  //android设备类型
                .channel(AppTypeUtil.getChannel())  //渠道
                .appVersionName(PackageUtil.getVersionName(application))  // app版本号
                .appBuildId(String.valueOf(PackageUtil.getVersionCode(application)))// app build id
                .build();
        JDMA.startWithConfig(application, config);
        JDMA.acceptPrivacyProtocol(false);
        JDMA.setShowLog(BuildConfig.DEBUG);
    }


}
