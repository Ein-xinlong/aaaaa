package com.jd.saas.pdacommon.printer_ble.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.databinding.BlueToothListItemDataBinding;
import com.jd.saas.pdacommon.printer_ble.bean.BlueDeviceInfo;

import java.util.ArrayList;
import java.util.List;

public class BlueToothPrintAdapter extends RecyclerView.Adapter<BlueToothPrintAdapter.BlueToothViewHolder> {
    private List<BlueDeviceInfo> blueDeviceInfoList=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public void refreshList(BlueDeviceInfo list) {
        if (!blueDeviceInfoList.contains(list)){
            blueDeviceInfoList.add(list);
        }
        notifyDataSetChanged();
    }

    public void deleteList(){
        if (blueDeviceInfoList.size()>=0){
            blueDeviceInfoList.clear();
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public BlueToothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BlueToothListItemDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        BlueToothViewHolder holder = new BlueToothViewHolder(binding.getRoot());
        holder.setDataBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BlueToothViewHolder holder, int position) {
        BlueDeviceInfo blueDeviceInfo = blueDeviceInfoList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
        holder.getBinding().setVm(blueDeviceInfo);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return blueDeviceInfoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.common_blue_tooth_item;
    }

    static class BlueToothViewHolder extends RecyclerView.ViewHolder {

        private BlueToothListItemDataBinding mBinding;

        public BlueToothViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setDataBinding(BlueToothListItemDataBinding dataBinding) {
            this.mBinding = dataBinding;
        }

        public BlueToothListItemDataBinding getBinding() {
            return mBinding;
        }
    }
    /**
     * 条目点击回调
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 条目内容点击回调接口
     */
    public interface OnItemClickListener {
        // 条目点击回调
        void onItemClick(int postion);
    }
}
