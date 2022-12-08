package com.jd.saas.pdacommon.dialog.linkage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.databinding.DialogTranstypeAdapterChildBinding;
import com.jd.saas.pdacommon.dialog.linkage.model.ChildTansType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 库位子类型Adapter
 *
 * @author ext.mengmeng
 */
public class ChildTransTypeAdapter extends RecyclerView.Adapter<ChildTransTypeAdapter.ChildViewHolder> {
    private final List<ChildTansType> mList = new ArrayList<>();
    @NonNull
    private final OnItemClick mItemClick;

    private final ChildTansType defaultAll;

    public ChildTransTypeAdapter(@NonNull OnItemClick itemClickListener) {
        this.mItemClick = itemClickListener;
        defaultAll = new ChildTansType();
        defaultAll.setName(MyApplication.getInstance().getString(R.string.stock_trans_type_default));
        mList.add(defaultAll);
    }

    public interface OnItemClick {
        void onItemClick(ChildTansType tansType);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void freshList(List<ChildTansType> list) {
        mList.clear();
        mList.add(defaultAll);
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DialogTranstypeAdapterChildBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_transtype_child, parent, false);
        return new ChildViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChildViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ChildViewHolder extends RecyclerView.ViewHolder {
        private final DialogTranstypeAdapterChildBinding mBinding;

        public ChildViewHolder(@NonNull @NotNull DialogTranstypeAdapterChildBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(ChildTansType bean) {
            mBinding.setBean(bean);
            mBinding.getRoot().setOnClickListener(v -> {
                //回调接口，赋值
                mItemClick.onItemClick(bean);
            });
            mBinding.executePendingBindings();
        }
    }

}
