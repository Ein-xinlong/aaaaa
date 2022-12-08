package com.jd.saas.pdadelivery.detail.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailCombinedSkuDialogAdapterBinding;
import com.jd.saas.pdadelivery.detail.bean.DeliveryBoxProductBean;

import java.math.BigDecimal;
import java.util.List;

public class DeliveryBoxProductAdapter extends RecyclerView.Adapter<DeliveryBoxProductAdapter.ItemViewHolder> {
    private final List<DeliveryBoxProductBean> mList;

    public DeliveryBoxProductAdapter(List<DeliveryBoxProductBean> mList) {
        this.mList = mList;
    }

    public void changeInputQty(BigDecimal inputQty) {
        for (DeliveryBoxProductBean boxProductBean : mList) {
            boxProductBean.setInputQty(inputQty.multiply(boxProductBean.getBoxNum()));
        }
        notifyDataSetChanged();
    }

    public void refreshList(List<DeliveryBoxProductBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryDetailCombinedSkuDialogAdapterBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new ItemViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DeliveryBoxProductBean bean = mList.get(position);
        if (bean != null) {
            holder.getDataBinding().setBean(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.delivery_detail_combined_sku_dialog_adapter;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final DeliveryDetailCombinedSkuDialogAdapterBinding mDataBinding;

        public ItemViewHolder(@NonNull DeliveryDetailCombinedSkuDialogAdapterBinding mDataBinding) {
            super(mDataBinding.getRoot());
            this.mDataBinding = mDataBinding;
        }

        public DeliveryDetailCombinedSkuDialogAdapterBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
