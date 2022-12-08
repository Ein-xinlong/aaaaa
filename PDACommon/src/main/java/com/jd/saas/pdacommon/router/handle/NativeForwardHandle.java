package com.jd.saas.pdacommon.router.handle;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.router.jdrouter.JDRouterComponent;
import com.jd.saas.pdacommon.router.jdrouter.RouterComponent;

/**
 * @author majiheng
 * Description:调用JDRouter提供的路由调用方式实现
 */
public class NativeForwardHandle extends AbstractForwardHandle {

    /**
     * 路由下发根据参数进行处理
     *
     * @param context
     * @param model       路由model 不包含type
     * @param path        路由地址？前内容
     * @param paramBundle 路由传输参数对象
     */
    @Override
    public void execute(Context context, String model, String path, Bundle paramBundle) {
        RouterComponent routerComponent = new JDRouterComponent();
        routerComponent.toRouter(context, model, path, paramBundle);
    }

    /**
     * 路由下发根据路由地址处理
     *
     * @param context
     * @param routerUrl   路由完整地址包含参数和type，需要去除type
     * @param paramBundle 路由传输参数对象
     */
    @Override
    public void execute(Context context, String routerUrl, Bundle paramBundle) {
        RouterComponent routerComponent = new JDRouterComponent();
        routerComponent.toRouter(context, routerUrl, paramBundle);
    }

    /**
     * 路由下发根据参数进行处理
     * @param context
     * @param routerUrl url
     * @param paramBundle 参数
     * @param requestCode requestCode
     */
    @Override
    public void execute(Context context, String routerUrl, Bundle paramBundle, Integer requestCode) {
        RouterComponent routerComponent = new JDRouterComponent();
        routerComponent.toRouter(context, routerUrl, paramBundle, requestCode);
    }
    public Fragment execute(String routerUrl) {
        RouterComponent routerComponent = new JDRouterComponent();
        return routerComponent.getRouterFragment(routerUrl);
    }
}
