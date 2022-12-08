package com.jd.saas.pdapersonal.store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.databinding.PersonalStoreItemDataBinding;
import com.jd.saas.pdapersonal.store.bean.PersonalNewShopListBean;

import java.util.ArrayList;
import java.util.List;

public class PersonalStoreAdapter extends RecyclerView.Adapter<PersonalStoreAdapter.ItemViewHolder> {
    // 网络请求到的门店列表
    private List<PersonalNewShopListBean> mShopList = new ArrayList<>();

    public void refreshList(List<PersonalNewShopListBean> list) {
        mShopList.clear();
        mShopList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PersonalStoreItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        PersonalStoreAdapter.ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        PersonalNewShopListBean bean = mShopList.get(position);
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
        return R.layout.personal_store_item;
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private PersonalStoreItemDataBinding mBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(PersonalStoreItemDataBinding dataBinding) {
            this.mBinding = dataBinding;
        }

        public PersonalStoreItemDataBinding getBinding() {
            return mBinding;
        }

    }

    public interface OnCheckedChange {
        void checkedChange(PersonalNewShopListBean bean);
    }

    private OnCheckedChange onCheckedChange;

    public void setOnCheckedChange(OnCheckedChange onCheckedChange) {
        this.onCheckedChange = onCheckedChange;
    }
}
