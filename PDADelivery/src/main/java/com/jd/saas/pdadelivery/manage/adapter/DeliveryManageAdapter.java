package com.jd.saas.pdadelivery.manage.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdadelivery.databinding.DeliveryListItemBinding;
import com.jd.saas.pdadelivery.manage.bean.DeliveryBean;

import org.jetbrains.annotations.NotNull;

/**
 * 收货管理
 *
 * @author ext.mengmeng
 */
public class DeliveryManageAdapter extends PagedListAdapter<DeliveryBean, DeliveryManageAdapter.DeliveryViewHolder> {

    private final OnItemClickListener onItemClickListener;

    public DeliveryManageAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveryViewHolder(DeliveryListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeliveryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    class DeliveryViewHolder extends RecyclerView.ViewHolder {
        private final DeliveryListItemBinding binding;

        public DeliveryViewHolder(@NonNull DeliveryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DeliveryBean item) {
            binding.setBean(item);
            binding.setOnItemClick(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item);
                }
            });
            binding.executePendingBindings();
        }
    }


    private static final DiffUtil.ItemCallback<DeliveryBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DeliveryBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull @NotNull DeliveryBean oldItem, @NonNull @NotNull DeliveryBean newItem) {
                    return oldItem.getAsnNo().equals(newItem.getAsnNo());
                }


                @Override
                public boolean areContentsTheSame(@NonNull @NotNull DeliveryBean oldItem, @NonNull @NotNull DeliveryBean newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public interface OnItemClickListener {
        void onItemClick(DeliveryBean item);
    }
}
