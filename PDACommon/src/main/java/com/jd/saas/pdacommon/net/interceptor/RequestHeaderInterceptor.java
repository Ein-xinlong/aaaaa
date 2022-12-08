package com.jd.saas.pdacommon.net.interceptor;

import com.jd.saas.pdacommon.user.UserManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestHeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (UserManager.isLogin()) {
            Request original = chain.request();
            Request.Builder rb = original.newBuilder();
            // 如果是JNos登录，token名称叫"jnostoken"，否则是"token"，待账户迁移完毕后统一改成"jnostoken"
            rb.addHeader("jnostoken", UserManager.getToken());
            return chain.proceed(rb.build());
        }
        return chain.proceed(chain.request());
    }
}
