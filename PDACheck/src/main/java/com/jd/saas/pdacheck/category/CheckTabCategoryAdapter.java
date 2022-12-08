package com.jd.saas.pdacheck.category;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacheck.databinding.CheckItemCategoryTabBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 横向导航索引的适配器
 *
 * @author gouhetong
 */
public class CheckTabCategoryAdapter extends RecyclerView.Adapter<CheckTabCategoryAdapter.CheckTabCategoryViewHolder> {
    private final List<CheckCategoryNode> categoryBeans = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public CheckTabCategoryAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CheckTabCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckItemCategoryTabBinding dataBinding = CheckItemCategoryTabBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CheckTabCategoryViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckTabCategoryViewHolder holder, int position) {
        holder.bind(categoryBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryBeans.size();
    }

    public void setData(List<CheckCategoryNode> list) {
        categoryBeans.clear();
        Collections.reverse(list);
        categoryBeans.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(CheckCategoryNode bean);
    }

    class CheckTabCategoryViewHolder extends RecyclerView.ViewHolder {
        private final CheckItemCategoryTabBinding mBinding;

        public CheckTabCategoryViewHolder(@NonNull CheckItemCategoryTabBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(CheckCategoryNode bean) {
            mBinding.setBean(bean);
            mBinding.setOnItemClick(v -> onItemClickListener.onItemClick(bean));
            mBinding.executePendingBindings();
        }
    }
}
