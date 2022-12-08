package com.jd.saas.pdacheck.flow;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.databinding.CheckFlowMainDataBinding;
import com.jd.saas.pdacheck.flow.step1.CheckFlowStep1Fragment;
import com.jd.saas.pdacheck.flow.step2.CheckFLowStep2Fragment;
import com.jd.saas.pdacheck.flow.step3.CheckFLowStep3Fragment;
import com.jd.saas.pdacheck.flow.step4.CheckFLowStep4Fragment;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.activity.BaseActivity;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.toolbar.NormalTitleBar;
import com.jingdong.amon.router.annotation.JDRouteUri;

import org.greenrobot.eventbus.Subscribe;

/**
 * 盘点流程框架activity
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = CheckRouterPath.CHECK_MAIN, path = CheckRouterPath.CHECK_FLOW_ACTIVITY_PATH)
public class CheckFlowMainActivity extends BaseActivity {

    private CheckFlowMainViewModel mViewModel;
    private CheckListBean mCheckListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化DataBinding & VM
        CheckFlowMainDataBinding dataBinding = DataBindingUtil.setContentView(this,R.layout.check_activity_flow);
        mViewModel = new ViewModelProvider(this).get(CheckFlowMainViewModel.class);
        dataBinding.setVm(mViewModel);
        // 悬浮加载框
        ProgressDialog loadingDialog = new ProgressDialog(this);
        mViewModel.mShowLoadingDialog.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if(show) {
                    loadingDialog.show();
                }else {
                    loadingDialog.dismiss();
                }
            }
        });
        // 注册事件总线
        EventBusManager.register(this);
        // 设置标题
        NormalTitleBar toolbar = dataBinding.toolbar;
        toolbar.setTitle(R.string.check_flow_1);
        toolbar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 切换页面的监听
        mViewModel.mFragmentType.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 移除当前Fragment
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
                if(null != currentFragment) {
                    getSupportFragmentManager().beginTransaction().remove(currentFragment).commitAllowingStateLoss();
                }
                if(mViewModel.mFragmentType.get() == 1) {
                    // 添加「盘点商品」Fragment
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container, CheckFlowStep1Fragment.newInstance(mViewModel.mBean)).commitAllowingStateLoss();
                    toolbar.setTitle(R.string.check_flow_1);
                }else if(mViewModel.mFragmentType.get() == 2) {
                    // 添加「初盘核对」Fragment
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container, CheckFLowStep2Fragment.newInstance(mViewModel.mBean)).commitAllowingStateLoss();
                    toolbar.setTitle(R.string.check_flow_2);
                }else if(mViewModel.mFragmentType.get() == 3) {
                    // 添加「复盘修改」Fragment
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container, CheckFLowStep3Fragment.newInstance(mViewModel.mBean)).commitAllowingStateLoss();
                    toolbar.setTitle(R.string.check_flow_3);
                }else if(mViewModel.mFragmentType.get() == 4) {
                    // 添加「损益单」Fragment
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container, CheckFLowStep4Fragment.newInstance(mViewModel.mBean)).commitAllowingStateLoss();
                    toolbar.setTitle(R.string.check_flow_4);
                }
            }
        });
        // 获取从盘点列表传递过来的bean并设置到vm中，方便vm中使用和更改这个bean
        mCheckListBean = (CheckListBean)getIntent().getExtras().get("data");
        mViewModel.initData(mCheckListBean);
        // 初始化要显示的页面
        mViewModel.initFragment(mCheckListBean.getCurrentNode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消事件总线
        EventBusManager.unregister(this);
    }

    @Subscribe
    public void switchTabs(EventBean bean) {
        // 监听别的页面需要动态切换当前Fragment的需求
        if(bean.getType() == EventBean.EVENT_CHECK_SWITCH_TABS) {
            // 获取切换页面的配置bean
            CheckFlowSwitchEventBusBean switchEventBean = (CheckFlowSwitchEventBusBean) bean.getData();
            // 一个定制判断
            if(switchEventBean.getTab() == 2 && mCheckListBean.getCurrentNode() != CheckTaskNodeEnum.CREATE.getValue()) {
                // 如果准备要跳转的页面是第二个页面，并且当前的任务节点已经超过了「盘点商品」，那么不需要更新远端任务节点，直接跳转页面
                mViewModel.switchFragment(switchEventBean.getTab());
            }else {
                // 修改从列表传递过来的条目bean的节点，方便各个页面获取到最新的节点信息从而确定是否可以编辑页面（包括按钮是否显/隐，列表条目是否可点击）
                mViewModel.mBean.setCurrentNode(switchEventBean.getCurrentNode().getValue());
                // 否则全部需要更新远端任务节点
                mViewModel.updateTaskNode(switchEventBean);
            }
        }
    }
}
