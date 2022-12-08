package com.jd.saas.pdacheck.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckFragmentListBinding;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * 库存盘点任务列表Fragment
 *
 * @author ext.mengmeng
 */
public class CheckListFragment extends SimpleFragment {

    private CheckFragmentListBinding mDataBinding;
    private CheckListViewModel mViewModel;

    public static CheckListFragment newInstance() {
        return  new CheckListFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider(this).get(CheckListViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DialogBaseView dialogBaseView = new DialogBaseView(mContext,"");
        mViewModel.handleBaseNetUI(dialogBaseView);
        // 初始化rv
        mDataBinding.rv.setOrientation(RecyclerView.VERTICAL);
        mDataBinding.rv.setEmptyView(mDataBinding.tvNoData);
        mDataBinding.rv.setAdapter(mViewModel.getTaskListAdapter());
        // 获取列表
        mViewModel.getTaskList();
        // 下拉刷新
        mDataBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getTaskList();
            }
        });
        // 是否刷新监听
        mViewModel.mRefresh.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refresh) {
                mDataBinding.refresh.setRefreshing(refresh);
            }
        });
        // 弹出撤销任务二次确认对话框
        mViewModel.mRollBackDialog.observe(getViewLifecycleOwner(), new Observer<CheckListBean>() {
            @Override
            public void onChanged(CheckListBean bean) {
                if(null != bean) {
                    new SimpleAlertDialog.Builder(mContext,R.string.check_roll_back)
                            .setPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 撤回任务
                                    mViewModel.rollback(bean);
                                }
                            }).build().show();
                }
            }
        });
        // 审批被驳回，重新开始盘点弹窗
        mViewModel.mShowAlertDialog.observe(getViewLifecycleOwner(), new Observer<CheckListBean>() {
            @Override
            public void onChanged(CheckListBean checkListBean) {
                if(null != checkListBean) {
                    new SimpleAlertDialog.Builder(mContext, R.string.check_list_reject_content,checkListBean.getFlowMsg())
                            .setPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mViewModel.cancelProfit(checkListBean);
                                }
                            }).build().show();
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_list;
    }

    @Override
    protected void reload() {
        mViewModel.getTaskList();
    }

    @Subscribe
    public void refreshList(EventBean bean) {
        if(bean.getType() == EventBean.EVENT_REFRESH_LIST) {
            // 提交盘点成功后，刷新该列表数据
            mViewModel.getTaskList();
        }
    }
}