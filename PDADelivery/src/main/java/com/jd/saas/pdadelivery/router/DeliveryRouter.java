package com.jd.saas.pdadelivery.router;

import android.content.Context;
import android.os.Bundle;

import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdadelivery.manage.bean.DeliveryBean;

import static com.jd.saas.pdacommon.zxing.router.ZxingRouterPath.PATH_ZXING;
import static com.jd.saas.pdadelivery.router.DeliveryRouterConfig.PARAM_DELIVERY_BEAN;
import static com.jd.saas.pdadelivery.router.DeliveryRouterConfig.PATH_DETAIL;
import static com.jd.saas.pdadelivery.router.DeliveryRouterConfig.REQUEST_DETAIL_EDIT_RESULT;
import static com.jd.saas.pdadelivery.router.DeliveryRouterConfig.REQUEST_SCAN_CODE;

public class DeliveryRouter {
    public static void goDetail(Context context, DeliveryBean DeliveryBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARAM_DELIVERY_BEAN, DeliveryBean);
        RouterClient.getInstance().forward(context, PATH_DETAIL, bundle, REQUEST_DETAIL_EDIT_RESULT);
    }

    public static void goScanForResult(Context context) {
        RouterClient.getInstance().forward(context, PATH_ZXING, new Bundle(), REQUEST_SCAN_CODE);
    }
}
