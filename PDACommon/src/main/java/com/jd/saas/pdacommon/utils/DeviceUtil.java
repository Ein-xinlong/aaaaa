package com.jd.saas.pdacommon.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.jingdong.sdk.uuid.UUID;


public class DeviceUtil {
    // 厂商
    public static final String PDA_MANUFACTURER_SUNMI = "SUNMI"; // 商米设备
    public static final String PDA_MANUFACTURER_UROVO_1 = "urovo";// 优博讯设备
    public static final String PDA_MANUFACTURER_UROVO_2 = "UBX";// 优博讯设备
    // 指定型号
    public static final String PDA_MODEL_UROVO_I9000S = "i9000S";// 优博讯设备i9000S

    public static String readDeviceUUID(Context context) {
        String uuid;
        if (!TextUtils.isEmpty(UUID.readAndroidId(context))) {
            // 同意隐私权限
            uuid = UUID.readAndroidId(context);
        } else {
            uuid = UUID.readInstallationId(context);
        }
        return uuid;
    }

    public static boolean isPDA() {
        return getDeviceType() != DeviceType.MOBILE;
    }

    // 获取设备类型：不同品牌PDA、普通手机
    public static DeviceType getDeviceType() {
        String manufacturerTemp = Build.MANUFACTURER;
        if(null == manufacturerTemp) {
            manufacturerTemp = "";
        }
        String manufacturer = manufacturerTemp.trim();
        if (PDA_MANUFACTURER_SUNMI.equalsIgnoreCase(manufacturer)) {
            return DeviceType.SUNMI;
        }
        if (PDA_MANUFACTURER_UROVO_1.equalsIgnoreCase(manufacturer) || PDA_MANUFACTURER_UROVO_2.equalsIgnoreCase(manufacturer)) {
            return DeviceType.UROVO;
        }
        return DeviceType.MOBILE;
    }

    enum DeviceType {
        /**
         * 这里将没有适配的设备统一作为普通手机处理
         */
        MOBILE,
        SUNMI,
        UROVO,
    }
}
