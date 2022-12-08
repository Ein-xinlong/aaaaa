package com.example.pdalogin.enterprise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pdalogin.R;

import com.example.pdalogin.databinding.LoginChooesEnterpriseDataBinding;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

/***
 * 选择企业fragment
 *
 * @author majiheng
 */

public class LoginChooseEnterpriseFragment extends SimpleFragment {

    private LoginChooesEnterpriseDataBinding mDataBinding;
    private LoginChooseEnterpriseViewModel mViewModel;

    public  static LoginChooseEnterpriseFragment newInstance(){
        return new LoginChooseEnterpriseFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider(this).get(LoginChooseEnterpriseViewModel.class);
        mDataBinding.setChooesenterpriseVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.handleBaseNetUI(this);
        // 悬浮加载框
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
        mDataBinding.chooesrecyclerview.setLayoutManager(mViewModel.getLinearLayoutManager(mContext));
        mDataBinding.chooesrecyclerview.setAdapter(mViewModel.getLoginChooseEnterpriseAdapter());
        mViewModel.requestData();
    }

    @Override
    protected void reload() {
        // 点击重试
        mViewModel.requestData();
    }


    @Override
    protected int getLayout() {
        return R.layout.login_fragment_chooes_pries;
    }
}
