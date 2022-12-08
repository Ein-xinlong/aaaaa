package com.jd.saas.pdacheck.newtask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckNewTaskDataBinding;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * 新建盘点任务fragment
 *
 * @author ext.anxinlong
 */
public class CheckNewTaskFragment extends SimpleFragment {
    private CheckNewTaskViewModel mViewModel;
    private CheckNewTaskDataBinding mDataBinding;

    public static CheckNewTaskFragment newInstance() {
        return new CheckNewTaskFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(CheckNewTaskViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.handleBaseNetUI(this);
        ProgressDialog loadingDialog = new ProgressDialog(mContext);
        mViewModel.isShowDialog.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if (show) {
                    loadingDialog.show();
                } else {
                    loadingDialog.dismiss();
                }
            }
        });
        getData();

    }

    /**
     * 获取列表页传过来的数据
     */
    private void getData() {
        if (null != getActivity().getIntent().getExtras()) {
            CheckListBean bean = (CheckListBean) getActivity().getIntent().getExtras().get("data");
            mViewModel.updateStatus(bean);
        }
    }


    /**
     * 获得指定分类页返回的结果
     */
    @Subscribe
    public void onEvent(EventBean bean) {
        if (bean.getType() == EventBean.EVENT_CHECK_NEW_TASK) {
            ArrayList<String> selectIds = (ArrayList<String>) bean.getData();
            mViewModel.setCatList(selectIds);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.check_new_task_fragment;
    }

    @Override
    protected void reload() {
        getData();
    }


}
