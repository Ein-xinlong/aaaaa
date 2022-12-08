package com.jd.saas.pdacheck.check;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckGoodsCheckDataBinding;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;

/**
 * 商品盘点Fragment
 *
 * @author majiheng
 */
public class CheckGoodsCheckFragment extends SimpleFragment {

    private CheckGoodsCheckDataBinding mDataBinding;
    private CheckGoodsCheckViewModel mViewModel;

    public static CheckGoodsCheckFragment newInstance() {
        return new CheckGoodsCheckFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider((CheckGoodsCheckActivity) mContext).get(CheckGoodsCheckViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 页面详情加载
        mViewModel.handleBaseNetUI(this);
        // 悬浮Loading框
        ProgressDialog dialog = new ProgressDialog(mContext);
        // 刷新ui
        CheckListBean bean = (CheckListBean) getActivity().getIntent().getExtras().get("data");
        mViewModel.refreshUI(bean);
        // 监听回车键
        mDataBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    // 查询商品信息
                    mViewModel.getGood(mContext);
                    mDataBinding.etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
        // 监听是否弹出Loading框
        mViewModel.mShowProgress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if(show) {
                    dialog.show();
                }else {
                    dialog.dismiss();
                }
            }
        });
        // 接收扫码广播
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, code -> {
            // 获取商品详情
            mViewModel.getGoodFromScreen(mContext, code);
        });
        // 初始化箱柜商品列表
        mDataBinding.rvBoxProducts.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.rvBoxProducts.setAdapter(mViewModel.getBoxProductAdapter());
        // 是否存在盘点记录弹窗提示监听
        mViewModel.mShowExitDialog.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showExitDialog) {
                if(showExitDialog) {
                    new SimpleAlertDialog.Builder(mContext, R.string.check_warning)
                            .setPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 清空全局盘点记录
                                    mViewModel.exit();
                                }
                            }).build().show();
                }
            }
        });
    }

    @Override
    protected void onResumeWork() {
        super.onResumeWork();
        // 如果当前Fragment没有被销毁，重新回到当前页面，就重新执行下面逻辑！
        // 获取预盘点单号
        mViewModel.getConNo();
    }

    @Override
    protected void reload() {
        mViewModel.getConNo();
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_goods_check;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mViewModel.SCREEN_REQUEST_CODE) {
            if (data != null) {
                String upcCode = data.getStringExtra(Constant.CODED_CONTENT);
                // 获取商品详情
                mViewModel.getGoodFromScreen(mContext, upcCode);
            }
        }
    }
}
