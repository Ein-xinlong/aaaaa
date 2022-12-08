package com.jd.saas.pdapersonal.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.databinding.PersonalStoreActivityDataBinding;

public class PersonalStoreFragment extends SimpleFragment {

    private PersonalStoreActivityDataBinding mDataBinding;
    private PersonalStoreViewModel mViewModel;
    public static PersonalStoreFragment newInstance(){
        return new PersonalStoreFragment();
    }


    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(PersonalStoreViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        mDataBinding.recyclerview.setOrientation(RecyclerView.VERTICAL);
        mDataBinding.recyclerview.setEmptyView(mDataBinding.reStore);
        mDataBinding.recyclerview.setAdapter(mViewModel.getStoreListAdapter(mContext));
        Bundle bundle = getActivity().getIntent().getExtras();
        mViewModel.getShopList(bundle);
        // 下拉刷新
        mDataBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getShopList(bundle);
            }
        });
        // 是否刷新监听
        mViewModel.mRefresh.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refresh) {
                mDataBinding.refresh.setRefreshing(refresh);
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.personal_fragment_store;
    }

    @Override
    protected void reload() {

    }
}
