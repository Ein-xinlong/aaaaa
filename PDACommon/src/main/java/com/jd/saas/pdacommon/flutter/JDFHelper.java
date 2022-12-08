package com.jd.saas.pdacommon.flutter;

import android.app.Application;
import android.content.Context;

import com.idlefish.flutterboost.FlutterBoost;
import com.jdshare.jdf_container_plugin.components.router.api.JDFRouterHelper;
import com.jdshare.jdf_container_plugin.components.router.internal.IJDFRouterSettings;
import com.jdshare.jdf_container_plugin.container.JDFComponentConfig;
import com.jdshare.jdf_container_plugin.container.JDFContainer;
import com.jdshare.jdf_router_plugin.JDFRouterChannelHandler;
import com.jdshare.jdf_router_plugin.container.JDFRouterModule;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;

/**
 * desc: 初始化容器及路由相关
 */
public class JDFHelper {
    private static final String TAG = "JDFContainer==>" + JDFHelper.class.getSimpleName();
    public static volatile JDFHelper instance;
    private Context mContext;
    public static boolean isInitContainer = false;

    public JDFHelper(Context context) {
        mContext = context;
    }


    public static void initRouterFlutterEngine(Application application) {
        FlutterEngine engine = FlutterBoost.instance().getEngine();
        if (engine == null) {
            FlutterEngineCache.getInstance().put(FlutterBoost.ENGINE_ID, new FlutterEngine(application));
        }
    }

    public void init(Application application) {
        if (isInitContainer) {
            System.out.println("jdflutter--- 不需要重复初始化");
            return;
        }
        initRouterFlutterEngine(application);

        JDFContainer.initContainerContextAndOnRegister(application, () -> {
            // 注意顺序
            JDFContainer.registerComponent(JDFComponentConfig.JDRouter, JDFRouterModule.getInstance(), application);
            JDFRouterChannelHandler.sendNativeRoutes2Flutter();

            // 设置拦截器(全局拦截和某个页面拦截)、设置默认容器页（不推设置，用默认的）
            IJDFRouterSettings routerSettings = JDFRouterHelper.initRouterSettings(application);
            routerSettings.setCustomFlutterActivity(MyCustomFlutterBoostActivity.class)
                    .setCustomFlutterFragment(MyCustomFlutterBoostFragment.class);
            isInitContainer = true;
        });
    }
}
