package com.jd.saas.pdavalidity.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdavalidity.R;
import com.jd.saas.pdavalidity.databinding.ValidityAdjustDetailDataBinding;

/**
 * 效期调整ui-fragment
 *
 * @author majiheng
 */
public class ValidityAdjustFragment extends SimpleFragment {

    private ValidityAdjustViewModel mViewModel;

    public static ValidityAdjustFragment newInstance() {
        return new ValidityAdjustFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        ValidityAdjustDetailDataBinding dataBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider(this).get(ValidityAdjustViewModel.class);
        dataBinding.setVm(mViewModel);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置网络
        DialogBaseView baseView = new DialogBaseView(mContext,"");
        mViewModel.handleBaseNetUI(baseView);
        // 获取详情bean
        SearchGoodBean data = (SearchGoodBean) getActivity().getIntent().getSerializableExtra("data");
        // 刷新ui
        mViewModel.refreshUI(data);
    }

    @Override
    protected void reload() {
    }

    @Override
    protected int getLayout() {
        return R.layout.validity_adjust_detail_layout;
    }
}
