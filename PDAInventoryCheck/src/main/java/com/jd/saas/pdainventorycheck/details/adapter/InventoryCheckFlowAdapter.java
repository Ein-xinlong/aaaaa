package com.jd.saas.pdainventorycheck.details.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.jd.saas.pdainventorycheck.databinding.InventorycheckAdapterFlowinfoBinding;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckFlowBean;

/**
 * 库存流水rv adapter
 *
 * @author majiheng
 */
public class InventoryCheckFlowAdapter extends PagedListAdapter<InventoryCheckFlowBean,InventoryCheckFlowAdapter.FlowViewHolder> {

    public InventoryCheckFlowAdapter(){
       super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FlowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InventorycheckAdapterFlowinfoBinding dataBinding = InventorycheckAdapterFlowinfoBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        FlowViewHolder viewHolder = new FlowViewHolder(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlowViewHolder holder, int position) {
        InventoryCheckFlowBean bean = getItem(position);
        holder.mDataBinding.setBean(getItem(position));

    }

    private static final DiffUtil.ItemCallback<InventoryCheckFlowBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<InventoryCheckFlowBean>() {

        @Override
        public boolean areItemsTheSame(@NonNull InventoryCheckFlowBean oldItem, @NonNull InventoryCheckFlowBean newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull InventoryCheckFlowBean oldItem, @NonNull InventoryCheckFlowBean newItem) {
            return oldItem.equals(newItem);
        }
    };
    static class FlowViewHolder extends RecyclerView.ViewHolder{

        private InventorycheckAdapterFlowinfoBinding mDataBinding;

        public FlowViewHolder(@NonNull InventorycheckAdapterFlowinfoBinding binding) {
            super(binding.getRoot());
            mDataBinding = binding;
        }

    }
}
