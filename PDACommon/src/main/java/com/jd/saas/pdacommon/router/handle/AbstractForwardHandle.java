package com.jd.saas.pdacommon.router.handle;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * @author majiheng
 * Description:路由跳转请求处理抽象类
 */
public abstract class AbstractForwardHandle {
    /**
     * 路由下发根据参数进行处理
     * @param context
     * @param host 路由host
     * @param path 路由地址？前内容
     * @param paramBundle 路由传输参数对象
     */
    public abstract void execute(Context context, String host, String path, Bundle paramBundle);

    /**
     * 路由下发根据路由地址处理
     * @param context
     * @param routerUrl 路由完整地址包含参数
     */
    public abstract void execute(Context context, String routerUrl, Bundle paramBundle);

    public abstract void execute(Context context, String routerUrl, Bundle paramBundle, Integer requestCode);

    public abstract Fragment execute(String var1);
}
