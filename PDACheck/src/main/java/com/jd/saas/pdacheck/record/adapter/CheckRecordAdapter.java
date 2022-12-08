package com.jd.saas.pdacheck.record.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckAdapterRecordBinding;
import com.jd.saas.pdacheck.net.CheckCommitRequestBean;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;

import java.util.List;

/**
 * 盘存记录Adapter
 *
 * @author majiheng
 */
public class CheckRecordAdapter extends RecyclerView.Adapter<CheckRecordAdapter.ItemViewHolder> {

    private List<CheckCommitRequestBean.PdaStockTakeInfoDetailBO> mList;

    public void refreshList(List<CheckCommitRequestBean.PdaStockTakeInfoDetailBO> list) {
        if (null != list && list.size() != 0) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckAdapterRecordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder holder = new ItemViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CheckCommitRequestBean.PdaStockTakeInfoDetailBO bean = mList.get(position);
        if(null != bean) {
            holder.getBinding().ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 确认删除弹窗
                    new SimpleAlertDialog.Builder(holder.itemView.getContext(),R.string.check_record_delete_confirm_notice)
                            .setPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 改为全局列表刷新，我已经被单独刷新item给整无语了，线上偶尔蹦出来一个crash，但是平常从来没有出现过，都是模板代码
                                    if(mList.size() != 0) {
                                        mList.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }
                            }).build().show();
                }
            });
            holder.getBinding().setVm(bean);
            holder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        if(null != mList) {
            return mList.size();
        }else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.check_adapter_record;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        CheckAdapterRecordBinding binding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setBinding(CheckAdapterRecordBinding mBinding) {
            this.binding = mBinding;
        }

        public CheckAdapterRecordBinding getBinding() {
            return binding;
        }
    }

}
