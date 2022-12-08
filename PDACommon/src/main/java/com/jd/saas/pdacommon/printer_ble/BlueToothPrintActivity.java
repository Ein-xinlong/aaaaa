package com.jd.saas.pdacommon.printer_ble;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.printer_ble.router.BlueToothPrintRouterPath;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

@JDRouteUri(scheme = RouterBasePath.SCHAME, host = BlueToothPrintRouterPath.HOST_BLUETOOTH, path = BlueToothPrintRouterPath.PAGE_BLUETOOTH)
public class BlueToothPrintActivity extends SimpleActivity {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.common_blue_connecting_to_bluetooth);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return BlueToothPrintFragment.newInstance();
    }

}
