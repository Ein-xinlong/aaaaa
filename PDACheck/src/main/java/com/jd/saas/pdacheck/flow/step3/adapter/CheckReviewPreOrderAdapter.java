package com.jd.saas.pdacheck.flow.step3.adapter;

import android.annotation.SuppressLint;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckItemReviewPreCheckOrderBinding;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuSaleModeEnum;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderBean;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.EditTextHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckReviewPreOrderAdapter extends RecyclerView.Adapter<CheckReviewPreOrderAdapter.CheckReviewPreCheckOrderViewHolder> {
    private final List<CheckReviewPreOrderBean> orderBeans = new ArrayList<>();
    private final int saleMode;

    public CheckReviewPreOrderAdapter(int saleMode) {
        this.saleMode = saleMode;
    }


    @NonNull
    @Override
    public CheckReviewPreCheckOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckItemReviewPreCheckOrderBinding dataBinding = CheckItemReviewPreCheckOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CheckReviewPreCheckOrderViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckReviewPreCheckOrderViewHolder holder, int position) {
        holder.bind(orderBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return orderBeans.size();
    }

    public List<CheckReviewPreOrderBean> getOrderBeans() {
        return orderBeans;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<CheckReviewPreOrderBean> list) {
        orderBeans.clear();
        orderBeans.addAll(list);
        notifyDataSetChanged();
    }

    private void onItemInputQtyChange(boolean skipNotify, BigDecimal newNum, CheckReviewPreOrderBean bean) {
        if (newNum.compareTo(BigDecimal.ZERO) < 0) {
            MyToast.show(R.string.check_missed_input_qty_less_than_zero, false);
            return;
        }
        bean.setInputQty(newNum);
        if (!skipNotify) {
            int index = orderBeans.indexOf(bean);
            if (index >= 0) {
                notifyItemChanged(index);
            }
        }
    }

    public class CheckReviewPreCheckOrderViewHolder extends RecyclerView.ViewHolder {
        private final CheckItemReviewPreCheckOrderBinding mBinding;

        public CheckReviewPreCheckOrderViewHolder(CheckItemReviewPreCheckOrderBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            if (saleMode == CheckMissedSkuSaleModeEnum.PIECE.getValue()) {
                mBinding.etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                mBinding.addImage.setVisibility(View.VISIBLE);
                mBinding.ivMinus.setVisibility(View.VISIBLE);
            } else {
                EditTextHelper.INSTANCE.limitDecimalPlaces(3, mBinding.etInput);
                mBinding.addImage.setVisibility(View.GONE);
                mBinding.ivMinus.setVisibility(View.GONE);
            }
        }

        public void bind(CheckReviewPreOrderBean bean) {
            mBinding.setBean(bean);
            mBinding.setOnPlusClick(v -> onItemInputQtyChange(false, bean.getInputQty().add(BigDecimal.ONE), bean));
            mBinding.setOnMinusClick(v -> onItemInputQtyChange(false, bean.getInputQty().subtract(BigDecimal.ONE), bean));
            mBinding.setAfterTextChange(s -> {
                if (!mBinding.etInput.hasFocus()) {
                    return;
                }
                if (TextUtils.isEmpty(s) || ".".equals(s.toString())) {
                    return;
                }
                onItemInputQtyChange(true, new BigDecimal(s.toString()), bean);
            });
            mBinding.executePendingBindings();
        }

    }

}
