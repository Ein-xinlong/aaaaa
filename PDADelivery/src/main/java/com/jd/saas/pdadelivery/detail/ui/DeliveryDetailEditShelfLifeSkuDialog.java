package com.jd.saas.pdadelivery.detail.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.saas.pdacommon.dialog.SimpleCancelDialog;
import com.jd.saas.pdacommon.component.UpcCodeDialog;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdadelivery.Inject;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailEditShelfLifeSkuDialogBinding;
import com.jd.saas.pdadelivery.detail.DeliveryDetailViewModel;
import com.jd.saas.pdadelivery.detail.adapter.DeliveryShelfLiftInfoAdapter;
import com.jd.saas.pdadelivery.detail.bean.DeliveryShelfLifeInfoBean;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.util.Formatter;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 编辑效期商品的收货信息的弹窗
 */
public class DeliveryDetailEditShelfLifeSkuDialog extends BottomSheetDialogFragment {

    private DeliveryDetailEditShelfLifeSkuDialogBinding binding;
    private OnEnsureClickListener onEnsureClickListener;
    private DeliveryDetailViewModel mViewModel;
    private List<DeliveryShelfLifeInfoBean> shelfLifeInfoBeans;
    private DeliveryShelfLiftInfoAdapter adapter;

    private OnDismissListener onDismissListener;

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

    public static DeliveryDetailEditShelfLifeSkuDialog getInstance() {
        return new DeliveryDetailEditShelfLifeSkuDialog();
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setOnEnsureClickListener(OnEnsureClickListener onEnsureClickListener) {
        this.onEnsureClickListener = onEnsureClickListener;
    }

    public void updateExpiryDateGoodsStatus(Pair<Long, Integer> pair) {
        if (shelfLifeInfoBeans != null) {
            for (DeliveryShelfLifeInfoBean shelfLifeInfoBean : shelfLifeInfoBeans) {
                if (shelfLifeInfoBean.getLocalId() == pair.first) {
                    shelfLifeInfoBean.setState(pair.second);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delivery_detail_edit_shelf_life_sku_dialog, null);
        binding = DeliveryDetailEditShelfLifeSkuDialogBinding.bind(dialogView);
        Context context = binding.getRoot().getContext();
        int screenHeight = ScreenUtil.getScreenHeight(context) - ScreenUtil.getStatusBarHeight(context);
        int topHeight = ScreenUtil.dp2px(context, 18 + 24) + ScreenUtil.sp2px(context, 18);
        int bottomHeight = ScreenUtil.dp2px(context, 42) + ScreenUtil.sp2px(context, 20);
        int headerHeight = ScreenUtil.dp2px(context, 24 + 14) + ScreenUtil.sp2px(context, 14);
        int remainedHeight = screenHeight - topHeight - bottomHeight;
        int minHeight = Math.max(remainedHeight, headerHeight);
        int fixHeight = ScreenUtil.dp2px(context, 382);
        binding.content.getLayoutParams().height = Math.min(minHeight, fixHeight);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setOnShowListener(dialog -> {
            bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
            binding.recyclerview.postDelayed(() -> {
                binding.recyclerview.requestFocus();
                SoftInputUtil.showSoftInput(binding.getRoot().getContext());
            }, 50);
        });
        binding.iconQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleCancelDialog.Builder(context,R.string.delivery_detail_cancel_hint).build().show();
            }
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
        mViewModel = new ViewModelProvider(requireActivity(), Inject.injectViewModelFactory()).get(DeliveryDetailViewModel.class);
        final DeliverySkuBean bean = mViewModel.editSku.getValue();
        initView(bean);
    }

    private DeliveryShelfLifeInfoBean createDeliveryShelfLifeInfoBean(DeliverySkuBean bean) {
        DeliveryShelfLifeInfoBean deliveryShelfLifeInfoBean = new DeliveryShelfLifeInfoBean();
        deliveryShelfLifeInfoBean.setLocType(bean.getLocType());
        deliveryShelfLifeInfoBean.setLocName(bean.getLocName());
        deliveryShelfLifeInfoBean.setLocId(bean.getLocId());
        return deliveryShelfLifeInfoBean;
    }

    private void initView(final DeliverySkuBean bean) {
        if (bean == null) {
            return;
        }
        Gson gson = new Gson();
        shelfLifeInfoBeans = gson.fromJson(gson.toJson(bean.getShelfLifeInfoList()), new TypeToken<List<DeliveryShelfLifeInfoBean>>() {
        }.getType());
        if (shelfLifeInfoBeans == null || shelfLifeInfoBeans.isEmpty()) {
            shelfLifeInfoBeans = new ArrayList<>();
            shelfLifeInfoBeans.add(createDeliveryShelfLifeInfoBean(bean));
        }
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DeliveryShelfLiftInfoAdapter(getChildFragmentManager(), bean, () -> createDeliveryShelfLifeInfoBean(bean), shelfLifeInfoBeans);
        adapter.setOnInputQtyChangeListener((newNum, shelfLifeInfoBean) -> bind2View(bean));
        adapter.setProductDateChangeListener(shelfLifeInfoBean -> mViewModel.queryExpiryDateGoodsStatus(bean.getSkuId(), shelfLifeInfoBean));
        adapter.setOnAddItemListener(() -> binding.recyclerview.post(() -> binding.recyclerview.scrollBy(0, ScreenUtil.dp2px(binding.recyclerview.getContext(), 60))));
        binding.recyclerview.setAdapter(adapter);

        binding.setOnEnsureClick(v -> {
            if (this.shelfLifeInfoBeans == null || this.shelfLifeInfoBeans.isEmpty()) {
                MyToast.show(getString(R.string.delivery_input_loc_tips), false);
                return;
            }
            BigDecimal sum = BigDecimal.ZERO;
            for (DeliveryShelfLifeInfoBean shelfLifeInfoBean : shelfLifeInfoBeans) {
                if (shelfLifeInfoBean.getCreateDate() == null) {
                    MyToast.show(getString(R.string.delivery_input_create_date_empty_tips), false);
                    return;
                }
                if (shelfLifeInfoBean.getLocId() == null) {
                    MyToast.show(getString(R.string.delivery_input_loc_tips), false);
                    return;
                }
                BigDecimal inputQty = shelfLifeInfoBean.getInputQty();
                if (inputQty != null) {
                    if (inputQty.compareTo(BigDecimal.ZERO) <= 0) {
                        MyToast.show(getString(R.string.delivery_input_qty_less_than_or_equal_zero), false);
                        return;
                    }
                    sum = sum.add(inputQty);
                }
            }
            BigDecimal limit = bean.getUpperLimitReceived().subtract(bean.getActualQty());
            if (sum.compareTo(limit) > 0) {
                MyToast.show(getString(R.string.delivery_input_qty_more_than_limit), false);
                return;
            }
            bean.setShelfLifeInfoList(shelfLifeInfoBeans);
            bean.setInputQty(sum);
            if (onEnsureClickListener != null) {
                onEnsureClickListener.onEnsure(bean);
            }
            dismiss();
        });
        binding.setOnCloseClick(v -> dismiss());

        binding.setOnUpcMoreClick(v -> {
            String[] upcCodes = bean.getUpcCodes();
            if (upcCodes != null) {
                UpcCodeDialog.open(v, upcCodes);
            }
        });

        bind2View(bean);
    }

    private void bind2View(final DeliverySkuBean bean) {
        binding.setBean(bean);
        binding.setSumStr(Formatter.format(adapter.getSum()));
        binding.executePendingBindings();
    }
}
