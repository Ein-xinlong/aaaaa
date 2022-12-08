package com.example.pdalogin.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.pdalogin.R;
import com.example.pdalogin.databinding.LoginMainActivityDataBinding;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

/**
 * 登录fragment
 */
public class LoginMainFragment extends SimpleFragment {

    private LoginMainViewModel mViewModel;

    public static LoginMainFragment newInstance() {
        return new LoginMainFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        LoginMainActivityDataBinding dataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(LoginMainViewModel.class);
        dataBinding.setLoginVm(mViewModel);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DialogBaseView dialog = new DialogBaseView(mContext, R.string.login_ing);
        mViewModel.handleBaseNetUI(dialog);
    }

    @Override
    protected void reload() {
        // do nothing
    }

    @Override
    protected int getLayout() {
        return R.layout.login_fragment_login_main;
    }
}
