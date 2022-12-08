package com.jd.saas.padinventory.stock;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.InventoryStockDataBinding;
import com.jd.saas.padinventory.stock.router.InventoryStockRouterPath;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;

import org.greenrobot.eventbus.Subscribe;

/**
 * 库存调整ui-fragment
 *
 * @author majiheng
 */
public class InventoryStockFragment extends SimpleFragment {

    private InventoryStockDataBinding mDataBinding;
    private InventoryStockViewModel mViewModel;


    public static InventoryStockFragment newInstance() {
        return new InventoryStockFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(InventoryStockViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化rv
        mDataBinding.rvOddNumbers.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.rvOddNumbers.setAdapter(mViewModel.getAdapter());
        mViewModel.mSearchListLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<InventoryStockRepostBean.ItemListBean>>() {
            @Override
            public void onChanged(PagedList<InventoryStockRepostBean.ItemListBean> itemListBeans) {
                mViewModel.getAdapter().submitList(itemListBeans);
            }
        });
        // 下拉刷新
        mDataBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshList();
            }
        });
        // 隐藏键盘
        mViewModel.mHideKeyBoard.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hide) {
                if (hide) {
                    SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
                }
            }
        });
        // 是否loading标记
        mViewModel.mLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                mDataBinding.refreshLayout.setRefreshing(loading);
            }
        });
        // 搜索框监听enter
        mDataBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    // 搜索
                    mViewModel.search(mDataBinding.etSearch.getText().toString());
                    // 清除搜索框焦点
                    mDataBinding.etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
        // 接收扫码广播
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, code -> {
            // 搜索
            mViewModel.search(code);
        });
    }

    @Override
    protected void reload() {
        // do nothing
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InventoryStockRouterPath.REQUEST_CODE_SCAN) {
            if (data != null) {
                String result = data.getStringExtra(Constant.CODED_CONTENT);
                // 搜索
                mViewModel.search(result);
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.inventory_stock_layout;
    }

    @Subscribe
    public void refreshList(EventBean bean) {
        if (bean.getType() == EventBean.EVENT_REFRESH_LIST) {
            // 提交成功后，需要通知刷新当前列表
            mViewModel.refreshList();
        }
    }
}
