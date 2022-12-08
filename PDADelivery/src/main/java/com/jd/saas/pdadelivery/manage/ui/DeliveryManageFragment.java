package com.jd.saas.pdadelivery.manage.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;
import com.jd.saas.pdadelivery.Inject;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.base.DeliveryBaseFragment;
import com.jd.saas.pdadelivery.databinding.DeliveryManageLayoutBinding;
import com.jd.saas.pdadelivery.manage.DeliveryManageViewModel;
import com.jd.saas.pdadelivery.manage.adapter.DeliveryManageAdapter;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.router.DeliveryRouter;
import com.jd.saas.pdadelivery.router.DeliveryRouterConfig;
import com.jd.saas.pdadelivery.util.ScannerUtil;
import com.jd.saas.pdadelivery.util.TabUtils;

/**
 * 收货管理
 *
 * @author ext.mengmeng
 */
public class DeliveryManageFragment extends DeliveryBaseFragment {

    private DeliveryManageLayoutBinding mDataBinding;
    private DeliveryManageViewModel mViewModel;
    private DeliveryManageAdapter mAdapter;
    private DeliveryFilterDialog filterDialog;


    public static DeliveryManageFragment newInstance() {
        return new DeliveryManageFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this, Inject.injectViewModelFactory()).get(DeliveryManageViewModel.class);
        mDataBinding.setVm(mViewModel);
        mViewModel.handleBaseNetUI(this);
    }


    @Override
    protected void initView() {
        filterDialog = new DeliveryFilterDialog(mDataBinding.getRoot().getContext());
        filterDialog.setOnFilterChangeListener((selectTypes, start, end) -> {
            if (start != null && end != null) {
                mViewModel.timeRange.setValue(new Pair<>(start, end));
            } else {
                mViewModel.timeRange.setValue(null);
            }
            mViewModel.setSelectTypes(selectTypes);
            mViewModel.refresh();
        });
        TabLayout tableLayout = mDataBinding.tableLayout;
        tableLayout.addTab(TabUtils.createTab(tableLayout, R.string.delivery_status_initial, true));
        tableLayout.addTab(TabUtils.createTab(tableLayout, R.string.delivery_status_receiving, false));
        tableLayout.addTab(TabUtils.createTab(tableLayout, R.string.delivery_status_received, false));
        //tabLayout监听事件
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabUtils.showAsSelect(tab);
                AsnStatusEnum asnStatusEnum = null;
                switch (tab.getPosition()) {
                    case 0:
                        asnStatusEnum = AsnStatusEnum.INITIAL;
                        break;
                    case 1:
                        asnStatusEnum = AsnStatusEnum.PART_RECEIVE;
                        break;
                    case 2:
                        asnStatusEnum = AsnStatusEnum.RECEIVED;
                        break;
                }
                if (asnStatusEnum == mViewModel.asnStatusEnum.getValue()) {
                    return;
                }
                mViewModel.asnStatusEnum.setValue(asnStatusEnum);
                mViewModel.refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabUtils.showAsUnselect(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //recyclerview设置
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DeliveryManageAdapter(item -> DeliveryRouter.goDetail(getContext(), item));
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.setOnFilterClickListener(v -> openFilterDialog());
        mDataBinding.setOnScanCodeClickListener(v -> DeliveryRouter.goScanForResult(getContext()));
        mDataBinding.refreshLayout.setOnRefreshListener(() -> mViewModel.refresh());
        ScannerUtil.registerEditorListener(mDataBinding.etSearch, str -> mViewModel.refresh());
    }

    @Override
    protected void observeLiveData() {
        //adapter数据监听，无数据的时候，图片显示，
        mViewModel.deliveryList.observe(getViewLifecycleOwner(), pagedList -> {
            mAdapter.submitList(pagedList);
        });

        mViewModel.isEmpty.observe(getViewLifecycleOwner(), isEmpty -> {
            if (isEmpty) {
                mDataBinding.refreshLayout.setVisibility(View.GONE);
                mDataBinding.layoutNoData.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.refreshLayout.setVisibility(View.VISIBLE);
                mDataBinding.layoutNoData.setVisibility(View.GONE);
            }
        });
        mViewModel.showToastEvent.observe(getViewLifecycleOwner(), msg -> {
            MyToast.show(msg, false);
        });
        mViewModel.timeRange.observe(this, range -> {
            if (range != null) {
                filterDialog.setRange(range.first, range.second);
            } else {
                filterDialog.setRange(null, null);
            }
        });
        mViewModel.asnStatusEnum.observe(this, asnStatusEnum -> {
            switch (asnStatusEnum) {
                case INITIAL:
                    TabLayout.Tab tab0 = mDataBinding.tableLayout.getTabAt(0);
                    if (tab0 == null) {
                        return;
                    }
                    tab0.select();
                    break;
                case PART_RECEIVE:
                    TabLayout.Tab tab1 = mDataBinding.tableLayout.getTabAt(1);
                    if (tab1 == null) {
                        return;
                    }
                    tab1.select();
                    break;
                case RECEIVED:
                    TabLayout.Tab tab2 = mDataBinding.tableLayout.getTabAt(2);
                    if (tab2 == null) {
                        return;
                    }
                    tab2.select();
                    break;
            }
        });
        mViewModel.searchStr.observe(this, str -> {
            mViewModel.refresh();
            if (TextUtils.isEmpty(str)) {
                SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
                mDataBinding.etSearch.clearFocus();
            }
        });
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, code -> {
            mViewModel.searchStr.setValue(code);
        });
        mViewModel.isRefresh.observe(this, isRefresh -> mDataBinding.refreshLayout.setRefreshing(isRefresh));
    }

    @Override
    protected void initData() {
        mViewModel.reset();
    }


    @Override
    protected int getLayout() {
        return R.layout.delivery_manage_layout;
    }


    @Override
    protected void reload() {

    }

    /*
    bottomSheetDialog创建
     */
    private void openFilterDialog() {
        filterDialog.open();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DeliveryRouterConfig.REQUEST_SCAN_CODE) {
            if (data != null) {
                String str = data.getStringExtra(Constant.CODED_CONTENT);
                mViewModel.searchStr.setValue(str);
            }
        } else if (requestCode == DeliveryRouterConfig.REQUEST_DETAIL_EDIT_RESULT && resultCode == Activity.RESULT_OK) {
            mViewModel.refresh();
        }
    }
}