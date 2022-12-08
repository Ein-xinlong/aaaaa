package com.jd.saas.pdainventorycheck.details.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.databinding.InventorycheckAdapterLocationinfoBinding;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckLocationBean;

import org.jetbrains.annotations.NotNull;

public class InventoryCheckLocationAdapter extends PagedListAdapter<InventoryCheckLocationBean,InventoryCheckLocationAdapter.LocationViewHolder> {

    public InventoryCheckLocationAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @NotNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        InventorycheckAdapterLocationinfoBinding binding = InventorycheckAdapterLocationinfoBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);
        LocationViewHolder holder = new LocationViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LocationViewHolder holder, int position) {
        holder.mBinding.setBean(getItem(position));
    }


    class LocationViewHolder extends RecyclerView.ViewHolder{
        InventorycheckAdapterLocationinfoBinding mBinding;
        public LocationViewHolder(@NonNull @NotNull InventorycheckAdapterLocationinfoBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

    }
    private static final DiffUtil.ItemCallback<InventoryCheckLocationBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<InventoryCheckLocationBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull InventoryCheckLocationBean oldItem, @NonNull @NotNull InventoryCheckLocationBean newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull @NotNull InventoryCheckLocationBean oldItem, @NonNull @NotNull InventoryCheckLocationBean newItem) {
            return oldItem.equals(newItem);
        }
    };
}
