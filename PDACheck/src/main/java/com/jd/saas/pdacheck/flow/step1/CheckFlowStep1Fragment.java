package com.jd.saas.pdacheck.flow.step1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacheck.CheckInject;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckFlowStep1DataBinding;
import com.jd.saas.pdacheck.flow.step1.bean.CheckPreOrderBean;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.toast.MyToast;

import org.greenrobot.eventbus.Subscribe;

/**
 * 盘点第一步：预盘点列表fragment
 *
 * @author majiheng
 */
public class CheckFlowStep1Fragment extends SimpleFragment {

    private CheckFlowStep1DataBinding mDataBinding;
    private CheckFlowStep1ViewModel mViewModel;
    private CheckPreOrderListAdapter mAdapter;

    public static CheckFlowStep1Fragment newInstance(CheckListBean bean) {
        CheckFlowStep1Fragment fragment = new CheckFlowStep1Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider(this, CheckInject.injectViewModelFactory()).get(CheckFlowStep1ViewModel.class);
        mDataBinding.setLifecycleOwner(this);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckListBean mCheckListBean = (CheckListBean) getArguments().getSerializable("data");
        mViewModel.init(mCheckListBean);
        mAdapter = new CheckPreOrderListAdapter(new CheckPreOrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CheckPreOrderBean item) {
                if(mViewModel.mContentClickable.get()) {
                    // 如果可以删除，才弹窗提示
                    new SimpleAlertDialog.Builder(getContext(), R.string.check_new_task_delete_confirm_notice_preorder)
                            .setPositiveButton(v -> mViewModel.deleteDelCouHeader(item.getCouNo()))
                            .build()
                            .show();
                }
            }
        });
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.setEmptyView(mDataBinding.layoutNoData);
        mDataBinding.swipeRefresh.setOnRefreshListener(() -> mViewModel.refresh());
        mViewModel.taskNo.observe(getViewLifecycleOwner(), s -> mViewModel.refresh());
        mViewModel.preOrderList.observe(getViewLifecycleOwner(), checkPreOrderBeans -> mAdapter.submitList(checkPreOrderBeans));
        mViewModel.showToastEvent.observe(getViewLifecycleOwner(), msg -> {
            MyToast.show(msg, false);
        });
        mViewModel.isRefresh.observe(getViewLifecycleOwner(), isRefresh -> mDataBinding.swipeRefresh.setRefreshing(isRefresh));
    }

    @Subscribe
    public void refreshList(EventBean bean) {
        // 「盘点记录」页提交后，通知该页面刷新列表数据
        if(bean.getType() == EventBean.EVENT_REFRESH_LIST) {
            mViewModel.refresh();
        }
    }

    @Override
    protected void reload() {
        // do nothing
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_step_1;
    }
}
