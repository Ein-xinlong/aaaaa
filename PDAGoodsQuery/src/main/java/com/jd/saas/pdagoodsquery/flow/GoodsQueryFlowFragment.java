package com.jd.saas.pdagoodsquery.flow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.databinding.GoodsQueryFlowDataBinding;
import com.jd.saas.pdagoodsquery.flow.adapter.GoodsQueryFlowListAdapter;

/**
 * 库存流水ui-fragment
 *
 * @author majiheng
 */
public class GoodsQueryFlowFragment extends SimpleFragment {

    private GoodsQueryFlowDataBinding mDataBinding;
    private GoodsQueryFlowViewModel mViewModel;

    private GoodsQueryFlowListAdapter mAdapter;

    public static GoodsQueryFlowFragment newInstance() {
        return new GoodsQueryFlowFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        SearchResultBean mInfoResponseData = (SearchResultBean) getActivity().getIntent().getSerializableExtra("bean");

        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(GoodsQueryFlowViewModel.class);
        mViewModel.handleBaseNetUI(this);
        mViewModel.mDetailBean = mInfoResponseData;
        mDataBinding.setVm(mViewModel);

        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.swipeRefreshLayout.setOnRefreshListener(() -> mViewModel.refreshSearchList());
        mDataBinding.swipeRefreshLayout.setRefreshing(true);
        // 初始化rv
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new GoodsQueryFlowListAdapter();
        mDataBinding.rvList.setAdapter(mAdapter);
        mViewModel.mFlowLiveData.observe(getViewLifecycleOwner(), searchGoodBeans -> mAdapter.submitList(searchGoodBeans));
        mViewModel.mRefresh.observe(getViewLifecycleOwner(), aBoolean -> mDataBinding.swipeRefreshLayout.setRefreshing(aBoolean));

    }


    @Override
    protected void reload() {
        mViewModel.refreshSearchList();
    }

    @Override
    protected int getLayout() {
        return R.layout.goods_query_flow_layout;
    }


}
