package com.jd.saas.pdacheck.check.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckBoxProductItemDataBinding;
import com.jd.saas.pdacommon.search.SearchResultBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 多箱柜商品列表
 * @author majiheng
 */
public class CheckBoxProductsAdapter extends RecyclerView.Adapter<CheckBoxProductsAdapter.ItemViewHolder>{

    // 列表数据
    List<SearchResultBean.BoxProducts> mList = new ArrayList<>();

    // 刷新列表
    public void refreshList(List<SearchResultBean.BoxProducts> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    // 清空数据
    public void clear() {
        if(null != mList && mList.size() != 0) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckBoxProductItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SearchResultBean.BoxProducts bean = mList.get(position);
        if(null != bean) {
            holder.getDataBinding().setBean(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.check_item_box_product;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position,@NonNull List<Object> payloads) {
        // 初始化需要局部刷新的控件
        holder.getDataBinding().tvTest.setText("0");
        if(payloads.isEmpty()) {
            //如果payloads没数据，说明不是局部刷新，下面这句是关键，通过源码看 会执行不带payloads参数的onBindViewHolder
            super.onBindViewHolder(holder, position, payloads);
        }else {
            // 局部刷新
            holder.getDataBinding().tvTest.setText(payloads.get(0).toString());
        }
    }

    // 用户设置子品实际盘点数量
    public void setCheckNumber(String checkNumber) {
        BigDecimal checkNumberBigDecimal = new BigDecimal(checkNumber);
        for (int i = 0; i < mList.size(); i++) {
            BigDecimal boxNumBigDecimal = new BigDecimal(mList.get(i).getBoxNum());
            notifyItemChanged(i,checkNumberBigDecimal.multiply(boxNumBigDecimal));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private CheckBoxProductItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(CheckBoxProductItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        CheckBoxProductItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
