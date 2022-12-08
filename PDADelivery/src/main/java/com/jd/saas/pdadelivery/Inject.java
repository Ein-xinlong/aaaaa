package com.jd.saas.pdadelivery;

import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdadelivery.net.DeliveryApi;
import com.jd.saas.pdadelivery.net.DeliveryRepository;
import com.jd.saas.pdadelivery.net.MockDeliveryRepository;
import com.jd.saas.pdadelivery.net.RemoteDeliveryRepository;

public class Inject {
    public static DeliveryApi injectApi() {
        return ApiMgr.getApi(DeliveryApi.class);
    }

    public static DeliveryRepository injectRepository() {
        return RemoteDeliveryRepository.getInstance(injectApi());
//        return new MockDeliveryRepository(injectApi());
    }

    public static DeliveryViewModelFactory injectViewModelFactory() {
        return new DeliveryViewModelFactory();
    }
}
