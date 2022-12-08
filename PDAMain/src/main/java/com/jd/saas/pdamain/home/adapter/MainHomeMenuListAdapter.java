package com.jd.saas.pdamain.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.router.FlutterRouter;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.databinding.MainHomeMenuItemDataBinding;
import com.jd.saas.pdamain.home.MenuResource;
import com.jd.saas.pdamain.home.bean.MainMenuItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 嵌套在菜单item里面的list adapter
 *
 * @author majiheng
 */
public class MainHomeMenuListAdapter extends RecyclerView.Adapter<MainHomeMenuListAdapter.ItemViewHolder> {

    private final List<MainMenuItemBean> mList = new ArrayList<>();

    public void refreshList(List<MainMenuItemBean> list) {
        if (null != list && list.size() != 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainHomeMenuItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(dataBinding.getRoot());
        viewHolder.setDataBinding(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MainMenuItemBean bean = mList.get(position);
        Context context = holder.itemView.getContext();
        if (null != bean) {
            // 设置icon
            MenuResource menuResource = MenuResource.fromResourceCode(bean.getResourceCode());
            holder.getBinding().tvMenu.setCompoundDrawablesWithIntrinsicBounds(0, menuResource.getIcon(), 0, 0);
            holder.itemView.setOnClickListener(v -> {
                MenuResource menuResource1 = MenuResource.fromResourceCode(bean.getResourceCode());
                String router = menuResource1.getRouter();
                if (router.startsWith("pda://")) {
                    RouterClient.getInstance().forward(holder.itemView.getContext(), router);
                } else if (router.startsWith("flutter://")) {
                    FlutterRouter.open(context, router, null);
                } else {
                    // 无跳转，提示：暂无该模块
                    MyToast.show(R.string.main_module_empty, false);
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

    @Override
    public int getItemViewType(int position) {
        return R.layout.main_item_menu;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private MainHomeMenuItemDataBinding mBinding;

        public void setDataBinding(MainHomeMenuItemDataBinding dataBinding) {
            this.mBinding = dataBinding;
        }

        public MainHomeMenuItemDataBinding getBinding() {
            return mBinding;
        }
    }
}
