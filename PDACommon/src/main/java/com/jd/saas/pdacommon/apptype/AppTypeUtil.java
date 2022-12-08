package com.jd.saas.pdacommon.apptype;

import java.lang.reflect.Field;

/**
 * 用于判断当前app类型，门店：1，仓：2
 *
 * @author majiheng
 */
public class AppTypeUtil {
    private static int appType = -1;

    private static String getAppFlavor() {
        try {
            Class<?> aClass = Class.forName("com.jd.saas.pda.BuildConfig");
            Field flavor = aClass.getDeclaredField("FLAVOR");
            return (String) flavor.get(aClass);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            return "unknown";
        }
    }

    public static int getAppType() {
        if (appType == -1) {
            try {
                Class<?> aClass = Class.forName("com.jd.saas.pda.BuildConfig");
                Field flavor = aClass.getDeclaredField("APP_TYPE");
                appType = flavor.getInt(aClass);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                appType = 0;
            }
        }
        return appType;
    }

    public static String getChannel() {
        return getAppType() == 1 ? "store" : "warehouse";
    }
}
