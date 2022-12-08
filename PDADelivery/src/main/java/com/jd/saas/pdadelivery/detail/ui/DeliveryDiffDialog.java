package com.jd.saas.pdadelivery.detail.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jd.saas.pdacommon.dialog.SimpleListDialog;
import com.jd.saas.pdacommon.component.UpcCodeDialog;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.jd.saas.pdacommon.utils.ListUtils;
import com.jd.saas.pdadelivery.Inject;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailDiffDialogBinding;
import com.jd.saas.pdadelivery.detail.DeliveryDetailViewModel;
import com.jd.saas.pdadelivery.detail.adapter.DeliveryDiffSkuAdapter;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDetailBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDiffReasonBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDiffSkuBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 关单前显示和编辑收货差异的弹窗
 */
public class DeliveryDiffDialog extends BottomSheetDialogFragment {
    private DeliveryDetailDiffDialogBinding mDataBinding;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delivery_detail_diff_dialog, null);
        mDataBinding = DeliveryDetailDiffDialogBinding.bind(dialogView);
        Context context = mDataBinding.getRoot().getContext();
        int screenHeight = ScreenUtil.getScreenHeight(context) - ScreenUtil.getStatusBarHeight(context);
        int topHeight = ScreenUtil.dp2px(context, 18 + 9) + ScreenUtil.sp2px(context, 18);
        int bottomHeight = ScreenUtil.dp2px(context, 42) + ScreenUtil.sp2px(context, 20);
        int headerHeight = ScreenUtil.dp2px(context, 10) + ScreenUtil.sp2px(context, 14);
        int remainedHeight = screenHeight - topHeight - bottomHeight - headerHeight - ScreenUtil.dp2px(context, 25);
        int minHeight = Math.max(remainedHeight, ScreenUtil.dp2px(context, 150));
        int fixHeight = ScreenUtil.dp2px(context, 382);
        mDataBinding.recyclerView.getLayoutParams().height = Math.min(minHeight, fixHeight);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setOnShowListener(dialog -> bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED));
        return bottomSheetDialog;
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DeliveryDetailViewModel mViewModel = new ViewModelProvider(requireActivity(), Inject.injectViewModelFactory()).get(DeliveryDetailViewModel.class);
        final List<DeliveryDiffSkuBean> deliveryDiffSkuList = mViewModel.deliveryDiffSkuList.getValue();
        if (deliveryDiffSkuList == null) {
            dismiss();
            return;
        }
        DeliveryDiffSkuAdapter adapter = new DeliveryDiffSkuAdapter(deliveryDiffSkuList);
        adapter.setOnUpcMoreClickListener((view, bean) -> {
            String[] upcCodes = bean.getUpcCodes();
            if (upcCodes != null) {
                UpcCodeDialog.open(view, upcCodes);
            }
        });
        adapter.setOnSelectReasonClickListener((view, bean) -> {
            List<DeliveryDiffReasonBean> diffReasons = mViewModel.getDiffReasons(bean.getDiffType());
            SimpleListDialog<DeliveryDiffReasonBean> dialog = new SimpleListDialog<>(getContext(), R.string.delivery_diff_select_reason_dialog_title);
            dialog.setOnItemSelectListener(item -> {
                bean.setReasonDesc(item.getReasonDesc());
                bean.setReasonCode(item.getReasonCode());
                int i = deliveryDiffSkuList.indexOf(bean);
                if (i >= 0 && i < adapter.getItemCount()) {
                    adapter.notifyItemChanged(i);
                }
            });
            dialog.setOptions(ListUtils.map(diffReasons, from -> {
                SimpleListDialog.Option<DeliveryDiffReasonBean> option = new SimpleListDialog.Option<>();
                option.setName(from.getReasonDesc());
                option.setSelected(from.getReasonCode().equals(bean.getReasonCode()));
                option.setData(from);
                return option;
            }));
            dialog.show();
        });
        mDataBinding.recyclerView.setAdapter(adapter);
        mDataBinding.setOnCloseClick(v -> dismiss());
        mDataBinding.setOnEnsureClick(v -> {
            DeliveryDetailBean value = mViewModel.bean.getValue();
            if (value != null) {
                mViewModel.receiveAsnFinishByDiff(value.getAsnNo(), deliveryDiffSkuList);
            }
            dismiss();
        });
    }
}
