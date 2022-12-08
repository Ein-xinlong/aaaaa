package com.jd.saas.padinventory.create;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.CreateStorageItemDataBinding;
import com.jd.saas.pdacommon.search.InventoryCreateDialogBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 库位适配器
 * @author: ext.anxinlong
 * @date: 2021/6/11
 */
public class InventoryCreateStorageAdapter extends RecyclerView.Adapter<InventoryCreateStorageAdapter.ItemViewHolder> {

    private final List<InventoryCreateDialogBean> mList =new ArrayList<>();

    public void refreshList(List<InventoryCreateDialogBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public InventoryCreateStorageAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CreateStorageItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        InventoryCreateDialogBean bean = mList.get(position);
        if (bean != null) {
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
        return R.layout.inventory_adjustment_create_storage_item;
    }

  static class ItemViewHolder extends RecyclerView.ViewHolder {

        private CreateStorageItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void setDataBinding(CreateStorageItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public CreateStorageItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    public interface OnCheckedChange {
        void checkedChange(InventoryCreateDialogBean bean);
    }

    private OnCheckedChange onCheckedChange;
    public void setOnCheckedChange(OnCheckedChange onCheckedChange) {
        this.onCheckedChange = onCheckedChange;
    }
}
