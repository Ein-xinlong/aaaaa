package com.jd.saas.pdacheck.flow.step3.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckDialogReviewSortTypeBinding;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortKeyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortOption;


public class CheckReviewSortTypeDialog extends BottomSheetDialogFragment {
    private OnChangeSelectListener onChangeSelectListener;
    private CheckReviewSortTypeViewModel mViewModel;
    private CheckDialogReviewSortTypeBinding mDataBinding;

    public static CheckReviewSortTypeDialog getInstance(CheckReviewSortKeyType sortKeyType, CheckReviewSortOption sortOption) {
        CheckReviewSortTypeDialog dialog = new CheckReviewSortTypeDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sortKeyType", sortKeyType);
        bundle.putSerializable("sortOption", sortOption);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.check_dialog_review_sort_type, null);
        mDataBinding = CheckDialogReviewSortTypeBinding.bind(view);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(CheckReviewSortTypeViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            CheckReviewSortKeyType sortKeyType = (CheckReviewSortKeyType) bundle.getSerializable("sortKeyType");
            CheckReviewSortOption sortOption = (CheckReviewSortOption) bundle.getSerializable("sortOption");
            mViewModel.changeSelect(sortKeyType, sortOption);
        }
        mDataBinding.setVm(mViewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(true);
            if (dialog instanceof BottomSheetDialog) {
                ((BottomSheetDialog) dialog).getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
        // 关闭按钮
        mDataBinding.ivClose.setOnClickListener(v -> dismiss());
        //确定按钮
        mDataBinding.buttonSure.setOnClickListener(v -> {
            Pair<CheckReviewSortKeyType, CheckReviewSortOption> sortTypeValue = mViewModel.sortType.getValue();
            if (sortTypeValue != null) {
                onChangeSelectListener.onChangeSelect(sortTypeValue.first, sortTypeValue.second);
            }

            dismiss();
        });
        //重置
        mDataBinding.buttonReset.setOnClickListener(v -> {
            onChangeSelectListener.onChangeSelect(null, null);
            dismiss();
        });
    }


    public void setOnChangeSelectListener(OnChangeSelectListener onSureListener) {
        this.onChangeSelectListener = onSureListener;
    }


    public interface OnChangeSelectListener {
        void onChangeSelect(CheckReviewSortKeyType sortKeyType, CheckReviewSortOption sortOption);
    }
}
