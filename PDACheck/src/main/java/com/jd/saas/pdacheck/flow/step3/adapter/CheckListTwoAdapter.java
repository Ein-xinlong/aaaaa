package com.jd.saas.pdacheck.flow.step3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckStepListTwoItemDataBinding;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuBean;

import org.jetbrains.annotations.NotNull;

public class CheckListTwoAdapter extends PagedListAdapter<CheckReviewSkuBean, CheckListTwoAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CheckReviewSkuBean item);
    }

    private final OnItemClickListener onItemClickListener;

    public CheckListTwoAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    private static final DiffUtil.ItemCallback<CheckReviewSkuBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CheckReviewSkuBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull @NotNull CheckReviewSkuBean oldItem, @NonNull @NotNull CheckReviewSkuBean newItem) {
                    return oldItem.getSkuId().equals(newItem.getSkuId());
                }


                @Override
                public boolean areContentsTheSame(@NonNull @NotNull CheckReviewSkuBean oldItem, @NonNull @NotNull CheckReviewSkuBean newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public CheckListTwoAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckStepListTwoItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.check_list_step_two_item, parent, false);
        CheckListTwoAdapter.ItemViewHolder viewHolder = new CheckListTwoAdapter.ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListTwoAdapter.ItemViewHolder holder, int position) {
        final CheckReviewSkuBean bean = getItem(position);
        holder.mDataBinding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(bean));
        holder.getDataBinding().setBean(bean);
        holder.getDataBinding().executePendingBindings();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private CheckStepListTwoItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(CheckStepListTwoItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        CheckStepListTwoItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
