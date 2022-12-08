package com.jd.saas.pdadelivery.detail.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.component.UpcCodeDialog;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailSkuItemBinding;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/4
 */
public class DeliveryDetailSkuAdapter extends RecyclerView.Adapter<DeliveryDetailSkuAdapter.ItemViewHolder> {
    private final List<DeliverySkuBean> mList = new ArrayList<>();
    private final OnEditItemClickListener onEditItemClickListener;

    public DeliveryDetailSkuAdapter(OnEditItemClickListener onEditItemClickListener) {
        this.onEditItemClickListener = onEditItemClickListener;
    }


    public interface OnEditItemClickListener {
        /**
         * 编辑商品信息
         */
        void onEditItem(DeliverySkuBean skuBean);

        /**
         * 展开库存记录的日志
         */
        void onExplainSkuLog(DeliverySkuBean skuBean);
    }

    public void move2Top(DeliverySkuBean bean) {
        int index = mList.indexOf(bean);
        if (index > 0) {
            mList.remove(index);
            mList.add(0, bean);
            notifyItemMoved(index, 0);
        }
        notifyItemChanged(0);
    }

    public void move2TopOrAdd(DeliverySkuBean bean) {
        int index = mList.indexOf(bean);
        if (index > 0) {
            mList.remove(index);
            mList.add(0, bean);
            notifyItemMoved(index, 0);
        } else if (index == 0) {
            notifyItemChanged(0);
        } else {
            mList.add(0, bean);
            notifyItemInserted(0);
        }
    }

    public boolean searchToEdit(String upcOrSkuId) {
        if (upcOrSkuId == null) {
            return false;
        }
        for (DeliverySkuBean bean : mList) {
            String[] upcCodes = bean.getUpcCodes();
            if (upcCodes != null) {
                for (String upcCode : upcCodes) {
                    if (upcOrSkuId.equals(upcCode)) {
                        onEditItemClickListener.onEditItem(bean);
                        return true;
                    }
                }
            }
            if (upcOrSkuId.equals(bean.getSkuId())) {
                onEditItemClickListener.onEditItem(bean);
                return true;
            }
        }
        return false;
    }

    public void refreshList(List<DeliverySkuBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeliveryDetailSkuAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryDetailSkuItemBinding dataBinding = DeliveryDetailSkuItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryDetailSkuAdapter.ItemViewHolder holder, int position) {
        DeliverySkuBean bean = mList.get(position);
        holder.bind(bean);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void changeItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            mList.get(position).setFold(false);
            notifyItemChanged(position);
        }
    }

    /**
     * 子布局DataBinding绑定  查看明细状态改变
     *
     * @author: ext.anxinlong
     * @date: 2021/5/31
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final DeliveryDetailSkuItemBinding mDataBinding;
        private final DeliveryDetailSkuLogAdapter adapter = new DeliveryDetailSkuLogAdapter();

        public ItemViewHolder(@NonNull DeliveryDetailSkuItemBinding mDataBinding) {
            super(mDataBinding.getRoot());
            this.mDataBinding = mDataBinding;
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance().getApplicationContext()));
            mDataBinding.recyclerview.setAdapter(adapter);//查看明细适配器
        }

        public void bind(DeliverySkuBean bean) {
            mDataBinding.setBean(bean);
            mDataBinding.layoutEditNumInfo.setOnClickListener(v -> onEditItemClickListener.onEditItem(bean));
            mDataBinding.tvArrow.setOnClickListener(v -> {
                if (bean.isFold()) {
                    onEditItemClickListener.onExplainSkuLog(bean);
                } else {
                    bean.setFold(true);
                    notifyItemChanged(getAdapterPosition());
                }
            });
            mDataBinding.setOnUpcMoreClick(v -> {
                String[] upcCodes = bean.getUpcCodes();
                if (upcCodes != null) {
                    UpcCodeDialog.open(v, upcCodes);
                }
            });
            if (bean.isFold()) {
                adapter.refreshList(null);
            } else {
                adapter.refreshList(bean.getLogBeans());
                mDataBinding.recyclerview.getLayoutParams().height = adapter.getHeight();
                mDataBinding.recyclerview.requestLayout();
            }
            mDataBinding.executePendingBindings();
        }
    }


}
