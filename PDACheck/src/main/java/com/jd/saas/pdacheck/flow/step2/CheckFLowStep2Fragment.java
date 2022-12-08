package com.jd.saas.pdacheck.flow.step2;

import android.content.Context;
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
import com.jd.saas.pdacheck.databinding.CheckFlowStep2DataBinding;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;
import com.jd.saas.pdacheck.flow.step2.dialog.CheckEditMissedCheckSkuDialog;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.toast.MyToast;

import java.util.List;

/**
 * 盘点第二步：初盘核对fragment
 * 漏盘商品列表
 *
 * @author gouhetong
 */
public class CheckFLowStep2Fragment extends SimpleFragment {

    private CheckFlowStep2DataBinding mDataBinding;
    private CheckFlowStep2ViewModel mViewModel;
    private CheckMissedSkuListAdapter mAdapter;

    public static CheckFLowStep2Fragment newInstance(CheckListBean bean) {
        CheckFLowStep2Fragment fragment = new CheckFLowStep2Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void reload() {
        // do nothing
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this, CheckInject.injectViewModelFactory()).get(CheckFlowStep2ViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_step_2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置提交接口时的全局Loading加载框
        DialogBaseView dialogBaseView = new DialogBaseView(mContext,"");
        mViewModel.handleBaseNetUI(dialogBaseView);
        mAdapter = new CheckMissedSkuListAdapter(this::showEditNormalSkuDialog);
        mDataBinding.switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mViewModel.filterZeroQty.setValue(isChecked);
            mViewModel.refresh();
        });
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.setEmptyView(mDataBinding.layoutNoData);
        mDataBinding.swipeRefresh.setOnRefreshListener(() -> mViewModel.refresh());
        mViewModel.skuList.observe(getViewLifecycleOwner(), missedCheckSkuBeans -> mAdapter.setData(missedCheckSkuBeans));
        mViewModel.showToastEvent.observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) {
                String pop = msg.poll();
                if (pop != null) {
                    MyToast.show(pop, false);
                    mViewModel.showToastEvent.postValue(msg);
                }
            }
        });
        mViewModel.isRefresh.observe(getViewLifecycleOwner(), isRefresh -> mDataBinding.swipeRefresh.setRefreshing(isRefresh));
        CheckListBean mCheckListBean = (CheckListBean) getArguments().getSerializable("data");
        mViewModel.init(mCheckListBean);
    }

    private void showEditNormalSkuDialog(CheckMissedSkuBean skuBean) {
        if(mViewModel.mContentClickable.get()) {
            CheckEditMissedCheckSkuDialog instance = CheckEditMissedCheckSkuDialog.getInstance(skuBean);
            instance.setOnEnsureClickListener(this::onEditSkuEnsure);
            instance.setOnDismissListener(this::hideSoftInput);
            instance.show(getChildFragmentManager(), "editSku");
        }
    }

    private void hideSoftInput() {
        if (mDataBinding == null) {
            return;
        }
        mDataBinding.getRoot().postDelayed(() -> {
            View view = getView();
            Context context = getContext();
            if (view != null && context != null) {
                SoftInputUtil.hideSoftInput(view, context);
            }
        }, 50);
    }

    /**
     * 修改商品 后 的回调，用于更新当前页面的展示和向待提交的列表中添加信息
     * 对于组合商品 并不保存在提交列表中，而是拆分成子品保存到提交信息中
     *
     * @param bean 商品信息
     */
    private void onEditSkuEnsure(CheckMissedSkuBean bean) {
        if (bean == null) {
            return;
        }
        mAdapter.notifyItemChanged(bean);
        mViewModel.addSku2SubmitList(bean);
    }

    /**
     * 是否存在已输入的内容
     *
     * @return true已经存在待提交的内容
     */
    public boolean hasInput() {
        List<CheckMissedSkuBean> value = mViewModel.submitList.getValue();
        return value != null && !value.isEmpty();
    }
}
