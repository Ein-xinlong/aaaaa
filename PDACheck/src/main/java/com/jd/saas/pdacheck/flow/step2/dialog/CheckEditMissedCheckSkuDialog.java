package com.jd.saas.pdacheck.flow.step2.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckDialogEditMissedCheckSkuBinding;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuSaleModeEnum;
import com.jd.saas.pdacheck.util.CheckFormatter;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.EditTextHelper;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * 编辑商品的盘点信息的弹窗
 * 包括库位和数量信息
 *
 * @author gouhetong
 */
public class CheckEditMissedCheckSkuDialog extends BottomSheetDialogFragment {
    public static CheckEditMissedCheckSkuDialog getInstance(CheckMissedSkuBean skuBean) {
        CheckEditMissedCheckSkuDialog dialog = new CheckEditMissedCheckSkuDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean", skuBean);
        dialog.setArguments(bundle);
        return dialog;
    }

    private CheckDialogEditMissedCheckSkuBinding mDataBinding;
    private OnEnsureClickListener onEnsureClickListener;
    private OnDismissListener onDismissListener;
    private BigDecimal limit;
    private BigDecimal inputQty;


    /**
     * 点击确定时的回调
     */
    public interface OnEnsureClickListener {
        void onEnsure(CheckMissedSkuBean bean);
    }

    /**
     * 弹窗关闭时的回调 包括确定和取消两种情况
     */
    public interface OnDismissListener {
        void onDismiss();
    }


    public void setOnEnsureClickListener(OnEnsureClickListener onEnsureClickListener) {
        this.onEnsureClickListener = onEnsureClickListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.check_dialog_edit_missed_check_sku, null);
        mDataBinding = CheckDialogEditMissedCheckSkuBinding.bind(view);
        mDataBinding.setLifecycleOwner(this);
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
        Bundle arguments = getArguments();
        if (arguments == null) {
            dismiss();
            return;
        }
        CheckMissedSkuBean bean = arguments.getParcelable("bean");
        if (bean == null) {
            dismiss();
            return;
        }
        initView(bean);
        mDataBinding.getRoot().postDelayed(() -> {
            mDataBinding.etActualQty.requestFocus();
            SoftInputUtil.showSoftInput(mDataBinding.getRoot().getContext());
        }, 50);
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    /**
     * 更新除基本信息外其他信息的方法
     *
     * @param bean 编辑后的商品
     */
    private void bind(final CheckMissedSkuBean bean) {
        mDataBinding.setBean(bean);
        mDataBinding.setInputQtyStr(CheckFormatter.format(inputQty));
        mDataBinding.executePendingBindings();
    }

    /**
     * 初始化方法 只会调用一次
     *
     * @param bean 要编辑的商品
     */
    private void initView(final CheckMissedSkuBean bean) {
        if (bean == null) {
            return;
        }
        limit = BigDecimal.valueOf(Integer.MAX_VALUE);
        BigDecimal skuInput = bean.getInputQty();
        if (skuInput == null) {
            skuInput = BigDecimal.ZERO;
        }
        inputQty = skuInput.compareTo(BigDecimal.ZERO) > 0 ? skuInput : BigDecimal.ZERO;
        final boolean isInt = bean.getSaleMode() == CheckMissedSkuSaleModeEnum.PIECE.getValue();
        if (isInt) {
            mDataBinding.etActualQty.setInputType(InputType.TYPE_CLASS_NUMBER);
            mDataBinding.addImage.setVisibility(View.VISIBLE);
            mDataBinding.ivReduce.setVisibility(View.VISIBLE);
        } else {
            EditTextHelper.INSTANCE.limitDecimalPlaces(3, mDataBinding.etActualQty);
            mDataBinding.addImage.setVisibility(View.GONE);
            mDataBinding.ivReduce.setVisibility(View.GONE);
        }

        mDataBinding.etActualQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s) || ".".equals(s.toString())) {
                    return;
                }
                inputQty = new BigDecimal(s.toString());
                if (inputQty.compareTo(BigDecimal.ZERO) < 0) {
                    MyToast.show(getString(R.string.check_missed_input_qty_less_than_zero), false);
                    mDataBinding.etActualQty.setText("0");
                    return;
                }
                if (inputQty.compareTo(limit) > 0) {
                    MyToast.show(getString(R.string.check_missed_input_qty_more_than_limit), false);
                    if (isInt) {
                        mDataBinding.etActualQty.setText(String.valueOf(limit.toBigInteger()));
                    } else {
                        mDataBinding.etActualQty.setText(CheckFormatter.format(limit));
                    }
                }
            }
        });
        mDataBinding.setOnCloseClick(v -> dismiss());

        mDataBinding.setOnPlusClick(v -> {
            if (checkNumAndChange(inputQty.add(BigDecimal.ONE), limit)) {
                bind(bean);
            }

        });
        mDataBinding.setOnMinusClick(v -> {
            if (checkNumAndChange(inputQty.subtract(BigDecimal.ONE), limit)) {
                bind(bean);
            }
        });
        mDataBinding.setOnEnsureClick(v -> {
            Editable editable = mDataBinding.etActualQty.getText();
            if (editable == null || TextUtils.isEmpty(editable)) {
                MyToast.show(getString(R.string.check_missed_input_qty_tips), false);
                return;
            }
            try {
                String s = editable.toString();
                BigDecimal value = new BigDecimal(s);
                if (checkNumAndChange(value, limit)) {
                    if (onEnsureClickListener != null) {
                        bean.setInputQty(inputQty);
                        onEnsureClickListener.onEnsure(bean);
                    }
                    dismiss();
                }
            } catch (NumberFormatException ignored) {

            }
        });
        bind(bean);
    }

    private boolean checkNumAndChange(BigDecimal newNum, BigDecimal limit) {
        if (newNum.compareTo(limit) > 0) {
            MyToast.show(getString(R.string.check_missed_input_qty_more_than_limit), false);
            return false;
        }
        if (newNum.compareTo(BigDecimal.ZERO) < 0) {
            MyToast.show(getString(R.string.check_missed_input_qty_less_than_zero), false);
            return false;
        }
        inputQty = newNum;
        return true;
    }
}
