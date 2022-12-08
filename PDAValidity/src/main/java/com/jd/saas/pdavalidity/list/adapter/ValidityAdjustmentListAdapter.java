package com.jd.saas.pdavalidity.list.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdavalidity.R;
import com.jd.saas.pdavalidity.ValidityRouterPath;
import com.jd.saas.pdavalidity.databinding.ValidityAdjustmentListItemDataBinding;

/**
 * 效期详情列表adapter
 * @author: ext.anxinlong
 * @date: 2021/6/2
 */
public class ValidityAdjustmentListAdapter extends PagedListAdapter<SearchGoodBean, ValidityAdjustmentListAdapter.ItemViewHolder> {

    public ValidityAdjustmentListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ValidityAdjustmentListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ValidityAdjustmentListItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ValidityAdjustmentListAdapter.ItemViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        SearchGoodBean bean = getItem(position);
        if (bean != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",bean);
                    RouterClient.getInstance().forward(context, ValidityRouterPath.HOST_PATH_VALIDITY_ADJUST_DETAIL,bundle);
                }
            });
            if(bean.getPeriodState() == 1) {
                // 正常
                holder.getDataBinding().image.setImageResource(R.drawable.validity_adjustment_list_item_normal);
            }else if(bean.getPeriodState() == 2) {
                // 提示
                holder.getDataBinding().image.setImageResource(R.drawable.validity_adjustment_list_item_tip);
            }else if(bean.getPeriodState() == 3) {
                // 预警
                holder.getDataBinding().image.setImageResource(R.drawable.validity_adjustment_list_item_warning);
            }else if(bean.getPeriodState() == 4) {
                // 失效
                holder.getDataBinding().image.setImageResource(R.drawable.validity_adjustment_list_item_invalidation);
            }else {
                holder.getDataBinding().image.setImageResource(0);
            }
            holder.getDataBinding().setVm(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.validity_adjustment_list_item;
    }

    /**
     * 子布局DataBinding绑定
     *
     * @author: ext.anxinlong
     * @date: 2021/5/31
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ValidityAdjustmentListItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(ValidityAdjustmentListItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public ValidityAdjustmentListItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    private static final DiffUtil.ItemCallback<SearchGoodBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<SearchGoodBean>() {

        @Override
        public boolean areItemsTheSame(@NonNull SearchGoodBean oldItem, @NonNull SearchGoodBean newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull SearchGoodBean oldItem, @NonNull SearchGoodBean newItem) {
            return oldItem.equals(newItem);
        }
    };
}
