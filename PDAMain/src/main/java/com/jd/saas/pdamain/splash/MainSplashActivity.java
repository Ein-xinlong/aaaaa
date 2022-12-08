package com.jd.saas.pdamain.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.router.MainRouterPath;
import com.jd.sentry.annotation.StartupMainActivity;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * app启动页
 *
 * @author majiheng
 */
@StartupMainActivity
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = MainRouterPath.HOST_MAIN, path = MainRouterPath.SPLASH_ACTIVITY_PATH)
public class MainSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_splash);
        countDown();
    }

    private void countDown() {
        CountDownTimer countDownTimer = new CountDownTimer(100,100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                // 判断是否登录，登录：跳转到主页 没登录：跳转到登录页
                RouterClient.getInstance().forward(MainSplashActivity.this, UserManager.isLogin() ? MainRouterPath.HOST_PATH_MAIN : "pda://native.LoginModule/LoginNewPage");
                finish();
            }
        };
        countDownTimer.start();
    }
}