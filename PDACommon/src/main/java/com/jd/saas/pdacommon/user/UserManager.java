package com.jd.saas.pdacommon.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.json.JsonUtil;

/**
 * 用户管理
 *
 * @author majiheng
 */
public class UserManager {

    private static final String SP_NAME = "UserManager";
    private static UserManager sInstance = null;
    private final SharedPreferences mPref;

    private UserManager() {
        mPref = MyApplication.getInstance().getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     */
    public static UserManager getInstance() {
        if (sInstance == null) {
            sInstance = new UserManager();
        }
        return sInstance;
    }

    /**
     * 设置用户数据
     */
    public void setUserData(UserData user) {
        if (user == null) {
            return;
        }
        String strUser = JsonUtil.ObjectToString(user);
        mPref.edit().putString("userData", strUser).apply();
    }

    /**
     * 获取用户数据
     */
    public UserData getUserData() {
        String user = mPref.getString("userData", "");
        if (TextUtils.isEmpty(user)) {
            return new UserData();
        }
        return JsonUtil.StringToObject(user, UserData.class);
    }

    /**
     * 获取token
     */
    public static String getToken() {
        return getInstance().getUserData().getToken();
    }

    /**
     * 更新用户数据
     */
    public static void updateUser(UserData userData) {
        getInstance().setUserData(userData);
    }

    /**
     * 是否已登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getInstance().getUserData().getToken());
    }

    /**
     * 登出，清空用户数据
     */
    public void logout() {
        // 清空sp所有内容
        mPref.edit().clear().apply();
    }
}
