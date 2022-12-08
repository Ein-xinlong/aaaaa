package com.jd.saas.pdacheck.flow.step2;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.databinding.CheckItemMissedCheckSkuBinding;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 漏盘商品列表适配器
 *
 * @author gouhetong
 */
public class CheckMissedSkuListAdapter extends RecyclerView.Adapter<CheckMissedSkuListAdapter.MissedCheckSkuViewHolder> {
    private final ArrayList<CheckMissedSkuBean> list = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public CheckMissedSkuListAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MissedCheckSkuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckItemMissedCheckSkuBinding dataBinding = CheckItemMissedCheckSkuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MissedCheckSkuViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MissedCheckSkuViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<CheckMissedSkuBean> missedCheckSkuBeans) {
        list.clear();
        list.addAll(missedCheckSkuBeans);
        notifyDataSetChanged();
    }

    public void notifyItemChanged(CheckMissedSkuBean bean) {
        int index = list.indexOf(bean);
        if (index >= 0) {
            list.set(index,bean);
            notifyItemChanged(index);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CheckMissedSkuBean item);
    }

    class MissedCheckSkuViewHolder extends RecyclerView.ViewHolder {
        private final CheckItemMissedCheckSkuBinding mBinding;

        public MissedCheckSkuViewHolder(@NonNull CheckItemMissedCheckSkuBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(CheckMissedSkuBean bean) {
            mBinding.setBean(bean);
            mBinding.setOnItemClick(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(bean);
                }
            });
            mBinding.executePendingBindings();
        }
    }


}
