package com.jd.saas.padinventory.adjustment.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.InventoryAdjustmentDetailDataBinding;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/1
 */
public class InventoryAdjustmentDetailFragment extends SimpleFragment {

    private InventoryAdjustmentDetailDataBinding mBinding;
    private InventoryAdjustmentDetailViewModel mViewModel;

    public static InventoryAdjustmentDetailFragment getInstance() {
        return new InventoryAdjustmentDetailFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(InventoryAdjustmentDetailViewModel.class);
        mBinding.setVm(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DialogBaseView dialogBaseView = new DialogBaseView(mContext,"");
        mViewModel.handleBaseNetUI(dialogBaseView);
        mBinding.recyclerview.setOrientation(RecyclerView.VERTICAL);
        mBinding.recyclerview.setEmptyView(mBinding.tvNoData);
        mBinding.recyclerview.setAdapter(mViewModel.getDetailAdapter());
        mViewModel.refreshUI();
    }

    @Override
    protected int getLayout() {
        return R.layout.inventory_adjustment_fragment_detail;
    }

    @Override
    protected void reload() {
        // do nothing
    }
}
