package com.jd.saas.pdacommon.notification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.router.FlutterRouter;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.user.UserManager;

/**
 * 通知栏消息点击中转activity
 * @author majiheng
 */
public class NotificationActivity extends AppCompatActivity {

    public static final String NOTIFICATION_ACTION = "notification_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(UserManager.isLogin()) {
            // 用户已经登录
            if(AppManager.getInstance().isActivityEmpty()) {
                // 判断当前app是否有存在的activity
                RouterClient.getInstance().forward(this, "pda://native.MainModule/MainNewPage");
            }
            int notificationAction = getIntent().getIntExtra(NOTIFICATION_ACTION,0);
            if (notificationAction == 1) {
                // 跳转到「拣货」菜单
                FlutterRouter.openPdaPickingPlugin(this, null);
            }
        }else {
            // 用户未登录-跳转到登录页
            RouterClient.getInstance().forward(this, "pda://native.MainModule/MainSplashPage");
        }
        finish();
    }
}