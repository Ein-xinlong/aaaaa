package com.jd.saas.padinventory.stock.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.InventoryStockItemOddNumberDataBinding;
import com.jd.saas.padinventory.stock.InventoryStockRepostBean;

/**
 * 库存调整列表
 *
 * @author majiheng
 */
public class InventoryStockAdapter extends PagedListAdapter<InventoryStockRepostBean.ItemListBean,InventoryStockAdapter.ItemViewHolder> {

    private InventoryStockItemOddNumberDataBinding mDataBinding;

    public InventoryStockAdapter(){
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public InventoryStockAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),viewType,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(mDataBinding.getRoot());
        viewHolder.setDataBinding(mDataBinding);
        return viewHolder;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull InventoryStockAdapter.ItemViewHolder holder, int position) {
        InventoryStockRepostBean.ItemListBean bean = getItem(position);

        if(null != bean) {
            holder.getDataBinding().setVm(bean);
            holder.getDataBinding().executePendingBindings();
        }

    }
    private static final DiffUtil.ItemCallback<InventoryStockRepostBean.ItemListBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<InventoryStockRepostBean.ItemListBean>() {

        @Override
        public boolean areItemsTheSame(@NonNull InventoryStockRepostBean.ItemListBean oldItem, @NonNull InventoryStockRepostBean.ItemListBean newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull InventoryStockRepostBean.ItemListBean oldItem, @NonNull InventoryStockRepostBean.ItemListBean newItem) {
            return oldItem.equals(newItem);
        }
    };


    @Override
    public int getItemViewType(int position) {
        return R.layout.inventory_stock_item_odd_number;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        private InventoryStockItemOddNumberDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void setDataBinding(InventoryStockItemOddNumberDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public InventoryStockItemOddNumberDataBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
