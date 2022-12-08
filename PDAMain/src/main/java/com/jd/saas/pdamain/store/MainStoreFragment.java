package com.jd.saas.pdamain.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.databinding.MainStoreActivityDataBinding;

public class MainStoreFragment extends SimpleFragment {
    private MainStoreActivityDataBinding mDataBinding;
    private MainStoreViewModel mViewModel;

    public static MainStoreFragment newInstance() {
        return new MainStoreFragment();

    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(MainStoreViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.recyclerview.setOrientation(RecyclerView.VERTICAL);
        mDataBinding.recyclerview.setEmptyView(mDataBinding.reStore);
        mDataBinding.recyclerview.setAdapter(mViewModel.getStoreListAdapter());
        mViewModel.getShopList();
        // 下拉刷新
        mDataBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getShopList();
            }
        });
        // 是否刷新监听
        mViewModel.mRefresh.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refresh) {
                mDataBinding.refresh.setRefreshing(refresh);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.main_activity_stroe;
    }

    @Override
    protected void reload() {
        mViewModel.getShopList();
    }
}
