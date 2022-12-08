package com.jd.saas.padinventory.adjustment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.InventoryAdjustmentItemDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 调整单详情适配器
 *
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
public class InventoryAdjustmentAdapter extends RecyclerView.Adapter<InventoryAdjustmentAdapter.ItemViewHolder> {
    private final List<InventoryAdjustmentItemBean.SkuBoPageExtBean.DataBean> mList = new ArrayList<>();
    private InventoryAdjustmentItemDataBinding mDataBinding;

    public void refreshList(List<InventoryAdjustmentItemBean.SkuBoPageExtBean.DataBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(mDataBinding.getRoot());
        viewHolder.setDataBinding(mDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdjustmentAdapter.ItemViewHolder holder, int position) {
        InventoryAdjustmentItemBean.SkuBoPageExtBean.DataBean inventoryAdjustmentItemBean = mList.get(position);
        if (inventoryAdjustmentItemBean != null) {
            holder.getDataBinding().setInventoryAdjustmentItemVM(inventoryAdjustmentItemBean);
            holder.getDataBinding().executePendingBindings();
        }


    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.inventory_adjustment_item;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 子布局DataBinding绑定
     *
     * @author: ext.anxinlong
     * @date: 2021/5/31
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private InventoryAdjustmentItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(InventoryAdjustmentItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public InventoryAdjustmentItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
