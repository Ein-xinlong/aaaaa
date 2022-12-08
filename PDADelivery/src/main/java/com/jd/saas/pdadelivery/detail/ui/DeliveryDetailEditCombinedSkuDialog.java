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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.saas.pdacommon.dialog.SimpleListDialog;
import com.jd.saas.pdacommon.component.UpcCodeDialog;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.EditTextHelper;
import com.jd.saas.pdacommon.utils.ListUtils;
import com.jd.saas.pdadelivery.Inject;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailEditCombinedSkuDialogBinding;
import com.jd.saas.pdadelivery.detail.DeliveryDetailViewModel;
import com.jd.saas.pdadelivery.detail.adapter.DeliveryBoxProductAdapter;
import com.jd.saas.pdadelivery.detail.bean.DeliveryBoxProductBean;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryStockTypeBean;
import com.jd.saas.pdadelivery.net.enums.SaleModeEnum;
import com.jd.saas.pdadelivery.net.enums.SkuNatureEnum;
import com.jd.saas.pdadelivery.util.Formatter;
import com.jd.saas.pdadelivery.util.LocTypeUtils;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 编辑普通商品的收货信息的弹窗
 */
public class DeliveryDetailEditCombinedSkuDialog extends BottomSheetDialogFragment {
    public static String GOOD_NAME = "好品";
    public static String BAD_NAME = "坏品";
    private DeliveryDetailViewModel mViewModel;
    private DeliveryDetailEditCombinedSkuDialogBinding mDataBinding;
    private DeliveryBoxProductAdapter adapter;
    private List<DeliveryBoxProductBean> boxProductBeans;
    private OnEnsureClickListener onEnsureClickListener;
    private OnDismissListener onDismissListener;
    private BigDecimal limit;
    private BigDecimal inputQty;
    private String locName;

    public static DeliveryDetailEditCombinedSkuDialog getInstance() {
        return new DeliveryDetailEditCombinedSkuDialog();
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
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delivery_detail_edit_combined_sku_dialog, null);
        mDataBinding = DeliveryDetailEditCombinedSkuDialogBinding.bind(dialogView);
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
        Gson gson = new Gson();
        boxProductBeans = gson.fromJson(gson.toJson(bean.getBoxProducts()), new TypeToken<List<DeliveryBoxProductBean>>() {
        }.getType());
        if (boxProductBeans == null || boxProductBeans.isEmpty()) {
            boxProductBeans = new ArrayList<>();
        }
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DeliveryBoxProductAdapter(boxProductBeans);
        mDataBinding.recyclerview.setAdapter(adapter);
        //组合商品收货数量限制是随时变化的
        //组合商品可收数量为当前商品的所有（子品的可收数量/组合系数）的最小值
        //子品可收数量= 单品最大收货数量 - 单品实收数量 -  单品本次收货数量 - （单品组合收货数量-本组合商品中该子品本次收货数量）
        limit = boxProductBeans.get(0).getUpperLimitReceived();
        for (DeliveryBoxProductBean boxProductBean : boxProductBeans) {
            ArrayList<DeliverySkuBean> value = mViewModel.skuList.getValue();
            //子商品的单个商品收货信息
            DeliverySkuBean skuBean = null;
            if (value != null) {
                for (int i = 0; i < value.size(); i++) {
                    if (value.get(i).getSkuId().equals(boxProductBean.getSkuId())) {
                        skuBean = value.get(i);
                        break;
                    }
                }
            }
            if (skuBean != null) {
                BigDecimal childLimit = boxProductBean.getUpperLimitReceived()
                        .subtract(boxProductBean.getActualQty())
                        .subtract(skuBean.getInputQty())
                        .subtract(skuBean.getCombinedInputQty())
                        .add(bean.getInputQty().multiply(boxProductBean.getBoxNum()))
                        .divide(boxProductBean.getBoxNum(), RoundingMode.DOWN);
                //设置本次的收货数量限制
                boxProductBean.setLimit(childLimit);
                limit = limit.min(childLimit);
            }
        }

        Logger.d("delivery", "limit is " + limit.toPlainString());
        inputQty = bean.getInputQty();
        locName = bean.getLocName();
        final boolean isInt = bean.getSaleMode() == SaleModeEnum.PIECE.getValue();
        if (isInt) {
            limit = limit.setScale(0, BigDecimal.ROUND_DOWN);
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
//                    mDataBinding.etActualQty.setText("0");
                    inputQty = BigDecimal.ZERO;
                    mDataBinding.setInputQtyStr(Formatter.format(inputQty));
                    mDataBinding.executePendingBindings();
                    adapter.changeInputQty(inputQty);
                    return;
                }
                if (inputQty.compareTo(limit) > 0) {
                    MyToast.show(getString(R.string.delivery_input_qty_more_than_limit), false);
//                    if (isInt) {
//                        mDataBinding.etActualQty.setText(String.valueOf(limit.toBigInteger()));
//                    } else {
//                        mDataBinding.etActualQty.setText(Formatter.format(limit));
//                    }
                    inputQty = limit;
                    mDataBinding.setInputQtyStr(Formatter.format(inputQty));
                    mDataBinding.executePendingBindings();
                }

                adapter.changeInputQty(inputQty);
            }
        });
        mDataBinding.setOnCloseClick(v -> dismiss());
        mDataBinding.setOnSelectStockClick(v -> {
            SimpleListDialog<String> simpleListDialog = new SimpleListDialog<>(requireContext(), R.string.delivery_detail_choose_storage);
            ArrayList<String> nameList = new ArrayList<>();
            nameList.add(GOOD_NAME);
            nameList.add(BAD_NAME);
            simpleListDialog.setOptions(ListUtils.map(nameList, from -> {
                SimpleListDialog.Option<String> option = new SimpleListDialog.Option<>();
                option.setName(from);
                option.setSelected(locName.equals(from));
                option.setData(from);
                return option;
            }));
            simpleListDialog.setOnItemSelectListener(str -> {
                locName = str;
                bind(bean);
            });
            simpleListDialog.show();
        });
        mDataBinding.setOnUpcMoreClick(v -> {
            String[] upcCodes = bean.getUpcCodes();
            if (upcCodes != null) {
                UpcCodeDialog.open(v, upcCodes);
            }
        });

        mDataBinding.setOnPlusClick(v -> {
            if (checkNumAndChange(inputQty.add(BigDecimal.ONE), limit)) {
                adapter.changeInputQty(inputQty);
                bind(bean);
            }

        });
        mDataBinding.setOnMinusClick(v -> {
            if (checkNumAndChange(inputQty.subtract(BigDecimal.ONE), limit)) {
                adapter.changeInputQty(inputQty);
                bind(bean);
            }
        });
        mDataBinding.setOnEnsureClick(v -> {
            if (locName == null) {
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
                        //实际修改商品对象的属性

                        ArrayList<DeliverySkuBean> skuListValue = mViewModel.skuList.getValue();

                        if (skuListValue != null && !skuListValue.isEmpty()) {
                            for (DeliveryBoxProductBean boxProductBean : boxProductBeans) {
                                for (DeliverySkuBean skuBean : skuListValue) {
                                    if (boxProductBean.getSkuId().equals(skuBean.getSkuId())) {
                                        //更新单个商品中的组合收货数量信息
                                        skuBean.setCombinedInputQty(
                                                skuBean
                                                        .getCombinedInputQty()
                                                        //修改前 该组合商品的组合收货数量
                                                        .subtract(bean.getInputQty().multiply(boxProductBean.getBoxNum()))
                                                        //修改后 该组合商品的组合收货数量
                                                        .add(inputQty.multiply(boxProductBean.getBoxNum())));
                                        //更新组合收货信息的子商品的库位信息
                                        String lotType = LocTypeUtils.getLotTypeBySkuTypeAndSkuNature(
                                                skuBean.getSkuType(),
                                                GOOD_NAME.equals(locName) ?
                                                        SkuNatureEnum.GOOD.getValue()
                                                        : SkuNatureEnum.BAD.getValue());
                                        DeliveryStockTypeBean stockType = mViewModel.getStockTypeByLotType(lotType);
                                        if (stockType != null) {
                                            boxProductBean.setLocId(stockType.getLocId());
                                            boxProductBean.setLocName(stockType.getLocName());
                                            boxProductBean.setLocType(stockType.getLocType());
                                        }
                                    }
                                }
                            }
                        }
                        bean.setLocName(locName);
                        bean.setInputQty(inputQty);
                        bean.setBoxProducts(boxProductBeans);
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
