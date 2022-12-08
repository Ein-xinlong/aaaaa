package com.jd.saas.pdacheck.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacheck.CheckInject;
import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacheck.category.repo.CheckCategoryTreeUtil;
import com.jd.saas.pdacheck.databinding.CheckFragmentCategoryBinding;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.toast.MyToast;

import java.util.ArrayList;

/**
 * 指定类目
 *
 * @author gouhetong
 */
public class CheckCategoryFragment extends SimpleFragment {

    private CheckFragmentCategoryBinding mDataBinding;
    private CheckCategoryViewModel mViewModel;
    private CheckCategoryAdapter mAdapter;
    private CheckTabCategoryAdapter mTabAdapter;

    public static CheckCategoryFragment newInstance() {
        return new CheckCategoryFragment();
    }

    @Override
    protected void reload() {
        mViewModel.reset();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_category;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this, CheckInject.injectViewModelFactory()).get(CheckCategoryViewModel.class);
        // 解析bundle，刷新ui
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CheckRouterPath.CHECK_CATEGORY_PARAM_SELECT)) {
            ArrayList<String> ids = bundle.getStringArrayList(CheckRouterPath.CHECK_CATEGORY_PARAM_SELECT);
            mViewModel.initSelectIds(ids);
        } else {
            mViewModel.initSelectIds(new ArrayList<>());
        }

        mDataBinding.setVm(mViewModel);
        mViewModel.handleBaseNetUI(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new CheckCategoryAdapter(new CheckCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CheckCategoryNode bean) {
                mViewModel.curNode.postValue(bean);
            }

            @Override
            public void onItemSelectIconClick(CheckCategoryNode bean) {
                mViewModel.selectCheckCategoryNode(bean);
                mAdapter.notifyDataSetChanged();
            }
        });
        mDataBinding.recyclerView.setEmptyView(mDataBinding.layoutNoData);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.swipeRefresh.setOnRefreshListener(() -> mViewModel.refresh());
        mTabAdapter = new CheckTabCategoryAdapter(bean -> mViewModel.curNode.postValue(bean));
        mDataBinding.tabList.setAdapter(mTabAdapter);

        mViewModel.reset();

        mViewModel.curNode.observe(getViewLifecycleOwner(), node -> {
            mAdapter.setData(node.getChildren());
            mTabAdapter.setData(CheckCategoryTreeUtil.getParents(node));
        });

        mViewModel.msgList.observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) {
                String pop = msg.poll();
                if (pop != null) {
                    MyToast.show(pop, false);
                    mViewModel.msgList.postValue(msg);
                }
            }
        });
        mViewModel.isRefresh.observe(getViewLifecycleOwner(), isRefresh ->
                mDataBinding.swipeRefresh.setRefreshing(isRefresh));
        mViewModel.cancelEvent.observe(getViewLifecycleOwner(), value ->
                finish()
        );
        mViewModel.ensureEvent.observe(getViewLifecycleOwner(), value ->
                {
                    EventBean eventBean = new EventBean();
                    eventBean.setType(EventBean.EVENT_CHECK_NEW_TASK);
                    eventBean.setData(new ArrayList<>(value));
                    EventBusManager.post(eventBean);
                    finish();
                }

        );
    }
}
