package com.jd.saas.pdadelivery.net.param;

import com.jd.saas.pdadelivery.util.DeliveryConfigProvider;

public class ClientInfoParam {
    /**
     * 1门店 2仓
     */
    private int bizCode = DeliveryConfigProvider.getClientType().getValue();
}
