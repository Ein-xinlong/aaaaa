package com.jd.saas.pdadelivery.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jd.saas.pdacommon.fragment.SimpleFragment;

public abstract class DeliveryBaseFragment extends SimpleFragment {
    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        observeLiveData();
        initData();
    }

    protected abstract void initView();

    protected abstract void observeLiveData();

    protected abstract void initData();
}
