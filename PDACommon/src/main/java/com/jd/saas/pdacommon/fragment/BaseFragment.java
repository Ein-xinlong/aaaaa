package com.jd.saas.pdacommon.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.log.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 基础Fragment
 *
 * @author majiheng
 */
public abstract class BaseFragment extends Fragment {

    private final String TAG = BaseFragment.class.getSimpleName();
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Logger.d(TAG, "##onAttach##");
        mContext = context;
    }

    protected abstract int getLayout();

    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onCreateContentView(inflater, container);
    }

    protected void finish() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    protected void runOnUiThread(Runnable runnable) {
        ((Activity) mContext).runOnUiThread(runnable);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusManager.register(this);
        Logger.d(TAG, "##onCreate##");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(TAG, "##onViewCreated##");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(TAG, "##onActivityCreated##");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(TAG, "##onStart##");
    }

    private boolean mHidden;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mHidden = hidden;
        if (mHidden) {
            onPauseWork();
        } else {
            onResumeWork();
        }
    }

    /**
     * 当通过add的方式添加fragment  hide和show的方式 应该在这里处理逻辑
     */
    protected void onResumeWork() {
        Logger.d(TAG, "##onResumeWork##");

    }

    protected void onPauseWork() {
        Logger.d(TAG, "##onPauseWork##");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG, "##onResume##");
        if (!mHidden) {
            onResumeWork();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(TAG, "##onPause##");
        if (!mHidden) {
            onPauseWork();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(TAG, "##onStop##");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG, "##onDestroyView##");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusManager.unregister(this);
        Logger.d(this.getClass().getSimpleName(), "##onDestroy##");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(this.getClass().getSimpleName(), "##onDetach##");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(EventBean eventBean) {

    }
}
