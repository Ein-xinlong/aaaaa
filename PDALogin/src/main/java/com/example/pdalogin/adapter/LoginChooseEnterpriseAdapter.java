package com.example.pdalogin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdalogin.R;
import com.example.pdalogin.bean.LoginChoseEnterpriseBean;
import com.example.pdalogin.databinding.LoginChooesenterAdapterDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择企业适配器
 * @author anxinlong
 */

public class LoginChooseEnterpriseAdapter extends RecyclerView.Adapter<LoginChooseEnterpriseAdapter.ItemViewHolder> {

    private final List<LoginChoseEnterpriseBean> mList = new ArrayList<>();

    public void refreshList(List<LoginChoseEnterpriseBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LoginChooesenterAdapterDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoginChooseEnterpriseAdapter.ItemViewHolder holder, int position) {
        LoginChoseEnterpriseBean bean = mList.get(position);
        if (bean != null) {
            // 设置条目bg
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(bean);
                }
            });
            bean.setBgType(position % 2 == 0 ? 1 : 2);
            holder.getDataBinding().setVm(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.login_item_chooesenter_layout;
    }

    /**
     * 子布局mDataBinding绑定
     * *
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LoginChooesenterAdapterDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(LoginChooesenterAdapterDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public LoginChooesenterAdapterDataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    public void setOnItemClick(OnItemClickListener itemClick) {
        this.mItemClickListener = itemClick;
    }

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(LoginChoseEnterpriseBean bean);
    }
}
