package com.jd.saas.pdacheck.flow.step1;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.databinding.CheckItemPreOrderBinding;
import com.jd.saas.pdacheck.flow.step1.bean.CheckPreOrderBean;

import org.jetbrains.annotations.NotNull;

/**
 * 列表适配器
 *
 * @author gouhetong
 */
public class CheckPreOrderListAdapter extends PagedListAdapter<CheckPreOrderBean, CheckPreOrderListAdapter.PreOrderViewHolder> {

    private final OnItemClickListener onItemClickListener;

    public CheckPreOrderListAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PreOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckItemPreOrderBinding dataBinding = CheckItemPreOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PreOrderViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PreOrderViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    private static final DiffUtil.ItemCallback<CheckPreOrderBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CheckPreOrderBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull @NotNull CheckPreOrderBean oldItem, @NonNull @NotNull CheckPreOrderBean newItem) {
                    return oldItem.getCouNo().equals(newItem.getCouNo());
                }


                @Override
                public boolean areContentsTheSame(@NonNull @NotNull CheckPreOrderBean oldItem, @NonNull @NotNull CheckPreOrderBean newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public interface OnItemClickListener {
        void onItemClick(CheckPreOrderBean item);
    }

    class PreOrderViewHolder extends RecyclerView.ViewHolder {
        private final CheckItemPreOrderBinding mBinding;

        public PreOrderViewHolder(@NonNull CheckItemPreOrderBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(CheckPreOrderBean bean) {
            mBinding.setBean(bean);
            mBinding.setOnItemDelete(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(bean);
                }
            });
            mBinding.executePendingBindings();
        }
    }


}
