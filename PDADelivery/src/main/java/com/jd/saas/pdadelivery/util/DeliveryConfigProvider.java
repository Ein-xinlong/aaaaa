package com.jd.saas.pdadelivery.util;

import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.enums.AppType;

public class DeliveryConfigProvider {
    private static final AppType clientType = AppTypeUtil.getAppType() == 2 ? AppType.WAREHOUSE : AppType.STORE;

    public static AppType getClientType() {
        return clientType;
    }
}
