package com.jd.saas.pdacommon.log;

import android.text.TextUtils;
import android.util.Log;

import com.jd.saas.pdacommon.BuildConfig;

/**
 * 日志打印，区分debug/release (可扩展工具类)
 *
 * @author majiheng
 */
public class Logger {

    public static boolean gIsDebug = BuildConfig.DEBUG;

    /**
     * 普通log打印
     */
    public static void d(String tag, String message) {
        if (gIsDebug) {
            if(!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
                Log.d(tag, message);
            }
        }
    }

    /**
     * 错误log打印
     */
    public static void e(String tag, String message) {
        if (gIsDebug) {
            if(!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
                Log.e(tag, message);
            }
        }
    }

    public static void eLong(String tag, String message) {
        if (gIsDebug) {
            int max_str_length = 2001 - tag.length();
            while (message.length() > max_str_length) {
                Log.i(tag, message.substring(0, max_str_length));
                message = message.substring(max_str_length);
            }
//            SDCardUtil.saveFileToCustomDir(message.getBytes(), DirConfig.cache,tag + DateUtils.getNow() + ".log");
            Log.e(tag, message);
        }
    }
}