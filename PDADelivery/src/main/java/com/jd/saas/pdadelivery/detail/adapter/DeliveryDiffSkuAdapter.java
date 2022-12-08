package com.jd.saas.pdadelivery.detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryDiffSkuItemBinding;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDiffSkuBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeliveryDiffSkuAdapter extends RecyclerView.Adapter<DeliveryDiffSkuAdapter.DiffSkuViewHolder> {
    private final List<DeliveryDiffSkuBean> mList;
    private OnUpcMoreClickListener onUpcMoreClickListener;
    private OnSelectReasonClickListener onSelectReasonClickListener;

    public DeliveryDiffSkuAdapter(List<DeliveryDiffSkuBean> mList) {
        this.mList = mList;
    }

    public void setOnUpcMoreClickListener(OnUpcMoreClickListener onUpcMoreClickListener) {
        this.onUpcMoreClickListener = onUpcMoreClickListener;
    }

    public void setOnSelectReasonClickListener(OnSelectReasonClickListener onSelectReasonClickListener) {
        this.onSelectReasonClickListener = onSelectReasonClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public DiffSkuViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DeliveryDiffSkuItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.delivery_diff_sku_item, parent, false);
        return new DiffSkuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DiffSkuViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DiffSkuViewHolder extends RecyclerView.ViewHolder {
        final DeliveryDiffSkuItemBinding binding;

        public DiffSkuViewHolder(@NonNull @NotNull DeliveryDiffSkuItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DeliveryDiffSkuBean bean) {
            binding.setBean(bean);
            binding.setOnSelectReasonClick(v -> {
                if (onSelectReasonClickListener != null) {
                    onSelectReasonClickListener.onSelectReasonClick(v, bean);
                }
            });
            binding.setOnUpcMoreClick(v -> {
                if (onUpcMoreClickListener != null) {
                    onUpcMoreClickListener.onUpcMoreClick(v, bean);
                }
            });
            binding.executePendingBindings();
        }
    }

    public interface OnUpcMoreClickListener {
        void onUpcMoreClick(View view, DeliveryDiffSkuBean bean);
    }

    public interface OnSelectReasonClickListener {
        void onSelectReasonClick(View view, DeliveryDiffSkuBean bean);
    }
}
