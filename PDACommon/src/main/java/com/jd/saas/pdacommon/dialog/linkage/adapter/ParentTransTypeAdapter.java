package com.jd.saas.pdacommon.dialog.linkage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.databinding.DialogTranstypeAdapterParentBinding;
import com.jd.saas.pdacommon.dialog.linkage.model.ParentTansType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 库位父类型Dialog的Adapter
 */
public class ParentTransTypeAdapter extends RecyclerView.Adapter<ParentTransTypeAdapter.ParentViewHolder> {
    private final List<ParentTansType> mList = new ArrayList<>();
    @NonNull
    private ParentTansType selectType;
    @NonNull
    private final OnItemClick mItemClick;

    private final ParentTansType defaultAll;

    public ParentTransTypeAdapter(@NonNull ParentTransTypeAdapter.OnItemClick itemClickListener) {
        this.mItemClick = itemClickListener;
        defaultAll = new ParentTansType();
        defaultAll.setName(MyApplication.getInstance().getString(R.string.stock_trans_type_all));
        selectType = defaultAll;
    }

    @NonNull
    public ParentTansType getSelectType() {
        return selectType;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParentTansType> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            selectType = defaultAll;
            mList.add(defaultAll);
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @NotNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DialogTranstypeAdapterParentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_transtype_parent, parent, false);
        return new ParentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ParentViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public interface OnItemClick {
        void onItemClick(ParentTansType tansType);
    }


    class ParentViewHolder extends RecyclerView.ViewHolder {

        private final DialogTranstypeAdapterParentBinding mBinding;

        public ParentViewHolder(@NonNull @NotNull DialogTranstypeAdapterParentBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(ParentTansType bean) {
            bean.setSelected(bean.equals(selectType));
            mBinding.setBean(bean);
            mBinding.getRoot().setOnClickListener(v -> {
                setSelectedType(bean);
                mItemClick.onItemClick(bean);
            });
            mBinding.executePendingBindings();
        }

        @SuppressLint("NotifyDataSetChanged")
        private void setSelectedType(ParentTansType bean) {
            selectType = bean;
            notifyDataSetChanged();
        }
    }
}
