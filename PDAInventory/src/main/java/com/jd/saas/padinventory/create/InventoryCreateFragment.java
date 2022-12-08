package com.jd.saas.padinventory.create;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.databinding.InventoryFragmentCreateBinding;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdacommon.utils.EditTextHelper;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;

import org.jetbrains.annotations.NotNull;

/**
 * 新建调整单Fragment
 *
 * @author ext.mengmeng
 */
public class InventoryCreateFragment extends SimpleFragment {

    private InventoryFragmentCreateBinding mDataBinding;
    private InventoryCreateViewModel mViewModel;

    public static InventoryCreateFragment getInstance() {
        return new InventoryCreateFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider((InventoryCreateActivity) mContext).get(InventoryCreateViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 网络ui
        DialogBaseView dialogBaseView = new DialogBaseView(mContext, "");
        mViewModel.handleBaseNetUI(dialogBaseView);
        // 设置输入框限制
        EditTextHelper.INSTANCE.limitDecimalPlaces(3, mDataBinding.etNumber);
        // 初始化搜索列表
        mDataBinding.rvSearch.setLayoutManager(mViewModel.getSearchLinearLayoutManager(mContext));
        mDataBinding.rvSearch.setAdapter(mViewModel.getSearchResultAdapter());
        mViewModel.mSearchListLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<SearchGoodBean>>() {
            @Override
            public void onChanged(PagedList<SearchGoodBean> searchGoodBeans) {
                mViewModel.getSearchResultAdapter().submitList(searchGoodBeans);
            }
        });
        // 监听回车键
        mDataBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    // 隐藏键盘
                    SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
                    // 查询商品信息
                    mViewModel.mImport.set(mDataBinding.etSearch.getText().toString());
                    // 清除搜索框焦点
                    mDataBinding.etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
        // 键盘隐藏监听
        mViewModel.mHideKeyBoard.observe(getViewLifecycleOwner(), hide -> {
            if (hide) {
                SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
            }
        });
        mDataBinding.radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1) {
                    mViewModel.lossReport();
                } else if (checkedId == R.id.radio2) {
                    mViewModel.reportOverflow();
                }

            }
        });
        // 接收扫码广播
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, code -> {
            mViewModel.mImport.set(code);
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.inventory_fragment_create;
    }

    @Override
    protected void reload() {
        // do nothing
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InventoryCreateRouterPath.REQUEST_CODE_SCAN) {
            if (data != null) {
                String result = data.getStringExtra(Constant.CODED_CONTENT);
                //扫码后，显示商品detail
                if (!TextUtils.isEmpty(result)) {
                    mViewModel.mImport.set(result);
                }
            }
        }
    }
}