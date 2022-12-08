package com.jd.saas.pdamain.store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.databinding.MainStoreItemDataBinding;
import com.jd.saas.pdamain.home.bean.MainShopListBean;

import java.util.ArrayList;
import java.util.List;

public class MainStoreAdapter extends RecyclerView.Adapter<MainStoreAdapter.ItemViewHolder> {

    // 网络请求到的门店列表
    private List<MainShopListBean> mShopList = new ArrayList<>();

    public void refreshList(List<MainShopListBean> list) {
        mShopList.clear();
        mShopList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainStoreItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        MainStoreAdapter.ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MainShopListBean bean = mShopList.get(position);
        if (bean != null) {
            holder.getBinding().setBean(bean);
            holder.getBinding().executePendingBindings();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCheckedChange.checkedChange(bean);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.main_item_store_list;
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private MainStoreItemDataBinding mBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(MainStoreItemDataBinding dataBinding) {
            this.mBinding = dataBinding;
        }

        public MainStoreItemDataBinding getBinding() {
            return mBinding;
        }

    }

    public interface OnCheckedChange {
        void checkedChange(MainShopListBean bean);
    }

    private OnCheckedChange onCheckedChange;

    public void setOnCheckedChange(OnCheckedChange onCheckedChange) {
        this.onCheckedChange = onCheckedChange;
    }
}
