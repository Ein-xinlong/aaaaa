package com.jd.saas.pdacheck.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckRecyclerviewListBinding;
import com.jd.saas.pdacheck.list.model.CheckListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存盘点-任务列表Adapter
 *
 * @author ext.mengmeng
 */
public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckViewHolder> {

    private final List<CheckListBean> mList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public void refreshList(List<CheckListBean> list) {
        mList.clear();
        if (null != list && list.size() != 0) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 条目点击回调
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckRecyclerviewListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        CheckViewHolder holder = new CheckViewHolder(binding.getRoot());
        holder.setDataBinding(binding);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.check_item_task;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListAdapter.CheckViewHolder holder, int position) {
        CheckListBean bean = mList.get(position);
        if (bean != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(bean);
                }
            });
            holder.getBinding().tvCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemBtn1Click(bean);
                }
            });
            holder.getBinding().tvRollback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemBtn2Click(bean);
                }
            });
            holder.getBinding().setBean(bean);
            holder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class CheckViewHolder extends RecyclerView.ViewHolder {

        private CheckRecyclerviewListBinding mBinding;

        public void setDataBinding(CheckRecyclerviewListBinding dataBinding) {
            this.mBinding = dataBinding;
        }

        public CheckRecyclerviewListBinding getBinding() {
            return mBinding;
        }

        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * 条目内容点击回调接口
     */
    public interface OnItemClickListener {
        // 条目点击回调
        void onItemClick(CheckListBean bean);
        // 查看任务btn点击回调
        void onItemBtn1Click(CheckListBean bean);
        // 撤回btn点击回调
        void onItemBtn2Click(CheckListBean bean);
    }
}
