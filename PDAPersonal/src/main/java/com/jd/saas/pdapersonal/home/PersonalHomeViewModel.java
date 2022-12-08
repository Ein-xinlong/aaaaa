package com.jd.saas.pdapersonal.home;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.upgrade.JdAvatar;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.webview.PDAWebViewConfigBean;
import com.jd.saas.pdacommon.webview.PDAWebViewRouterPath;
import com.jd.saas.pdapersonal.BuildConfig;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.router.PersonalRouterPath;

import org.jetbrains.annotations.NotNull;

import java.net.URLEncoder;

import jd.jnos.loginsdk.JNosLoginHelper;
import jd.jnos.loginsdk.common.listener.ErrorResult;
import jd.jnos.loginsdk.common.listener.OnCommonCallback;

/**
 * 个人中心VM
 *
 * @author mengmeng
 */
public class PersonalHomeViewModel extends NetViewModel {

    // 用户名
    public ObservableField<String> mUserName = new ObservableField<>();
    // 用户pin
    public ObservableField<String> mUserPin = new ObservableField<>();
    // 版本信息
    public ObservableField<String> mAppVersion = new ObservableField<>();
    // 显隐菜单条目
    public ObservableField<Boolean> mItemVisible = new ObservableField<>(false);
    // 关闭抽屉
    public final MutableLiveData<Boolean> liveDataDrawer = new MutableLiveData<Boolean>();

    /**
     * 关闭个人中心
     */
    public void clickClose() {
        closeDrawer();
    }

    /**
     * 切换租户门店
     */
    public void exchangeStore(View view) {
        closeDrawer();
        RouterClient.getInstance().forward(view.getContext(), PersonalRouterPath.HOST_PATH_COMPANY_EXCHANGE);
    }

    /**
     * 版本信息
     */
    public void clickVersion() {
        JdAvatar.forceCheck();
        MyToast.show(R.string.personal_tv_version_checking, true);
        closeDrawer();
    }

    /**
     * 小京铃工单反馈
     */
    public void clickFeedBack(View view) {
        closeDrawer();
        try {
            // 设置网页名称 & url
            Bundle bundle = new Bundle();
            PDAWebViewConfigBean bean = new PDAWebViewConfigBean();
            bean.setShowTitle(true);
            bean.setTitle(view.getContext().getString(R.string.personal_tv_feedback));
            String host;
            String platformId;
            if(BuildConfig.DEBUG) {
                // 预发环境
                host = "https://beat-itsv-h5.jd.com/#/";
                platformId = "48";
            }else {
                // 正式环境
                host = "https://itsv-h5.jd.com/#/";
                platformId = "63";
            }
            UserData userData = UserManager.getInstance().getUserData();
            String url = host + "report?platformId=" + platformId + "&source=21&outsideId=" + userData.getTenantId()
                    + "&userId=" + userData.getUserPin()
                    + "&userName=" + URLEncoder.encode(userData.getUserName(),"utf-8")
                    + "&phoneNumber=" + userData.getMobile()
                    + "&shopId=" + userData.getShopId()
                    + "&shopName=" + URLEncoder.encode(userData.getShopName(),"utf-8");
            bean.setUrl(url);
            bundle.putSerializable(PDAWebViewRouterPath.WEB_CONFIG,bean);
            RouterClient.getInstance().forward(view.getContext(), PDAWebViewRouterPath.HOST_PATH_PDA_WEB_VIEW,bundle);
        }catch (Exception e) {
            Logger.e("err",e.getMessage());
        }
    }

    /**
     * 清理缓存
     */
    public void clickClean() {
        MyToast.show(R.string.personal_clear, false);
        closeDrawer();
    }

    /**
     * Flutter网路代理
     */
    public void clickFlutterProxy(View view) {
        RouterClient.getInstance().forward(view.getContext(), PersonalRouterPath.HOST_PATH_FLUTTER_PROXY);
        closeDrawer();
    }

    /**
     * 关闭抽屉标识，true表示关闭
     */
    private void closeDrawer() {
        liveDataDrawer.postValue(true);
    }

    /**
     * 初始化ui
     */
    public void initUI(Context context) {
        UserData userData = UserManager.getInstance().getUserData();
        mUserName.set(userData.getUserName());
        mUserPin.set(userData.getUserPin());
        mAppVersion.set(context.getString(R.string.personal_tv_version) + getAppVersionName(context));
        mItemVisible.set(BuildConfig.DEBUG);
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {

        }
        return versionName;
    }

    /**
     * 注销按钮
     */
    public void logout(Context context) {
        new SimpleAlertDialog.Builder(context, R.string.personal_logout_confirm)
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jNosLogout();
                    }
                }).build().show();
    }

    /**
     * JNos登出
     */
    private void jNosLogout() {
        showProgress.set(true);
        // 登出接口回调
        JNosLoginHelper.INSTANCE.logout(new OnCommonCallback<Object>() {
            @Override
            public void onSuccess() {
                showProgress.set(false);
                logoutAction();
            }

            @Override
            public void onSuccess(Object o) {
                showProgress.set(false);
            }

            @Override
            public void onError(@NotNull ErrorResult errorResult) {
                showProgress.set(false);
                // 就算请求失败，同样退出登录，jnos sdk同事告知
                logoutAction();
            }

            @Override
            public void onFail(@NotNull ErrorResult errorResult) {
                showProgress.set(false);
                // 就算请求失败，同样退出登录，jnos sdk同事告知
                logoutAction();
            }
        });
    }

    /**
     * 退出登录后的动作
     */
    private void logoutAction() {
        // 清空JNos SDK本地登录态
        JNosLoginHelper.INSTANCE.exitLogin();
        MyToast.show(R.string.personal_logout_success, false);
        // 清空本地用户信息
        UserManager.getInstance().logout();
        // 关闭所有页面
        AppManager.getInstance().finishAllActivity();
        // 跳转到登录页
        Context context = MyApplication.getInstance().getApplicationContext();
        RouterClient.getInstance().forward(context, "pda://native.LoginModule/LoginNewPage");
    }
}
