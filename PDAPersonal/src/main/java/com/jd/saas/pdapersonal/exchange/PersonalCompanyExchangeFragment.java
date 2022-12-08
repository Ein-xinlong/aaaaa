package com.jd.saas.pdapersonal.exchange;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.databinding.PersonalCompanyExchangeDataBinding;

/**
 * 企业切换ui-fragment
 *
 * @author majiheng
 */
public class PersonalCompanyExchangeFragment extends SimpleFragment {

    private PersonalCompanyExchangeDataBinding mDataBinding;
    private PersonalCompanyExchangeViewModel mViewModel;

    public static PersonalCompanyExchangeFragment newInstance() {
        return new PersonalCompanyExchangeFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider(this).get(PersonalCompanyExchangeViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.handleBaseNetUI(this);
        mDataBinding.rvCompany.setLayoutManager(mViewModel.getLinearLayoutManager(mContext));
        mDataBinding.rvCompany.setAdapter(mViewModel.getCompanyExchangeListAdapter());
        mViewModel.requestData();
    }

    @Override
    protected void reload() {
        // 点击重试
        mViewModel.requestData();
    }

    @Override
    protected int getLayout() {
        return R.layout.personal_fragment_company_exchange;
    }
}
