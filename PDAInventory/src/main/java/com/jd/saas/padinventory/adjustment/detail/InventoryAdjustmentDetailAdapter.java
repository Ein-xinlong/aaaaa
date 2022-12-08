package com.jd.saas.padinventory.adjustment.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.InventoryAdjustmentDetailItemDataBinding;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.log.Logger;

import java.util.List;


/**
 * @author: ext.anxinlong
 * @date: 2021/6/1
 */
public class InventoryAdjustmentDetailAdapter extends RecyclerView.Adapter<InventoryAdjustmentDetailAdapter.ItemViewHolder> {

    private ObservableField<List<InventoryAdjustmentDetailItemBean>> mList;

    public void refreshList(ObservableField<List<InventoryAdjustmentDetailItemBean>> list) {
        if (null != list && list.get().size() != 0) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InventoryAdjustmentDetailItemDataBinding mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(mDataBinding.getRoot());
        viewHolder.setDataBinding(mDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        InventoryAdjustmentDetailItemBean bean = mList.get().get(position);
        if (bean != null) {
            holder.getDataBinding().inventoryAdjustmentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 确认删除弹窗
                    new SimpleAlertDialog.Builder(holder.itemView.getContext(),R.string.inventory_adjustment_detail_dialog_title)
                            .setPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int deletedPosition = holder.getAdapterPosition();
                                    if(deletedPosition == -1) {
                                        Logger.d("InventoryAdjustmentDetailAdapter","deleted err.");
                                        return;
                                    }
                                    mList.get().remove(deletedPosition);
                                    notifyItemRemoved(deletedPosition);
                                    mList.notifyChange();
                                }
                            }).build().show();
                }
            });
            holder.getDataBinding().setDetailItemVM(bean);
            holder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.inventory_adjustment_detail_item;
    }

    @Override
    public int getItemCount() {
        if(null != mList) {
            return mList.get().size();
        }else {
            return 0;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private InventoryAdjustmentDetailItemDataBinding mDataBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(InventoryAdjustmentDetailItemDataBinding dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public InventoryAdjustmentDetailItemDataBinding getDataBinding() {
            return mDataBinding;
        }
    }
}
