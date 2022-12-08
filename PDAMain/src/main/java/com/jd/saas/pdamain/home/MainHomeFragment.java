package com.jd.saas.pdamain.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.databinding.MainHomeActivityDataBinding;
import com.jd.saas.pdamain.home.bean.MainShopListBean;

import org.greenrobot.eventbus.Subscribe;

/**
 * 首页ui承载
 *
 * @author majiheng
 */
public class MainHomeFragment extends SimpleFragment {

    private MainHomeActivityDataBinding mDataBinding;
    private MainHomeViewModel mViewModel;

    public static MainHomeFragment newInstance() {
        return new MainHomeFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(MainHomeViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 透明状态栏
        ScreenUtil.setTranslucentStatusBar(getActivity());
        // toolbar距离顶部
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mDataBinding.bar.getLayoutParams();
        params.setMargins(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
        // 菜单列表初始化
        mDataBinding.rvMenu.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.rvMenu.setAdapter(mViewModel.getMenuListAdapter());
        // 请求网络
        mViewModel.handleBaseNetUI(this);
        mViewModel.getShopInLocalOrRemote(mContext);
        // 获取菜单列表
        mViewModel.getMenus(mContext);
    }

    @Override
    protected void reload() {
        // 点击重试按钮的回调
        // 获取门店列表信息
        mViewModel.getShopInLocalOrRemote(mContext);
        // 获取菜单列表
        mViewModel.getMenus(mContext);
        // 获取租户配置信息
        mViewModel.getConfig();
    }

    /**
     * 接受门店页面传过来的数据
     */
    @Subscribe
    public void onEvent(EventBean bean) {
        if (bean.getType() == EventBean.HOME_STORE_TEXT) {
            MainShopListBean evBean = (MainShopListBean) bean.getData();
            mViewModel.updateUserData(evBean);
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.main_activity_home;
    }

    @Override
    protected void onResumeWork() {
        super.onResumeWork();
        // 每次页面切回来时都重启「拣货」消息轮询
        mViewModel.restartPickingNotification();
        // 检查app更新
        mViewModel.appVersionCheck();
        // 获取租户配置信息
        mViewModel.getConfig();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止「拣货」消息轮询
        mViewModel.stopPickingNotification();
    }
}
