package com.jd.saas.pdadelivery.base;

import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacommon.fragment.NetViewModel;

public class DeliveryBaseViewModel extends NetViewModel {
    public final MutableLiveData<String> showToastEvent = new MutableLiveData<>();
}
