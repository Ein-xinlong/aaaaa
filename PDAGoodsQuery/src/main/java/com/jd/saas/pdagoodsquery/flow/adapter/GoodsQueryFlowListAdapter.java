package com.jd.saas.pdagoodsquery.flow.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.databinding.GoodQueryFlowItemDataBinding;
import com.jd.saas.pdagoodsquery.flow.model.GoodsQueryFlowBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存流水rv adapter
 *
 * @author majiheng
 */
public class GoodsQueryFlowListAdapter extends PagedListAdapter<GoodsQueryFlowBean,GoodsQueryFlowListAdapter.ItemViewHolder> {

    public GoodsQueryFlowListAdapter(){
       super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GoodQueryFlowItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),viewType,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        GoodsQueryFlowBean bean = getItem(position);
        if(null != bean) {
            holder.getDataBinding().setBean(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.goods_query_flow_item_layout;
    }

    private static final DiffUtil.ItemCallback<GoodsQueryFlowBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<GoodsQueryFlowBean>() {

        @Override
        public boolean areItemsTheSame(@NonNull GoodsQueryFlowBean oldItem, @NonNull GoodsQueryFlowBean newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull GoodsQueryFlowBean oldItem, @NonNull GoodsQueryFlowBean newItem) {
            return oldItem.equals(newItem);
        }
    };
    static class ItemViewHolder extends RecyclerView.ViewHolder{

        private GoodQueryFlowItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(GoodQueryFlowItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public GoodQueryFlowItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
