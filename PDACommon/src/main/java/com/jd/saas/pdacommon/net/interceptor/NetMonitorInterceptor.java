package com.jd.saas.pdacommon.net.interceptor;

import com.jd.saas.pdacommon.net.NetworkMgr;
import com.jd.saas.pdacommon.net.exception.NoNetworkException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 网络拦截器，判断当前网络是否连接
 *
 * @author majiheng
 */
public class NetMonitorInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if(NetworkMgr.isNetworkAvailable()) {
            return chain.proceed(chain.request());
        }else {
            throw new NoNetworkException();
        }
    }
}