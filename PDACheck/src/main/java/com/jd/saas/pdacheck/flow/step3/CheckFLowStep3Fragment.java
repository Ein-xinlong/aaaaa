package com.jd.saas.pdacheck.flow.step3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacheck.CheckInject;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckFlowStep3DataBinding;
import com.jd.saas.pdacheck.flow.CheckFlowSwitchEventBusBean;
import com.jd.saas.pdacheck.flow.step3.adapter.CheckListOneAdapter;
import com.jd.saas.pdacheck.flow.step3.adapter.CheckListTwoAdapter;
import com.jd.saas.pdacheck.flow.step3.dialog.CheckReviewEditSkuPreOrderDialog;
import com.jd.saas.pdacheck.flow.step3.dialog.CheckReviewSortTypeDialog;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortKeyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortOption;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.toast.MyToast;

/**
 * 盘点第三步：复盘修改fragment
 *
 * @author majiheng
 */
public class CheckFLowStep3Fragment extends SimpleFragment {

    private CheckFlowStep3ViewModel mViewModel;
    private CheckFlowStep3DataBinding mDataBinding;

    public static CheckFLowStep3Fragment newInstance(CheckListBean bean) {
        CheckFLowStep3Fragment fragment = new CheckFLowStep3Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this, CheckInject.injectViewModelFactory()).get(CheckFlowStep3ViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckListBean mCheckListBean = (CheckListBean) getArguments().getSerializable("data");
        mViewModel.init(mCheckListBean);
        CheckListOneAdapter adapter1 = new CheckListOneAdapter(bean -> mViewModel.editSku.postValue(bean));
        CheckListTwoAdapter adapter2 = new CheckListTwoAdapter(bean -> mViewModel.editSku.postValue(bean));
        mDataBinding.swipeRefresh.setOnRefreshListener(() -> mViewModel.refresh());
        mDataBinding.leftList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.rightList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.leftList.setAdapter(adapter1);
        mDataBinding.rightList.setAdapter(adapter2);

        RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];
        scrollListeners[0] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDataBinding.leftList.removeOnScrollListener(scrollListeners[1]);
                mDataBinding.leftList.scrollBy(dx, dy);
                mDataBinding.leftList.addOnScrollListener(scrollListeners[1]);
            }
        };
        scrollListeners[1] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                mDataBinding.rightList.removeOnScrollListener(scrollListeners[0]);
                mDataBinding.rightList.scrollBy(dx, dy);
                mDataBinding.rightList.addOnScrollListener(scrollListeners[0]);
            }
        };
        mDataBinding.rightList.addOnScrollListener(scrollListeners[0]);
        mDataBinding.leftList.addOnScrollListener(scrollListeners[1]);

        mViewModel.skuList.observe(getViewLifecycleOwner(), checkReviewSkuBeans -> {
            adapter1.submitList(checkReviewSkuBeans);
            adapter2.submitList(checkReviewSkuBeans);
        });
        mViewModel.changedFilterType.observe(getViewLifecycleOwner(), value -> mViewModel.refresh());
        mViewModel.showToastEvent.observe(getViewLifecycleOwner(), msg -> MyToast.show(msg, false));
        mViewModel.isRefresh.observe(getViewLifecycleOwner(), isRefresh -> mDataBinding.swipeRefresh.setRefreshing(isRefresh));
        mViewModel.editSku.observe(getViewLifecycleOwner(), bean -> {
            if (bean != null && mViewModel.mContentClickable.get()) {
                CheckReviewEditSkuPreOrderDialog dialog = CheckReviewEditSkuPreOrderDialog.getInstance(mViewModel.getTaskNo(), bean);
                dialog.setOnSubmitSuccessListener(() -> mViewModel.refresh());
                dialog.setOnDismissListener(() -> {
                    if (mDataBinding == null) {
                        return;
                    }
                    mDataBinding.getRoot().postDelayed(() -> {
                        View view1 = getView();
                        Context context = getContext();
                        if (view1 != null && context != null) {
                            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(view1.getWindowToken(), 0);
                        }
                    }, 50);
                });
                dialog.show(getChildFragmentManager(), "CheckReviewEditSkuPreOrderDialog");
            }
        });
        mViewModel.showSortTypeDialogEvent.observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                mViewModel.showSortTypeDialogEvent.setValue(null);
                Pair<CheckReviewSortKeyType, CheckReviewSortOption> sortType = mViewModel.getSortType();
                CheckReviewSortTypeDialog dialog = CheckReviewSortTypeDialog.getInstance(sortType.first, sortType.second);
                dialog.setOnChangeSelectListener((sortKeyType, sortFlagType) -> {
                    mViewModel.setSortType(new Pair<>(sortKeyType, sortFlagType));
                    mViewModel.refresh();
                });
                dialog.show(getChildFragmentManager(), "CheckReviewSortTypeDialog");
            }
        });
        mViewModel.goNextEvent.observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                mViewModel.goNextEvent.setValue(null);
                EventBean<CheckFlowSwitchEventBusBean> eventBean = new EventBean<>();
                eventBean.setType(EventBean.EVENT_CHECK_SWITCH_TABS);
                CheckFlowSwitchEventBusBean data = new CheckFlowSwitchEventBusBean();
                data.setTab(4);
                data.setCurrentNode(CheckTaskNodeEnum.PROFIT);
                data.setTargetNode(CheckTaskNodeEnum.PROFIT);
                eventBean.setData(data);
                EventBusManager.post(eventBean);
            }
        });
    }


    @Override
    protected void reload() {
        // do nothing
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_step_3;
    }
}
