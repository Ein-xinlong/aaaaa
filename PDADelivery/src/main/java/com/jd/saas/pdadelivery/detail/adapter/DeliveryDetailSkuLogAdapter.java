package com.jd.saas.pdadelivery.detail.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailSkuLogItemBinding;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuLogBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: ext.anxinlong
 * @date: 2021/6/4
 */
public class DeliveryDetailSkuLogAdapter extends RecyclerView.Adapter<DeliveryDetailSkuLogAdapter.ItemViewHolder> {

    private final List<DeliverySkuLogBean> mList = new ArrayList<>();

    public void refreshList(List<DeliverySkuLogBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public int getHeight() {
        return getItemCount() * ScreenUtil.dp2px(MyApplication.getInstance(), 25);
    }

    @NonNull
    @Override
    public DeliveryDetailSkuLogAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryDetailSkuLogItemBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new ItemViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryDetailSkuLogAdapter.ItemViewHolder holder, int position) {
        DeliverySkuLogBean bean = mList.get(position);
        if (bean != null) {
            holder.getDataBinding().setBean(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.delivery_detail_sku_log_item;
    }

    /**
     * 子布局DataBinding绑定
     *
     * @author: ext.anxinlong
     * @date: 2021/5/31
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final DeliveryDetailSkuLogItemBinding mDataBinding;

        public ItemViewHolder(@NonNull DeliveryDetailSkuLogItemBinding mDataBinding) {
            super(mDataBinding.getRoot());
            this.mDataBinding = mDataBinding;
        }

        public DeliveryDetailSkuLogItemBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
