package com.jd.saas.pdacommon.net.interceptor;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.json.JsonUtil;
import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.user.UserManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_OK;

public class TokenExpireInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == HTTP_MOVED_TEMP) {
            // 清空本地用户信息
            UserManager.getInstance().logout();
            // 关闭所有页面
            AppManager.getInstance().finishAllActivity();
            // 跳转到登录页
            RouterClient.getInstance().forward(MyApplication.getInstance(), "pda://native.LoginModule/LoginNewPage");
            Logger.d("TokenExpire", "登录过期");
            BaseResponse<String> mockResult = new BaseResponse<>();
            mockResult.setCode(-100);
            mockResult.setMsg(MyApplication.getInstance().getString(R.string.token_expire));
            String body = JsonUtil.ObjectToString(mockResult);
            return response.newBuilder()
                    .code(HTTP_OK)
                    .body(ResponseBody.create(body, MediaType.get("application/json")))
                    .build();

        }
        return response;
    }
}
