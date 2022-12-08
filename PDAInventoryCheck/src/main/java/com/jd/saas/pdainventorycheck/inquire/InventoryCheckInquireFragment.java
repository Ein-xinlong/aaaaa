package com.jd.saas.pdainventorycheck.inquire;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;
import com.jd.saas.pdacommon.zxing.router.ZxingRouterPath;
import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.databinding.InventoryCheckInquireDataBinDing;
import com.jd.saas.pdainventorycheck.router.InventoryCheckRouterPath;

import org.jetbrains.annotations.NotNull;

/**
 * 库存查询页面fragment
 *
 * @author: ext.anxinlong
 * @date: 2021/7/13
 */
public class InventoryCheckInquireFragment extends SimpleFragment {

    private InventoryCheckInquireViewModel mViewModel;
    private InventoryCheckInquireDataBinDing mDataBinding;

    public static InventoryCheckInquireFragment getInstance() {
        return new InventoryCheckInquireFragment();
    }


    @Override
    protected View onCreateContentView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(InventoryCheckInquireViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressDialog dialog = new ProgressDialog(getContext());
        mDataBinding.rvSearch.setLayoutManager(mViewModel.getSearchLinearLayoutManager(mContext));
        mDataBinding.rvSearch.setAdapter(mViewModel.getSearchResultAdapter());
        mViewModel.mSearchListLiveData.observe(getViewLifecycleOwner(), searchGoodBeans ->
                mViewModel.getSearchResultAdapter().submitList(searchGoodBeans));
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, code -> {
            mDataBinding.etSearch.setText(code);
            // 查询商品信息
            mViewModel.refreshSearchList();
        });
        // 监听回车键
        mDataBinding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_SEARCH
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                // 隐藏键盘
                SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
                mDataBinding.etSearch.clearFocus();
                // 查询商品信息
                mViewModel.refreshSearchList();
                return true;
            }
            return false;
        });
        mViewModel.mProgressBarHide.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!mViewModel.mProgressBarHide.get()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.show();
                        }
                    });
                }
            }
        });
        mDataBinding.setOnScanBtnClick(v ->
                RouterClient.getInstance().forward(getContext(), ZxingRouterPath.PATH_ZXING, new Bundle(), InventoryCheckRouterPath.REQUEST_CODE_SCAN));
    }

    @Override
    protected void reload() {

    }

    @Override
    protected int getLayout() {
        return R.layout.inventorycheck_fragment_main;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InventoryCheckRouterPath.REQUEST_CODE_SCAN) {
            if (data != null) {
                String mZXingResult = data.getStringExtra(Constant.CODED_CONTENT);
                //扫码后，显示商品detail
                if (!TextUtils.isEmpty(mZXingResult)) {
                    mDataBinding.etSearch.setText(mZXingResult);
                    // 查询商品信息
                    mViewModel.refreshSearchList();
                }
            }
        }
    }
}
