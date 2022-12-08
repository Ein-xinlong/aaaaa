package com.jd.saas.pdacommon.printer_ble.router;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

public class BlueToothPrintRouterPath {
    public static final String HOST_BLUETOOTH = "CommonModule";
    public static final String PAGE_BLUETOOTH = "/BlueToothPage";
    public static final String COMMON_PATH__BLUETOOTH = SCHAME + "://" + NATIVE_TYPE + HOST_BLUETOOTH + PAGE_BLUETOOTH;
}
