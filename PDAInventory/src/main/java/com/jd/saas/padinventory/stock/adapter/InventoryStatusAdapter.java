package com.jd.saas.padinventory.stock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.InventoryStatusItemDataBinding;

import com.jd.saas.padinventory.stock.InventoryStatusBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/16
 */
public class InventoryStatusAdapter extends RecyclerView.Adapter<InventoryStatusAdapter.ItemViewHolder> {

    private final List<InventoryStatusBean> mList = new ArrayList<>();

    // 填充数据，刷新
    public void refreshList(List<InventoryStatusBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InventoryStatusAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InventoryStatusItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        InventoryStatusBean bean = mList.get(position);
        if (null != bean) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCheckedChange.checkedChange(bean);
                }
            });
            holder.getDataBinding().setVm(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.inventory_stock_status_dialog_item;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private InventoryStatusItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void setDataBinding(InventoryStatusItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public InventoryStatusItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    public interface OnCheckedChange {
        void checkedChange(InventoryStatusBean bean);
    }

    OnCheckedChange onCheckedChange;

    public void setOnCheckedChange(OnCheckedChange onCheckedChange) {
        this.onCheckedChange = onCheckedChange;
    }
}
