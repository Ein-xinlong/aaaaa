package com.jd.saas.pdacommon.router.jdrouter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jingdong.amon.router.module.Letter;

/**
 * @author majiheng
 * Description:该实现中没有scheme参数，封装组件中全局进行配置，没有则默认使用当前应用名称
 */
public interface RouterComponent {
    /**
     * 根据路由地址跳转
     *
     * @param context
     * @param routerUrl
     */
    void toRouter(@NonNull Context context, @NonNull String routerUrl);

    /**
     * 根据路由地址跳转 带bundle
     *
     * @param context
     * @param routerUrl
     * @param paramBundle
     */
    void toRouter(@NonNull Context context, @NonNull String routerUrl, Bundle paramBundle);

    /**
     * 根据路由地址跳转 带bundle， 带requestCode
     *  @param context
     * @param routerUrl
     * @param paramBundle
     * @param requestCode
     */
    void toRouter(@NonNull Context context, @NonNull String routerUrl, Bundle paramBundle, Integer requestCode);
    /**
     * 根据具体参数进行路由跳转，带参数
     *
     * @param context
     * @param model
     * @param path
     * @param paramBundle
     */
    void toRouter(@NonNull Context context, @NonNull String model, @NonNull String path, Bundle paramBundle);

    /**
     * 根据具体参数进行路由跳转，无参数
     *
     * @param context
     * @param model
     * @param path
     */
    void toRouter(@NonNull Context context, @NonNull String model, @NonNull String path);

    /**
     * 根据请求路径返回Letter
     *
     * @param context
     * @param routerUrl
     * @return
     */
    Letter getRouterLetter(@NonNull Context context, @NonNull String routerUrl);

    /**
     * 根据请求路径返回Letter 带bundle
     *
     * @param context
     * @param routerUrl
     * @param paramBundle
     * @return
     */
    Letter getRouterLetter(@NonNull Context context, @NonNull String routerUrl, Bundle paramBundle);

    /**
     * 根据具体参数进行路由跳转，带参数 返回Letter
     *
     * @param context
     * @param host
     * @param path
     * @return
     */
    Letter getRouterLetter(@NonNull Context context, @NonNull String host, @NonNull String path);

    /**
     * 根据具体参数进行路由跳转，无参数 返回Letter
     *
     * @param context
     * @param host
     * @param path
     * @param paramBundle
     * @return
     */
    Letter getRouterLetter(@NonNull Context context, @NonNull String host, @NonNull String path, Bundle paramBundle);

    Fragment getRouterFragment(@NonNull String var1);
}
