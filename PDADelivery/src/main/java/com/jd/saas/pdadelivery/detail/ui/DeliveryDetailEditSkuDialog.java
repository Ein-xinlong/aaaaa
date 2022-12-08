package com.jd.saas.pdadelivery.detail.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jd.saas.pdacommon.component.UpcCodeDialog;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.EditTextHelper;
import com.jd.saas.pdadelivery.Inject;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailEditSkuDialogBinding;
import com.jd.saas.pdadelivery.detail.DeliveryDetailViewModel;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.net.enums.SaleModeEnum;
import com.jd.saas.pdadelivery.util.Formatter;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * 编辑普通商品的收货信息的弹窗
 */
public class DeliveryDetailEditSkuDialog extends BottomSheetDialogFragment {
    private DeliveryDetailEditSkuDialogBinding mDataBinding;
    private OnEnsureClickListener onEnsureClickListener;
    private OnDismissListener onDismissListener;
    private BigDecimal limit;
    private BigDecimal inputQty;
    private String locId;
    private String locName;
    private String locType;

    public static DeliveryDetailEditSkuDialog getInstance() {
        return new DeliveryDetailEditSkuDialog();
    }

    /**
     * 点击确定时的回调
     */
    public interface OnEnsureClickListener {
        void onEnsure(DeliverySkuBean bean);
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

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delivery_detail_edit_sku_dialog, null);
        mDataBinding = DeliveryDetailEditSkuDialogBinding.bind(dialogView);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setOnShowListener(dialog -> {
            bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
            mDataBinding.getRoot().postDelayed(() -> {
                mDataBinding.etActualQty.requestFocus();
                SoftInputUtil.showSoftInput(mDataBinding.getRoot().getContext());
            }, 50);
        });
        return bottomSheetDialog;
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DeliveryDetailViewModel mViewModel;
        mViewModel = new ViewModelProvider(requireActivity(), Inject.injectViewModelFactory()).get(DeliveryDetailViewModel.class);
        final DeliverySkuBean bean = mViewModel.editSku.getValue();
        initView(bean);
    }

    /**
     * 更新除基本信息外其他信息的方法
     *
     * @param bean 编辑后的商品
     */
    private void bind(final DeliverySkuBean bean) {
        mDataBinding.setBean(bean);
        mDataBinding.setLocName(locName);
        mDataBinding.setInputQtyStr(Formatter.format(inputQty));
        mDataBinding.executePendingBindings();
    }

    /**
     * 初始化方法 只会调用一次
     *
     * @param bean 要编辑的商品
     */
    private void initView(final DeliverySkuBean bean) {
        if (bean == null) {
            return;
        }
        limit = bean.getUpperLimitReceived().subtract(bean.getActualQty()).subtract(bean.getCombinedInputQty());
        inputQty = bean.getInputQty().compareTo(BigDecimal.ZERO) > 0 ? bean.getInputQty() : limit;
        locId = bean.getLocId();
        locName = bean.getLocName();
        locType = bean.getLocType();
        final boolean isInt = bean.getSaleMode() == SaleModeEnum.PIECE.getValue();
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
                    MyToast.show(getString(R.string.delivery_input_qty_less_than_zero), false);
                    mDataBinding.etActualQty.setText("0");
                    return;
                }
                if (inputQty.compareTo(limit) > 0) {
                    MyToast.show(getString(R.string.delivery_input_qty_more_than_limit), false);
                    if (isInt) {
                        mDataBinding.etActualQty.setText(String.valueOf(limit.toBigInteger()));
                    } else {
                        mDataBinding.etActualQty.setText(Formatter.format(limit));
                    }
                }
            }
        });
        mDataBinding.setOnCloseClick(v -> dismiss());
        mDataBinding.setOnSelectStockClick(v -> {
            DeliverySelectStockTypeDialog selectStockTypeDialog = new DeliverySelectStockTypeDialog();
            selectStockTypeDialog.setLocId(locId);
            selectStockTypeDialog.setOnSelectStockTypeListener(stockTypeBean -> {
                locId = stockTypeBean.getLocId();
                locName = stockTypeBean.getLocName();
                locType = stockTypeBean.getLocType();
                bind(bean);
            });
            selectStockTypeDialog.show(getChildFragmentManager(), "SelectStockType");
        });
        mDataBinding.setOnUpcMoreClick(v -> {
            String[] upcCodes = bean.getUpcCodes();
            if (upcCodes != null) {
                UpcCodeDialog.open(v, upcCodes);
            }
        });

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
            if (locId == null) {
                MyToast.show(getString(R.string.delivery_input_loc_tips), false);
                return;
            }
            Editable editable = mDataBinding.etActualQty.getText();
            if (editable == null || TextUtils.isEmpty(editable)) {
                MyToast.show(getString(R.string.delivery_input_qty_less_than_zero), false);
                return;
            }
            try {
                String s = editable.toString();
                BigDecimal value = new BigDecimal(s);
                if (checkNumAndChange(value, limit)) {
                    if (onEnsureClickListener != null) {
                        bean.setLocName(locName);
                        bean.setLocId(locId);
                        bean.setLocType(locType);
                        bean.setInputQty(inputQty);
                        onEnsureClickListener.onEnsure(bean);
                    }
                    dismiss();
                }
            } catch (NumberFormatException e) {

            }
        });
        bind(bean);
    }

    private boolean checkNumAndChange(BigDecimal newNum, BigDecimal limit) {
        if (newNum.compareTo(limit) > 0) {
            MyToast.show(getString(R.string.delivery_input_qty_more_than_limit), false);
            return false;
        }
        if (newNum.compareTo(BigDecimal.ZERO) < 0) {
            MyToast.show(getString(R.string.delivery_input_qty_less_than_zero), false);
            return false;
        }
        inputQty = newNum;
        return true;
    }
}
