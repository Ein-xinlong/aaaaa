package com.jd.saas.pdacommon.application;

import android.app.Application;
import android.content.res.Configuration;

import com.jd.saas.pdacommon.BuildConfig;
import com.jd.saas.pdacommon.GlideApp;
import com.jd.saas.pdacommon.flutter.JDFHelper;
import com.jd.saas.pdacommon.router.JDRouterManager;
import com.jd.saas.pdacommon.upgrade.JdAvatar;

import org.jetbrains.annotations.NotNull;

import jd.jnos.loginsdk.JNosLoginHelper;

/**
 * 全局Application
 *
 * @author majiheng
 */
public class MyApplication extends Application {
    static void loadLib() {
        System.loadLibrary("JDMobileSec");
    }

    static {

    }

    protected static MyApplication gApplication;

    /**
     * 获取Application
     */
    public static MyApplication getInstance() {
        return gApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gApplication = this;
        // 初始化京东路由JDRouter
        JDRouterManager.init(gApplication);
        JdAvatar.initBaseInfoSDK(gApplication);
        JdAvatar.initUpgrade(gApplication);
        JdAvatar.initJDCrashReport(gApplication);
        JdAvatar.initOperationSdk(gApplication);
        JdAvatar.initShooter(gApplication);
        JdAvatar.initEasyAnalytics(gApplication);
        initFlutter();
        // 初始化JNOS登录SDK
        JNosLoginHelper.INSTANCE.createInstance(gApplication, "mendian.xiaoli", "com.jd.retail.cloud.stores");
        if (BuildConfig.DEBUG) {
            JNosLoginHelper.INSTANCE.setDomain("https://api-unin.jdx.com/");
        }
    }


    private void initFlutter() {
        new JDFHelper(this).init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideApp.get(this).onLowMemory();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        GlideApp.get(this).onConfigurationChanged(newConfig);
    }

}
