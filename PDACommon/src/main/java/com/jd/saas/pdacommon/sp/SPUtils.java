package com.jd.saas.pdacommon.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.jd.saas.pdacommon.application.MyApplication;

/**
 * 一个SharedPreferences工具
 *
 * @author majiheng
 */
public class SPUtils {

    private static class SP {

        private static final String SP_NAME = "PDA_CONFIG";
        private static SharedPreferences sp;

        public static SharedPreferences get() {
            if (sp == null) {
                sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            }
            return sp;
        }
    }

    public static boolean saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = SP.get().edit();
        editor.putBoolean(key, value);
        editor.apply();
        return true;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return SP.get().getBoolean(key, defValue);
    }

    public static boolean saveString(String key, String value) {
        SharedPreferences.Editor editor = SP.get().edit();
        editor.putString(key, value);
        editor.apply();
        return true;
    }

    public static String getString(String key, String defValue) {
        return SP.get().getString(key, defValue);
    }

    public static boolean saveLong(String key, long value) {
        SharedPreferences.Editor editor = SP.get().edit();
        editor.putLong(key, value);
        editor.apply();
        return true;
    }

    public static long getLong(String key, long defValue) {
        return SP.get().getLong(key, defValue);
    }

    public static void saveInt(String key, int value) {
        SharedPreferences.Editor editor = SP.get().edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int getInt(String key,int defValue) {
        return SP.get().getInt(key, defValue);
    }


    public static void clean() {
        SharedPreferences.Editor edit = SP.get().edit();
        if (edit != null) {
            edit.clear().apply();
        }
    }

    public static void removeItem(String key) {
        SP.get().edit().remove(key).apply();
    }
}
