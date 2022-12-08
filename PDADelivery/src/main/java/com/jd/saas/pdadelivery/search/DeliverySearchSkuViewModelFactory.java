package com.jd.saas.pdadelivery.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacommon.net.ApiMgr;

import org.jetbrains.annotations.NotNull;

public class DeliverySearchSkuViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeliverySearchSkuViewModel.class)) {
            return (T) new DeliverySearchSkuViewModel(new DeliverySearchSkuRepository(ApiMgr.getApi(DeliverySearchApi.class)));
        }
        return super.create(modelClass);
    }
}