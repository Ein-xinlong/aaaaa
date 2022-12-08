package com.jd.saas.padinventory.create;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.CreateCauseItemDataBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 新建原因适配器
 * @author: ext.anxinlong
 * @date: 2021/6/11
 */
public class InventoryCreateStorageCauseAdapter extends RecyclerView.Adapter<InventoryCreateStorageCauseAdapter.ItemViewHolder> {
    private final List<String> mList = new ArrayList<>();
    private CreateCauseItemDataBinding mDataBinding;
    private ItemViewHolder mViewHolder;

    public void refreshList(List<String> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }
    /**
     * 返回title
     * */
    public String getDictName(int pos) {
        return mList.get(pos);
    }

    @NonNull
    @NotNull
    @Override
    public InventoryCreateStorageCauseAdapter.ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        mViewHolder = new ItemViewHolder(mDataBinding.getRoot());
        mViewHolder.setDataBinding(mDataBinding);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InventoryCreateStorageCauseAdapter.ItemViewHolder holder, int position) {
        String bean = mList.get(position);
        if (bean != null) {
            holder.getDataBinding().setVm(bean);
            holder.getDataBinding().executePendingBindings();
        }

        mDataBinding.item.setOnClickListener(v -> {

            onCheckedChange.checkedChange(position);

        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.inventory_adjustment_create_cause_item;
    }

    /**
     * 子布局DataBinding绑定
     *
     * @author: ext.anxinlong
     * @date: 2021/5/31
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private CreateCauseItemDataBinding mDataBinding;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void setDataBinding(CreateCauseItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public CreateCauseItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    public interface OnCheckedChange {
        void checkedChange(int pos);
    }

    OnCheckedChange onCheckedChange;

    public void setOnCheckedChange(OnCheckedChange onCheckedChange) {
        this.onCheckedChange = onCheckedChange;
    }
}
