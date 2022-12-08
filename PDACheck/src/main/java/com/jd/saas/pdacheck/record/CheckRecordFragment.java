package com.jd.saas.pdacheck.record;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckFragmentRecordBinding;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

/**
 * 盘点记录
 *
 * @author majiheng
 */
public class CheckRecordFragment extends SimpleFragment {

    private CheckFragmentRecordBinding mBinding;
    private CheckRecordViewModel mViewModel;

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(CheckRecordViewModel.class);
        mBinding.setVm(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DialogBaseView dialogBaseView = new DialogBaseView(mContext,R.string.check_record_confirm_ing);
        mViewModel.handleBaseNetUI(dialogBaseView);
        mBinding.recyclerview.setOrientation(RecyclerView.VERTICAL);
        mBinding.recyclerview.setEmptyView(mBinding.tvNoData);
        mBinding.recyclerview.setAdapter(mViewModel.getListAdapter());
        // 解析bundle，刷新ui
        Bundle bundle = getActivity().getIntent().getExtras();
        mViewModel.getBundleContent(bundle);
    }

    public static CheckRecordFragment newInstance() {
        return new CheckRecordFragment();
    }

    @Override
    protected void reload() {
        // this page do nothing here,no net request.
    }

    @Override
    protected int getLayout() {
        return R.layout.check_fragment_record;
    }
}