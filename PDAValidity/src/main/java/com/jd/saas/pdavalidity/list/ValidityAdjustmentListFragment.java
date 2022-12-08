package com.jd.saas.pdavalidity.list;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;
import com.jd.saas.pdavalidity.R;
import com.jd.saas.pdavalidity.databinding.ValidityAdjustmentListDataBinding;

import org.greenrobot.eventbus.Subscribe;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/2
 */
public class ValidityAdjustmentListFragment extends SimpleFragment {

    private ValidityAdjustmentListDataBinding mDataBinding;
    private ValidityAdjustmentListViewModel mViewModel;

    public static ValidityAdjustmentListFragment getInstance() {
        return new ValidityAdjustmentListFragment();
    }

    @Override
    protected void reload() {

    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(ValidityAdjustmentListViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 网络
        DialogBaseView baseView = new DialogBaseView(mContext, "");
        mViewModel.handleBaseNetUI(baseView);
        // 搜索列表list示例
        mDataBinding.rvSearch.setLayoutManager(mViewModel.getSearchLinearLayoutManager(mContext));
        mDataBinding.rvSearch.setAdapter(mViewModel.getSearchResultAdapter());
        mViewModel.mSearchListLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<SearchGoodBean>>() {
            @Override
            public void onChanged(PagedList<SearchGoodBean> searchGoodBeans) {
                mViewModel.getSearchResultAdapter().submitList(searchGoodBeans);
            }
        });
        // 详情列表list
        mDataBinding.validityList.setLayoutManager(mViewModel.getLinearLayoutManager(mContext));
        mDataBinding.validityList.setAdapter(mViewModel.getInventoryAdjustmentAdapter());
        mViewModel.mGoodDetailLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<SearchGoodBean>>() {
            @Override
            public void onChanged(PagedList<SearchGoodBean> searchGoodBeans) {
                mViewModel.getInventoryAdjustmentAdapter().submitList(searchGoodBeans);
            }
        });
        // 列表下拉刷新
        mDataBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshGoodDetailList();
            }
        });
        // 是否刷新监听
        mViewModel.mRefresh.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refresh) {
                mDataBinding.refresh.setRefreshing(refresh);
            }
        });
        // 软键盘监听
        mViewModel.mHideKeyBoard.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hide) {
                if (hide) {
                    SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
                }
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
                    // 搜索列表
                    mViewModel.refreshSearchList();
                    // 清除搜索框焦点
                    mDataBinding.etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
        // 接收扫码广播
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, code -> mViewModel.setScanResult(code));
    }

    @Override
    protected int getLayout() {
        return R.layout.validity_adjustment_fragnemt;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mViewModel.SCREEN_REQUEST_CODE) {
            if (data != null) {
                // 扫描后搜索商品-二维码扫描
                String screenResult = data.getStringExtra(Constant.CODED_CONTENT);
                mViewModel.setScanResult(screenResult);
            }
        }
    }

    @Subscribe
    public void refreshList(EventBean bean) {
        if (bean.getType() == EventBean.EVENT_REFRESH_LIST) {
            // 效期的调整页面提交成功后，需要通知刷新当前商品详情
            mViewModel.refreshGoodDetailList();
        }
    }
}
