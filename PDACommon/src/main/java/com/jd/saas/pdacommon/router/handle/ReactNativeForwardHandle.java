package com.jd.saas.pdacommon.router.handle;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * @author majiheng
 * Description:根据RN调用方式实现相关功能
 */
public class ReactNativeForwardHandle extends AbstractForwardHandle {

    /**
     * 路由下发根据参数进行处理
     *
     * @param context
     * @param host        路由host
     * @param path        路由地址？前内容
     * @param paramBundle 路由传输参数对象
     */
    @Override
    public void execute(Context context, String host, String path, Bundle paramBundle) {

    }

    /**
     * 路由下发根据路由地址处理
     *
     * @param context
     * @param routerUrl 路由完整地址包含参数
     */
    @Override
    public void execute(Context context, String routerUrl, Bundle paramBundle) {

    }

    @Override
    public void execute(Context context, String routerUrl, Bundle paramBundle, Integer requestCode) {

    }

    public Fragment execute(String var1) {
        return null;
    }
}
