package com.jd.saas.pdadelivery.detail.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jd.saas.pdacommon.dialog.SimpleListDialog;
import com.jd.saas.pdacommon.utils.ListUtils;
import com.jd.saas.pdadelivery.Inject;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.detail.DeliveryDetailViewModel;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryStockTypeBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 库位选择弹窗
 */
public class DeliverySelectStockTypeDialog extends BottomSheetDialogFragment {
    private SimpleListDialog<DeliveryStockTypeBean> simpleListDialog;
    private OnSelectStockTypeListener onSelectStockTypeListener;
    private String locId;

    /**
     * 选择库位后的回调
     */
    public interface OnSelectStockTypeListener {
        void onSelectStockType(DeliveryStockTypeBean stockTypeBean);
    }

    /**
     * 设置选中的库位
     */
    public void setLocId(String locId) {
        this.locId = locId;
    }

    public void setOnSelectStockTypeListener(OnSelectStockTypeListener onSelectStockTypeListener) {
        this.onSelectStockTypeListener = onSelectStockTypeListener;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        simpleListDialog = new SimpleListDialog<>(requireContext(), R.string.delivery_detail_choose_storage);
        return simpleListDialog.getDialog();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DeliveryDetailViewModel mViewModel = new ViewModelProvider(requireActivity(), Inject.injectViewModelFactory()).get(DeliveryDetailViewModel.class);
        DeliverySkuBean editSkuValue = mViewModel.editSku.getValue();
        if (editSkuValue == null) {
            return;
        }
        List<DeliveryStockTypeBean> stockTypeList = mViewModel.getStockTypeList(editSkuValue.getSkuType());
        simpleListDialog.setOptions(ListUtils.map(stockTypeList, from -> {
            SimpleListDialog.Option<DeliveryStockTypeBean> option = new SimpleListDialog.Option<>();
            option.setName(from.getLocName());
            option.setSelected(from.getLocId() != null && from.getLocId().equals(locId));
            option.setData(from);
            return option;
        }));
        simpleListDialog.setOnItemSelectListener(bean -> {
            if (onSelectStockTypeListener != null) {
                onSelectStockTypeListener.onSelectStockType(bean);
            }
        });
    }
}
