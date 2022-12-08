package com.jd.saas.pdacheck.category;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacheck.databinding.CheckItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前类目列表的适配器
 *
 * @author gouhetong
 */
public class CheckCategoryAdapter extends RecyclerView.Adapter<CheckCategoryAdapter.CheckCategoryViewHolder> {
    private final List<CheckCategoryNode> categoryBeans = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public CheckCategoryAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CheckCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckItemCategoryBinding dataBinding = CheckItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CheckCategoryViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckCategoryViewHolder holder, int position) {
        holder.bind(categoryBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryBeans.size();
    }

    public void setData(List<CheckCategoryNode> list) {
        categoryBeans.clear();
        categoryBeans.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(CheckCategoryNode bean);

        void onItemSelectIconClick(CheckCategoryNode bean);
    }

    class CheckCategoryViewHolder extends RecyclerView.ViewHolder {
        private final CheckItemCategoryBinding mBinding;

        public CheckCategoryViewHolder(@NonNull CheckItemCategoryBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(CheckCategoryNode bean) {
            mBinding.setBean(bean);
            mBinding.ivCheck.setImageResource(getIcon(bean));
            mBinding.setOnItemClick(v -> {
                if (bean.nextEnable()) {
                    onItemClickListener.onItemClick(bean);
                } else {
                    onItemClickListener.onItemSelectIconClick(bean);
                }
            });
            mBinding.setOnSelectIconClick(v -> onItemClickListener.onItemSelectIconClick(bean));
            mBinding.executePendingBindings();
        }

        public int getIcon(CheckCategoryNode bean) {
            switch (bean.getSelectSate()) {
                case PART:
                    return R.drawable.ic_checked_part;
                case ALL:
                    return R.drawable.ic_checked;
                default:
                    return R.drawable.ic_unchecked;
            }
        }
    }
}
