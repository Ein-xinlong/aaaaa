package com.jd.saas.pdacheck.flow.step4.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckItemProfit2DataBinding;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitListItemBean;

/**
 * 损益单右侧列表adapter
 *
 * @author majiheng
 */
public class CheckProfitRightListAdapter extends PagedListAdapter<CheckProfitListItemBean, CheckProfitRightListAdapter.ItemViewHolder> {

    public CheckProfitRightListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckItemProfit2DataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CheckProfitListItemBean bean = getItem(position);
        if(null != bean) {
            bean.setNum(String.valueOf(position + 1));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // do nothing.
                }
            });
            holder.getDataBinding().setBean(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.check_item_profit_2;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private CheckItemProfit2DataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(CheckItemProfit2DataBinding dataBinding) {
            mDataBinding = dataBinding;
        }

        public CheckItemProfit2DataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    private static final DiffUtil.ItemCallback<CheckProfitListItemBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<CheckProfitListItemBean>() {

        @Override
        public boolean areItemsTheSame(@NonNull CheckProfitListItemBean oldItem, @NonNull CheckProfitListItemBean newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull CheckProfitListItemBean oldItem, @NonNull CheckProfitListItemBean newItem) {
            return oldItem.equals(newItem);
        }
    };
}
