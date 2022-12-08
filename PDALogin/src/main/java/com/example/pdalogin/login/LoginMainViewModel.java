package com.example.pdalogin.login;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;

import androidx.databinding.ObservableField;

import com.example.pdalogin.R;
import com.example.pdalogin.net.LoginRepository;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.captcha.OnCaptchaResultCallback;
import com.jd.saas.pdacommon.captcha.CaptchaUtil;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;

import org.jetbrains.annotations.NotNull;

import jd.jnos.loginsdk.JNosLoginHelper;
import jd.jnos.loginsdk.common.listener.ErrorResult;
import jd.jnos.loginsdk.common.listener.OnCommonCallback;

/**
 * 登录vm
 *
 * @author majiheng
 */
public class LoginMainViewModel extends NetViewModel {

    // 网络请求
    private final LoginRepository mLoginRepository = new LoginRepository();

    // 是否是手机验证码登录，默认false
    public ObservableField<Boolean> mLoginByCode = new ObservableField<>(false);
    // 登录账号
    private String mCount;
    // 登录密码
    private String mPassWord;
    // 登录手机号
    private String mPhone;
    // 登录验证码
    private String mCode;
    // 发送验证码 & 120s后重新发送 的文案
    public ObservableField<String> mSendingSmsCodeStr = new ObservableField<>(MyApplication.getInstance().getString(R.string.login_sending_code));
    // 发送验证码按钮是否可点击，默认可点击
    public ObservableField<Boolean> mSendingSmsBtnClickable = new ObservableField<>(true);

    /**
     * 切换成手机验证码登录
     */
    public void loginMode() {
        mLoginByCode.set(!mLoginByCode.get());
    }

    /**
     * 回调输入手机号/账号
     */
    public void setAccountStr(Editable editable) {
        mCount = editable.toString();
    }

    /**
     * 回调输入密码
     */
    public void setPasswordStr(Editable editable) {
        mPassWord = editable.toString();
    }

    /**
     * 回调输入手机号
     */
    public void setPhoneStr(Editable editable) {
        mPhone = editable.toString();
    }

    /**
     * 回调输入密码
     */
    public void setCodeStr(Editable editable) {
        mCode = editable.toString();
    }

    /**
     * 发送验证码
     */
    public void sendingCode(Context context) {
        if (TextUtils.isEmpty(mPhone)) {
            MyToast.show(context.getString(R.string.login_phone_num_please), false);
            return;
        }
        CaptchaUtil.go(context, new OnCaptchaResultCallback() {
            @Override
            public void onCaptchaResult(String result) {
                // 发送验证码
                showProgress.set(true);
                JNosLoginHelper.INSTANCE.getLoginHelper().sendSmsCode(mPhone, result, "2", new OnCommonCallback<Object>() {
                    @Override
                    public void onSuccess() {
                        showProgress.set(false);
                        // 验证码发送成功
                        MyToast.show(R.string.login_sending_code_success,false);
                        // 启动倒计时-120s倒计时-根据JNOS验证码登录规则是120s
                        mSendingSmsBtnClickable.set(false);
                        CountDownTimer countDownTimer = new CountDownTimer(120000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                mSendingSmsCodeStr.set(millisUntilFinished / 1000 + context.getString(R.string.login_sending_code_retry));
                            }

                            @Override
                            public void onFinish() {
                                mSendingSmsCodeStr.set(context.getString(R.string.login_sending_code));
                                mSendingSmsBtnClickable.set(true);
                            }
                        };
                        countDownTimer.start();
                    }

                    @Override
                    public void onSuccess(Object o) {
                        showProgress.set(false);
                    }

                    @Override
                    public void onError(@NotNull ErrorResult errorResult) {
                        showProgress.set(false);
                        MyToast.show(errorResult.getErrorMsg(),false);
                    }

                    @Override
                    public void onFail(@NotNull ErrorResult errorResult) {
                        showProgress.set(false);
                        MyToast.show(errorResult.getErrorMsg(),false);
                    }
                });
            }
        });
    }

    /**
     * 判断登录账号是否已迁移到JNOS（后续所有商家账号迁移完毕后此流程会移除！）
     */
    public void login() {
        if(mLoginByCode.get()) {
            // 手机验证码登录
            doLogin4JNOSByCode();
        }else {
            // 账号密码登录
            doLogin4JNOS();
        }
    }

    /**
     * JNOS账号密码登录
     */
    private void doLogin4JNOS() {
        Context context = MyApplication.getInstance().getApplicationContext();
        if (TextUtils.isEmpty(mCount)) {
            MyToast.show(context.getString(R.string.login_count), false);
            return;
        }
        if (TextUtils.isEmpty(mPassWord)) {
            MyToast.show(context.getString(R.string.login_password), false);
            return;
        }
        showProgress.set(true);
        JNosLoginHelper.INSTANCE.getLoginHelper().accountLogin(mCount, mPassWord, new OnCommonCallback<Object>() {

            @Override
            public void onSuccess() {
                showProgress.set(false);
                // 当账号属于「单个组织」时，获取的是token
                String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getToken();
                loginAction(jnosToken);
            }

            @Override
            public void onSuccess(Object o) {
                showProgress.set(false);
                // 当账号属于「多组织」时，获取的是middle token
                String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getMiddleToken();
                loginAction(jnosToken);
            }

            @Override
            public void onFail(@NotNull ErrorResult errorResult) {
                showProgress.set(false);
                MyToast.show(errorResult.getErrorMsg(),false);
            }

            @Override
            public void onError(@NotNull ErrorResult errorResult) {
                showProgress.set(false);
                MyToast.show(errorResult.getErrorMsg(),false);
            }
        },null);
    }

    /**
     * JNOS手机验证码登录
     */
    private void doLogin4JNOSByCode() {
        Context context = MyApplication.getInstance().getApplicationContext();
        if (TextUtils.isEmpty(mPhone)) {
            MyToast.show(context.getString(R.string.login_phone_num_please), false);
            return;
        }
        if (TextUtils.isEmpty(mCode)) {
            MyToast.show(context.getString(R.string.login_code), false);
            return;
        }
        showProgress.set(true);
        JNosLoginHelper.INSTANCE.getLoginHelper().smsCodeLogin(mPhone, mCode, new OnCommonCallback<Object>() {
            @Override
            public void onSuccess() {
                showProgress.set(false);
                // 当账号属于「单个组织」时，获取的是token
                String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getToken();
                loginAction(jnosToken);
            }

            @Override
            public void onSuccess(Object o) {
                showProgress.set(false);
                // 当账号属于「多组织」时，获取的是middle token
                String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getMiddleToken();
                loginAction(jnosToken);
            }

            @Override
            public void onError(@NotNull ErrorResult errorResult) {
                showProgress.set(false);
                MyToast.show(errorResult.getErrorMsg(),false);
            }

            @Override
            public void onFail(@NotNull ErrorResult errorResult) {
                showProgress.set(false);
                MyToast.show(errorResult.getErrorMsg(),false);
            }
        },null);
    }

    /**
     * JNOS账号密码登录成功后的操作
     */
    private void loginAction(String jnosToken) {
        if(!TextUtils.isEmpty(jnosToken)) {
            // 更新本地用户数据（暂时更新：mCount不是真正的用户pin，token在选择租户时也需要更新）
            UserData userData = new UserData();
            userData.setToken(jnosToken);
            userData.setUserPin(mCount);
            UserManager.getInstance().setUserData(userData);
            // 提示登录成功
            Context context = MyApplication.getInstance().getApplicationContext();
            MyToast.show(context.getString(R.string.login_success), false);
            // 跳转到「企业选择」页
            RouterClient.getInstance().forward(context, "pda://native.LoginModule/ChooesenterpriseNewPage");
            AppManager.getInstance().finishActivity();
        }else {
            MyToast.show("JNOS Token is null.", false);
        }
    }
}
