package com.jd.saas.pdacommon.search;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.databinding.SearchResultItemDataBinding;

/**
 * 全局商品搜索结果adapter
 *
 * @author majiheng
 */
public class SearchGoodsResultAdapter extends PagedListAdapter<SearchGoodBean, SearchGoodsResultAdapter.ItemViewHolder> {

    public SearchGoodsResultAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchResultItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),viewType,parent,false);
        SearchGoodsResultAdapter.ItemViewHolder holder = new SearchGoodsResultAdapter.ItemViewHolder(dataBinding.getRoot());
        holder.setDataBinding(dataBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SearchGoodBean bean = getItem(position);
        if(null != bean) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClick.onItemClick(bean);
                }
            });
            holder.getDataBinding().setVm(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_goods_search;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private SearchResultItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(SearchResultItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public SearchResultItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    private static final DiffUtil.ItemCallback<SearchGoodBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<SearchGoodBean>() {

        @Override
        public boolean areItemsTheSame(@NonNull SearchGoodBean oldItem, @NonNull SearchGoodBean newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull SearchGoodBean oldItem, @NonNull SearchGoodBean newItem) {
            return oldItem.equals(newItem);
        }
    };

    // 条目点击监听
    private OnItemClick mItemClick;

    public interface OnItemClick {
        void onItemClick(SearchGoodBean searchGoodBean);
    }

    public void setOnItemClickListener(OnItemClick itemClickListener) {
        this.mItemClick = itemClickListener;
    }
}
