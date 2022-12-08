package com.jd.saas.pdacommon.flutter.channel;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;
import com.jdshare.jdf_container_plugin.components.channel.protocol.IJDFMessageResult;

import java.util.Map;

/**
 * Flutter登录过期消息通道
 * <p>
 * 接到flutter返回的登录过期消息时，退出登录状态
 * 不发送消息
 */
public class TokenExpireChannel extends FlutterChannel {

    public static final String MODULE_NAME = "pda_capture_channel";
    public static final String METHOD_LOGOUT = "method_logout";

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }


    @Override
    void onReceive(String methodName, Map<String, Object> map, IJDFMessageResult<Map> result) {
        if (METHOD_LOGOUT.equals(methodName)) {
            // 清空本地用户信息
            UserManager.getInstance().logout();
            // 关闭所有页面
            AppManager.getInstance().finishAllActivity();
            // 跳转到登录页
            RouterClient.getInstance().forward(MyApplication.getInstance(), "pda://native.LoginModule/LoginNewPage");
            MyToast.show(R.string.token_expire, false);
        }
    }

}
