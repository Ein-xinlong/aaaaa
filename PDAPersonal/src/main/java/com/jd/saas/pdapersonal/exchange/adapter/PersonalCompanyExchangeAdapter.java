package com.jd.saas.pdapersonal.exchange.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.databinding.PersonalCompanyExchangeItemDataBinding;
import com.jd.saas.pdapersonal.exchange.bean.PersonalCompanyItemBean;
import com.jd.saas.pdapersonal.router.PersonalRouterPath;

import java.util.ArrayList;
import java.util.List;

/**
 * 更换租户和门店列表
 *
 * @author majiheng
 */
public class PersonalCompanyExchangeAdapter extends RecyclerView.Adapter<PersonalCompanyExchangeAdapter.ItemViewHolder> {

    private final List<PersonalCompanyItemBean> mList = new ArrayList<>();

    public void refreshList(List<PersonalCompanyItemBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PersonalCompanyExchangeItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        PersonalCompanyItemBean bean = mList.get(position);
        if (bean != null) {
            holder.getDataBinding().setVm(bean);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click(holder.itemView.getContext(), bean);
                }
            });
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.personal_item_company_exchange;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private PersonalCompanyExchangeItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(PersonalCompanyExchangeItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public PersonalCompanyExchangeItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }

    /**
     * 条目点击
     */
    public void click(Context context, PersonalCompanyItemBean personalCompanyItemBean) {
        // 将租户传到门店选择页面
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", personalCompanyItemBean);
        RouterClient.getInstance().forward(context, PersonalRouterPath.HOST_PATH_STORE, bundle);
    }
}
