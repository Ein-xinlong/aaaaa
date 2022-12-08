package com.jd.saas.pdacheck.flow.step4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckFlowStep4DataBinding;
import com.jd.saas.pdacheck.flow.step3.dialog.CheckReviewSortTypeDialog;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortKeyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortOption;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitListItemBean;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

/**
 * 盘点第四步：损益单fragment
 *
 * @author majiheng
 */
public class CheckFLowStep4Fragment extends SimpleFragment {

    private CheckFlowStep4ViewModel mViewModel;
    private CheckFlowStep4DataBinding mDataBinding;

    public static CheckFLowStep4Fragment newInstance(CheckListBean bean) {
        CheckFLowStep4Fragment fragment = new CheckFLowStep4Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(CheckFlowStep4ViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 获取盘点列表传递过来的bean
        CheckListBean mCheckListBean = (CheckListBean) getArguments().getSerializable("data");
        mViewModel.init(mCheckListBean);
        // 初始化页面loading
        mViewModel.handleBaseNetUI(this);
        // 初始化悬浮loading
        ProgressDialog loadingDialog = new ProgressDialog(mContext);
        mViewModel.mShowLoadingDialog.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if(show) {
                    loadingDialog.show();
                }else {
                    loadingDialog.dismiss();
                }
            }
        });
        // 初始化左侧列表
        mDataBinding.list1.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.list1.setAdapter(mViewModel.getLeftListAdapter());
        // 初始化右侧列表
        mDataBinding.list2.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.list2.setAdapter(mViewModel.getRightListAdapter());
        // 绑定左右两个列表的滑动监听
        RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];
        scrollListeners[0] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDataBinding.list1.removeOnScrollListener(scrollListeners[1]);
                mDataBinding.list1.scrollBy(dx, dy);
                mDataBinding.list1.addOnScrollListener(scrollListeners[1]);
            }
        };
        scrollListeners[1] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                mDataBinding.list2.removeOnScrollListener(scrollListeners[0]);
                mDataBinding.list2.scrollBy(dx, dy);
                mDataBinding.list2.addOnScrollListener(scrollListeners[0]);
            }
        };
        mDataBinding.list2.addOnScrollListener(scrollListeners[0]);
        mDataBinding.list1.addOnScrollListener(scrollListeners[1]);
        // 下拉刷新监听
        mDataBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshList();
            }
        });
        // 是否刷新监听
        mViewModel.mRefresh.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refresh) {
                mDataBinding.refresh.setRefreshing(refresh);
            }
        });
        // 下拉刷新&上拉加载刷新adapter
        mViewModel.mListsLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<CheckProfitListItemBean>>() {
            @Override
            public void onChanged(PagedList<CheckProfitListItemBean> checkProfitListItemBeans) {
                // 刷新左侧列表
                mViewModel.getLeftListAdapter().submitList(checkProfitListItemBeans);
                // 刷新右侧列表
                mViewModel.getRightListAdapter().submitList(checkProfitListItemBeans);
            }
        });
        // 列表筛选弹窗
        mViewModel.mShowSortTypeDialog.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Pair<CheckReviewSortKeyType, CheckReviewSortOption> sortType = mViewModel.getSortType();
                CheckReviewSortTypeDialog dialog = CheckReviewSortTypeDialog.getInstance(sortType.first, sortType.second);
                dialog.setOnChangeSelectListener((sortKeyType, sortFlagType) -> {
                    // 设置当前弹窗筛选类型
                    mViewModel.setSortType(new Pair<>(sortKeyType, sortFlagType));
                });
                dialog.show(getChildFragmentManager(), "CheckReviewSortTypeDialog");
            }
        });
        // 获取当前盘点任务损益单状态
        mViewModel.getCouGalStatus();
    }

    @Override
    protected void reload() {
        // 获取当前盘点任务损益单状态
        mViewModel.getCouGalStatus();
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_step_4;
    }
}
