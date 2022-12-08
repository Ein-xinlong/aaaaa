package com.jd.saas.pdacommon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.net.IBaseView;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.toast.MyToast;

/**
 * 基础fragment
 *
 * @author majiheng
 */
public abstract class SimpleFragment extends BaseFragment implements IBaseView {

    private final String TAG = SimpleFragment.class.getSimpleName();

    protected View mBaseView;
    protected FrameLayout mContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "##onCreateView##");
        mBaseView = inflater.inflate(R.layout.fragment_base, container, false);
        mContent = mBaseView.findViewById(R.id.fl_fragment_content);
        mContent.addView(onCreateContentView(inflater, mContent));
        mBaseView.findViewById(R.id.tv_reload).setOnClickListener(v -> {
            reload();
        });
        return mBaseView;
    }

    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void loading(boolean show) {
        runOnUiThread(() -> {
            mBaseView.findViewById(R.id.pb_loading).setVisibility(show ? View.VISIBLE : View.GONE);
            mBaseView.findViewById(R.id.fl_fragment_content).setVisibility(show ? View.GONE : View.VISIBLE);
            mBaseView.findViewById(R.id.tv_reload).setVisibility(View.GONE);
        });
    }

    @Override
    public void showNetError(NetError error) {
        // toast显示错误信息
        MyToast.show(error.getMsg(), true);
        runOnUiThread(() -> {
            mBaseView.findViewById(R.id.tv_reload).setVisibility(View.VISIBLE);
            mBaseView.findViewById(R.id.fl_fragment_content).setVisibility(View.GONE);
            mBaseView.findViewById(R.id.pb_loading).setVisibility(View.GONE);
        });
    }

    protected abstract void reload();
}
