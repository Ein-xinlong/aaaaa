package com.jd.saas.pdacheck.flow.step3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jd.saas.pdacheck.CheckInject;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckDialogReviewEditSkuQtyBinding;
import com.jd.saas.pdacheck.flow.step3.adapter.CheckReviewPreOrderAdapter;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuBean;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.screen.ScreenUtil;

import org.jetbrains.annotations.NotNull;

/**
 * 复核修改商品数量的弹窗
 *
 * @author gouhetong
 */
public class CheckReviewEditSkuPreOrderDialog extends BottomSheetDialogFragment {
    public static CheckReviewEditSkuPreOrderDialog getInstance(String taskNo, CheckReviewSkuBean skuBean) {
        CheckReviewEditSkuPreOrderDialog checkReviewEditSkuPreOrderDialog = new CheckReviewEditSkuPreOrderDialog();
        Bundle bundle = new Bundle();
        bundle.putString("taskNo", taskNo);
        bundle.putParcelable("bean", skuBean);
        checkReviewEditSkuPreOrderDialog.setArguments(bundle);
        return checkReviewEditSkuPreOrderDialog;
    }

    private CheckDialogReviewEditSkuQtyBinding mDataBinding;
    private CheckReviewEditSkuPreOrderViewModel mViewModel;
    private OnSubmitSuccessListener onSubmitSuccessListener;
    private OnDismissListener onDismissListener;
    private CheckReviewPreOrderAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.check_dialog_review_edit_sku_qty, null);
        mDataBinding = CheckDialogReviewEditSkuQtyBinding.bind(view);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this, CheckInject.injectViewModelFactory()).get(CheckReviewEditSkuPreOrderViewModel.class);
        mDataBinding.setVm(mViewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
        DialogBaseView dialogBaseView = new DialogBaseView(getContext(),"");
        mViewModel.handleBaseNetUI(dialogBaseView);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(true);
            if (dialog instanceof BottomSheetDialog) {
                ((BottomSheetDialog) dialog).getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            dismiss();
            return;
        }
        String taskNo = arguments.getString("taskNo");
        CheckReviewSkuBean bean = arguments.getParcelable("bean");
        if (taskNo == null || bean == null) {
            dismiss();
            return;
        }
        mAdapter = new CheckReviewPreOrderAdapter(bean.getSaleMode());
        mDataBinding.orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBinding.orderList.setAdapter(mAdapter);
        mDataBinding.orderList.postDelayed(() -> {
            mDataBinding.orderList.requestFocus();
            SoftInputUtil.showSoftInput(mDataBinding.getRoot().getContext());
        }, 50);
        // 关闭按钮
        mDataBinding.ivClose.setOnClickListener(v -> dismiss());
        //确定按钮
        mDataBinding.buttonSure.setOnClickListener(v -> mViewModel.submit(mAdapter.getOrderBeans()));
        mViewModel.closeEvent.observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess && onSubmitSuccessListener != null) {
                onSubmitSuccessListener.onSubmitSuccess();
            }
            dismiss();
        });
        Context context = mDataBinding.getRoot().getContext();
        int screenHeight = ScreenUtil.getScreenHeight(context) - ScreenUtil.getStatusBarHeight(context);
        int topHeight = ScreenUtil.dp2px(context, 60);
        int headerHeight = ScreenUtil.dp2px(context, 10 + 10 + 10 + 10)
                + ScreenUtil.sp2px(context, 16)
                + ScreenUtil.sp2px(context, 14)
                + ScreenUtil.sp2px(context, 14);

        int bottomHeight = ScreenUtil.dp2px(context, 40) + ScreenUtil.sp2px(context, 20);
        int minHeight = screenHeight - topHeight - bottomHeight - headerHeight;
        int itemHeight = ScreenUtil.dp2px(context, 140);
        mViewModel.orderList.observe(getViewLifecycleOwner(), list -> {
            if (list != null && list.size() == 1) {
                mDataBinding.orderList.getLayoutParams().height = itemHeight;
            } else {
                mDataBinding.orderList.getLayoutParams().height = Math.min(minHeight, itemHeight * 2);
            }
            mAdapter.setData(list);
        });

        mViewModel.init(taskNo, bean);

    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setOnSubmitSuccessListener(OnSubmitSuccessListener onSubmitSuccessListener) {
        this.onSubmitSuccessListener = onSubmitSuccessListener;
    }

    public interface OnSubmitSuccessListener {
        void onSubmitSuccess();
    }

    /**
     * 弹窗关闭时的回调 包括确定和取消两种情况
     */
    public interface OnDismissListener {
        void onDismiss();
    }
}
