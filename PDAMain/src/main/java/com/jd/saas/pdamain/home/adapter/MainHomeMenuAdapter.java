package com.jd.saas.pdamain.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.databinding.MainHomeMenuItemListDataBinding;
import com.jd.saas.pdamain.home.bean.MainMenuItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页菜单列表adapter
 *
 * @author majiheng
 */
public class MainHomeMenuAdapter extends RecyclerView.Adapter<MainHomeMenuAdapter.ItemViewHolder> {

    private final List<MainMenuItemBean> mList = new ArrayList<>();

    public void refreshList(List<MainMenuItemBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainHomeMenuItemListDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        dataBinding.rvMenus.setLayoutManager(new GridLayoutManager(parent.getContext(),4));
        MainHomeMenuListAdapter adapter = new MainHomeMenuListAdapter();
        dataBinding.rvMenus.setAdapter(adapter);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        viewHolder.setAdapter(adapter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MainMenuItemBean bean = mList.get(position);
        if(null != bean) {
            holder.getAdapter().refreshList(bean.getChildren());
            holder.getBinding().setBean(bean);
            holder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.main_item_list;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private MainHomeMenuItemListDataBinding mBinding;
        private MainHomeMenuListAdapter mAdapter;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(MainHomeMenuItemListDataBinding dataBinding) {
            this.mBinding = dataBinding;
        }

        public MainHomeMenuItemListDataBinding getBinding() {
            return mBinding;
        }

        public void setAdapter(MainHomeMenuListAdapter adapter) {
            this.mAdapter = adapter;
        }

        public MainHomeMenuListAdapter getAdapter() {
            return mAdapter;
        }
    }
}
