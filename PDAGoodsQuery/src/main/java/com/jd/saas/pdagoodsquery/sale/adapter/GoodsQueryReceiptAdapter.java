package com.jd.saas.pdagoodsquery.sale.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jd.saas.pdagoodsquery.databinding.GoodsQueryReceiptDialogItemBinding;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQueryReceipt;
import java.util.List;

/**
单据详情adapter
 */
public class GoodsQueryReceiptAdapter extends RecyclerView.Adapter<GoodsQueryReceiptAdapter.ItemViewHolder>{

    private final List<GoodsQueryReceipt> receiptList;

    public GoodsQueryReceiptAdapter(List<GoodsQueryReceipt> receiptList) {
        this.receiptList = receiptList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GoodsQueryReceiptDialogItemBinding binding = GoodsQueryReceiptDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        ItemViewHolder holder = new ItemViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.mBinding.setBean(receiptList.get(position));
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private final GoodsQueryReceiptDialogItemBinding mBinding;
        public ItemViewHolder(@NonNull GoodsQueryReceiptDialogItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}

