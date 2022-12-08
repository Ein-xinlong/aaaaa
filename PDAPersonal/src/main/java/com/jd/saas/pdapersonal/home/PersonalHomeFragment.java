package com.jd.saas.pdapersonal.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.dialog.DialogBaseView;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.databinding.PersonalActivityHomeBinding;

/**
 * 个人中心Fragment
 *
 * @author mengmeng
 */
public class PersonalHomeFragment extends SimpleFragment {

    private PersonalActivityHomeBinding mDataBinding;
    private PersonalHomeViewModel mViewModel;

    public static PersonalHomeFragment getInstance(){
        return new PersonalHomeFragment();
    }
    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider(this).get(PersonalHomeViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 透明状态栏
        ScreenUtil.setTranslucentStatusBar(getActivity());
        // 抽屉距离顶部
        mDataBinding.llRoot.setPadding(0, ScreenUtil.getStatusBarHeight(mContext),0,0);
        // 加载框
        DialogBaseView dialog = new DialogBaseView(mContext, R.string.personal_logout_ing);
        mViewModel.handleBaseNetUI(dialog);
        mDataBinding.mDrawrLayout.openDrawer(GravityCompat.END,true);
        //抽屉监听事件
        mDataBinding.mDrawrLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int arg0) {
            }
            @Override
            public void onDrawerSlide(View arg0, float arg1) { }

            @Override
            public void onDrawerOpened(View arg0) { }

            @Override
            public void onDrawerClosed(View arg0) {
                //关闭当前ui
                AppManager.getInstance().finishActivity(PersonalHomeActivity.class);
            }
        });
        //是否关闭抽屉监听，true时关闭
        mViewModel.liveDataDrawer.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    mDataBinding.mDrawrLayout.closeDrawer(GravityCompat.END);
                }
            }
        });
        // 初始化ui
        mViewModel.initUI(mContext);
    }

    @Override
    protected void reload() {

    }

    @Override
    protected int getLayout() {
        return R.layout.personal_frament_home;
    }
}