package com.jd.saas.pdacommon.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.core.content.pm.PackageInfoCompat;

import com.jd.saas.pdacommon.BuildConfig;
import com.jd.saas.pdacommon.log.Logger;

public class PackageUtil {
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Logger.e("VersionInfo", "Exception" + e);
        }
        if (TextUtils.isEmpty(versionName)) {
            versionName = "";
        }
        if (BuildConfig.DEBUG) {
            return versionName + "-debug";
        }
        return versionName;
    }

    public static long getVersionCode(Context context) {
        long versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = PackageInfoCompat.getLongVersionCode(pi);
        } catch (Exception e) {
            Logger.e("VersionInfo", "Exception" + e);
        }
        return versioncode;
    }
}
