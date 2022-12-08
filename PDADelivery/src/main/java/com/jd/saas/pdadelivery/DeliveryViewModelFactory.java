package com.jd.saas.pdadelivery;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdadelivery.detail.DeliveryDetailViewModel;
import com.jd.saas.pdadelivery.manage.DeliveryManageViewModel;

import org.jetbrains.annotations.NotNull;

public class DeliveryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeliveryManageViewModel.class)){
            return (T) new DeliveryManageViewModel(Inject.injectRepository());
        }else if (modelClass.isAssignableFrom(DeliveryDetailViewModel.class)){
            return (T) new DeliveryDetailViewModel(Inject.injectRepository());
        }
        return super.create(modelClass);
    }
}
