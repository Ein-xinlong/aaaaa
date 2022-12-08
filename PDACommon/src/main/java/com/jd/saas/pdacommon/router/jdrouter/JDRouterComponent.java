package com.jd.saas.pdacommon.router.jdrouter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.router.ConstantUtil;
import com.jd.saas.pdacommon.router.beans.RouterProtocolDO;
import com.jd.saas.pdacommon.router.exception.InvokeException;
import com.jd.saas.pdacommon.router.formate.FormatCheck;
import com.jd.saas.pdacommon.router.formate.ProtocolFormat;
import com.jingdong.amon.router.JDRouter;
import com.jingdong.amon.router.module.Letter;

/**
 * @author majiheng
 * Description:根据公司封装的jdrouter实现相关功能
 */
public class JDRouterComponent implements RouterComponent {
    /**
     * 根据路由地址跳转
     *
     * @param context
     * @param routerUrl
     */
    @Override
    public void toRouter(@NonNull Context context, @NonNull String routerUrl) {
        boolean routerUrlError = FormatCheck.isRouterUrlError(routerUrl);
        if (routerUrlError) {
            throw new InvokeException(102, "跳转地址格式异常!");
        }
        String newRouterUrl = ProtocolFormat.toRouterUrlNotType(routerUrl);
        getRouterLetter(context, newRouterUrl).navigation();
    }

    /**
     * 根据路由地址跳转 带bundle
     *
     * @param context
     * @param routerUrl
     * @param paramBundle
     */
    @Override
    public void toRouter(@NonNull Context context, @NonNull String routerUrl, Bundle paramBundle) {
        if (null != paramBundle) {
            boolean routerUrlError = FormatCheck.isRouterUrlError(routerUrl);
            if (routerUrlError) {
                throw new InvokeException(102, "跳转地址格式异常!");
            }
            String newRouterUrl = ProtocolFormat.toRouterUrlNotType(routerUrl);
            getRouterLetter(context, newRouterUrl).putExtras(paramBundle).navigation();
        } else {
            toRouter(context,routerUrl);
        }
    }

    /**
     * 带参数，带requestCode
     * @param context
     * @param routerUrl
     * @param paramBundle
     * @param requestCode
     */
    @Override
    public void toRouter(@NonNull Context context, @NonNull String routerUrl, Bundle paramBundle, Integer requestCode) {
        if (null != paramBundle) {
            boolean routerUrlError = FormatCheck.isRouterUrlError(routerUrl);
            if (routerUrlError) {
                throw new InvokeException(102, "跳转地址格式异常!");
            }
            String newRouterUrl = ProtocolFormat.toRouterUrlNotType(routerUrl);
            Letter letter = getRouterLetter(context, newRouterUrl).putExtras(paramBundle);
            if(requestCode != null) {
                letter.withRequestCode(requestCode);
            }
            letter.navigation();
        } else {
            toRouter(context,routerUrl);
        }
    }

    /**
     * 根据具体参数进行路由跳转，带参数
     *
     * @param context
     * @param model
     * @param path
     * @param paramBundle
     */
    @Override
    public void toRouter(@NonNull Context context, @NonNull String model, @NonNull String path, Bundle paramBundle) {
        Letter letter = getRouterLetter(context, model, path, paramBundle);
        letter.navigation();
    }

    /**
     * 根据具体参数进行路由跳转，无参数
     *
     * @param context
     * @param model
     * @param path
     */
    @Override
    public void toRouter(@NonNull Context context, @NonNull String model, @NonNull String path) {
        toRouter(context, model, path, null);
    }

    /**
     * 根据请求路径返回Letter
     *
     * @param context
     * @param routerUrl
     * @return
     */
    @Override
    public Letter getRouterLetter(@NonNull Context context, @NonNull String routerUrl) {
        routerUrl = routerUrl.trim();
        if ("".equals(routerUrl)) {
            throw new InvokeException(101, "跳转地址不能为空!");
        }
        //处理path为斜杠情况
        RouterProtocolDO protocolDO = ProtocolFormat.routerUrlToObj(routerUrl);
        String path = protocolDO.getPath();
        String newPath = ProtocolFormat.pathFormat(path);
        String routerPrefix = ProtocolFormat.toRouterUrl(protocolDO.getScheme(), protocolDO.getHost(), path);
        String newRouterPrefix = ProtocolFormat.toRouterUrl(protocolDO.getScheme(), protocolDO.getHost(), newPath);
        //替换问号前完整路径防止替换错误
        String routerNewUrl = routerUrl.replaceFirst(routerPrefix, newRouterPrefix);
        boolean routerUrlError = FormatCheck.isRouterUrlError(routerNewUrl, false);
        Letter letter;
        if (routerUrlError) {
            throw new InvokeException(102, "跳转地址格式异常!");
        } else {
            letter = JDRouter.build(context, routerNewUrl);
        }
        return letter;
    }

    /**
     * 根据请求路径返回Letter 带bundle
     *
     * @param context
     * @param routerUrl
     * @param paramBundle
     * @return
     */
    @Override
    public Letter getRouterLetter(@NonNull Context context, @NonNull String routerUrl, Bundle paramBundle) {
        if (null != paramBundle) {
            return getRouterLetter(context, routerUrl).putExtras(paramBundle);
        }
        return getRouterLetter(context, routerUrl);
    }

    /**
     * 根据具体参数进行路由跳转，带参数 返回Letter
     *
     * @param context
     * @param model
     * @param path
     * @return
     */
    @Override
    public Letter getRouterLetter(@NonNull Context context, @NonNull String model, @NonNull String path) {
        return getRouterLetter(context, model, path, null);
    }

    /**
     * 根据具体参数进行路由跳转，无参数 返回Letter
     *
     * @param context
     * @param model
     * @param path
     * @param paramBundle
     * @return
     */
    @Override
    public Letter getRouterLetter(@NonNull Context context, @NonNull String model, @NonNull String path, Bundle paramBundle) {
        String newPath = ProtocolFormat.pathFormat(path);
        String routerUrl = ProtocolFormat.toRouterUrl(ConstantUtil.ROUTER_SCHEME, model, newPath);
        Letter letter = getRouterLetter(context, routerUrl);
        //排除bundle为空的情况
        if (paramBundle != null) {
            letter.putExtras(paramBundle);
        }
        return letter;
    }

    public Fragment getRouterFragment(@NonNull String routerUrl) {
        routerUrl = routerUrl.trim();
        if ("".equals(routerUrl)) {
            throw new InvokeException(101, "跳转地址不能为空!");
        } else {
            RouterProtocolDO protocolDO = ProtocolFormat.routerUrlToObj(routerUrl);
            String path = protocolDO.getPath();
            String newPath = ProtocolFormat.pathFormat(path);
            String routerPrefix = ProtocolFormat.toRouterUrl(protocolDO.getScheme(), protocolDO.getHost(), path);
            String newRouterPrefix = ProtocolFormat.toRouterUrl(protocolDO.getScheme(), protocolDO.getHost(), newPath);
            String routerNewUrl = routerUrl.replaceFirst(routerPrefix, newRouterPrefix);
            boolean routerUrlError = FormatCheck.isRouterUrlError(routerNewUrl, false);
            if (routerUrlError) {
                throw new InvokeException(102, "跳转地址格式异常!");
            } else {
                String newRouterUrl = ProtocolFormat.toRouterUrlNotType(routerNewUrl);
                Fragment fragment = (Fragment) JDRouter.getService(Fragment.class, newRouterUrl);
                return fragment;
            }
        }
    }
}
