package com.jd.saas.padinventory.adjustment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.padinventory.R;

import com.jd.saas.padinventory.databinding.InventoryAdjustmentDataBinding;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.toast.MyToast;

/**
 * 订单详情页fragment
 *
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
public class InventoryAdjustmentFragment extends SimpleFragment {

    private InventoryAdjustmentDataBinding mDataBinding;
    private InventoryAdjustmentViewModel mViewModel;
    private static String mGalNo="NO";
    public static InventoryAdjustmentFragment newInstance() {
        return new InventoryAdjustmentFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(InventoryAdjustmentViewModel.class);
        mDataBinding.setAdjustment(mViewModel);
        mGalNo=getActivity().getIntent().getStringExtra("galno");
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.handleBaseNetUI(this);
        mDataBinding.recyclerview.setLayoutManager(mViewModel.getLinearLayoutManager(getActivity()));
        mDataBinding.recyclerview.setAdapter(mViewModel.getmInventoryAdjustmentAdapter());
        mViewModel.addList(mGalNo);

    }

    @Override
    protected void reload() {

    }


    @Override
    protected int getLayout() {
        return R.layout.inventory_adjustment_fragment_main;
    }
}
