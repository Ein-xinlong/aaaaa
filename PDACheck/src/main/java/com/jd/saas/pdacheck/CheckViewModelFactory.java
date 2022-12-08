package com.jd.saas.pdacheck;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacheck.category.CheckCategoryViewModel;
import com.jd.saas.pdacheck.flow.step1.CheckFlowStep1ViewModel;
import com.jd.saas.pdacheck.flow.step2.CheckFlowStep2ViewModel;
import com.jd.saas.pdacheck.flow.step3.CheckFlowStep3ViewModel;
import com.jd.saas.pdacheck.flow.step3.dialog.CheckReviewEditSkuPreOrderViewModel;

import org.jetbrains.annotations.NotNull;


/**
 * vm的工厂方法 用于生成指定的vm对象
 *
 * @author gouhetong
 */
public class CheckViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CheckFlowStep2ViewModel.class)) {
            return (T) new CheckFlowStep2ViewModel(CheckInject.injectMissedCheckRepository());
        } else if (modelClass.isAssignableFrom(CheckFlowStep1ViewModel.class)) {
            return (T) new CheckFlowStep1ViewModel(CheckInject.injectPreOrderListRepository());
        } else if (modelClass.isAssignableFrom(CheckCategoryViewModel.class)) {
            return (T) new CheckCategoryViewModel(CheckInject.injectCheckCategoryRepository());
        } else if (modelClass.isAssignableFrom(CheckFlowStep3ViewModel.class)) {
            return (T) new CheckFlowStep3ViewModel(CheckInject.injectCheckReviewRepository());
        } else if (modelClass.isAssignableFrom(CheckReviewEditSkuPreOrderViewModel.class)) {
            return (T) new CheckReviewEditSkuPreOrderViewModel(CheckInject.injectCheckReviewRepository());
        }
        return super.create(modelClass);
    }
}