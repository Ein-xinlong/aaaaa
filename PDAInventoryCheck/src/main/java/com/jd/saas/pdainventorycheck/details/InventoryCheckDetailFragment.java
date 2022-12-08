package com.jd.saas.pdainventorycheck.details;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.databinding.InventorycheckFragmentDetailsBinding;
import com.jd.saas.pdainventorycheck.details.adapter.InventoryCheckFlowAdapter;
import com.jd.saas.pdainventorycheck.details.adapter.InventoryCheckLocationAdapter;
import com.jd.saas.pdainventorycheck.utils.TabUtil;

/**
 * 库位查询
 *
 * @author ext.mengmeng
 */
public class InventoryCheckDetailFragment extends SimpleFragment {
    private InventorycheckFragmentDetailsBinding mDataBinding;
    private InventoryCheckDetailsViewModel mViewModel;
    private InventoryCheckLocationAdapter mLocationAdapter;
    private InventoryCheckFlowAdapter mFlowAdapter;

    public static InventoryCheckDetailFragment newInstance() {
        return new InventoryCheckDetailFragment();
    }

    @Override
    protected void reload() {
        mViewModel.refreshLocationList();
        mViewModel.refreshFlowList();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container) {
        mViewModel = new ViewModelProvider(this).get(InventoryCheckDetailsViewModel.class);
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        SearchResultBean mSearchGoodsBean = (SearchResultBean) getActivity().getIntent().getSerializableExtra("bean");
        mViewModel.init(mSearchGoodsBean);
        //列表下拉刷新-流水
        mDataBinding.refreshFlow.setOnRefreshListener(() -> mViewModel.refreshFlowList());
        mViewModel.mFlowRefresh.observe(getViewLifecycleOwner(),
                aBoolean -> mDataBinding.refreshFlow.setRefreshing(aBoolean));
        //列表下拉刷新-库位
        mDataBinding.refreshLocation.setOnRefreshListener(() -> mViewModel.refreshLocationList());
        mViewModel.mLocationRefresh.observe(getViewLifecycleOwner(),
                aBoolean -> mDataBinding.refreshLocation.setRefreshing(aBoolean));
        //Paging库加载数据-库位
        mViewModel.mLocationLiveData.observe(getViewLifecycleOwner(),
                inventoryCheckLocationBeans -> mLocationAdapter.submitList(inventoryCheckLocationBeans));
        //Paging库加载数据-流水
        mViewModel.mFlowLiveData.observe(getViewLifecycleOwner(),
                inventoryCheckFlowBeans -> mFlowAdapter.submitList(inventoryCheckFlowBeans));

    }

    private void initView() {
        //库位加载RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.rvLocationInfo.setLayoutManager(layoutManager);
        mLocationAdapter = new InventoryCheckLocationAdapter();
        mDataBinding.rvLocationInfo.setAdapter(mLocationAdapter);
        //流水加载RecyclerView
        LinearLayoutManager flowLayoutManager = new LinearLayoutManager(getContext());
        flowLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mDataBinding.rvFlowInfo.setLayoutManager(flowLayoutManager);
        mFlowAdapter = new InventoryCheckFlowAdapter();
        mDataBinding.rvFlowInfo.setAdapter(mFlowAdapter);
        TabLayout tableLayout = mDataBinding.tableLayout;
        //tabLayout初始化、监听事件
        tableLayout.addTab(TabUtil.createTab(tableLayout, R.string.inventory_check_info, true));
        tableLayout.addTab(TabUtil.createTab(tableLayout, R.string.inventory_check_flow_info, false));
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabUtil.selectTextLargen(tab);
                switch (tab.getPosition()) {
                    case 0:
                        mViewModel.showFlowOrLocation.set(false);
                        break;
                    case 1:
                        mViewModel.showFlowOrLocation.set(true);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabUtil.unSelectTextNormal(tab);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.inventorycheck_fragment_details;
    }
}