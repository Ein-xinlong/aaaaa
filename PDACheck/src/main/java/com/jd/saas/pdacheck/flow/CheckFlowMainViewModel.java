package com.jd.saas.pdacheck.flow;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckListRepository;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.HashMap;

/**
 * 盘点流程框架VM
 *
 * @author majiheng
 */
public class CheckFlowMainViewModel extends NetViewModel {

    // 网络
    private final CheckListRepository mRepository = new CheckListRepository();

    // 从盘点列表页传递过来的bean
    public CheckListBean mBean;
    // 是否显示加载dialog
    public MutableLiveData<Boolean> mShowLoadingDialog = new MutableLiveData<>(false);
    // 当前页面Fragment的类型
    public ObservableField<Integer> mFragmentType = new ObservableField<>(0);
    // 第一个tab ui状态
    public ObservableField<CheckFlowTabUIBean> mTab1UI = new ObservableField<>();
    // 第二个tab ui状态
    public ObservableField<CheckFlowTabUIBean> mTab2UI = new ObservableField<>();
    // 第三个tab ui状态
    public ObservableField<CheckFlowTabUIBean> mTab3UI = new ObservableField<>();
    // 第四个tab ui状态
    public ObservableField<CheckFlowTabUIBean> mTab4UI = new ObservableField<>();

    /**
     * 初始化本页数据
     */
    public void initData(CheckListBean bean) {
        mBean = bean;
    }

    /**
     * 接收数据总线，切换tab
     */
    public void updateTaskNode(CheckFlowSwitchEventBusBean checkFlowSwitchEventBusBean) {
        // 首先请求接口更新盘点流程节点，更新完成后再切换流程tab
        mShowLoadingDialog.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId",userData.getTenantId());
        hashMap.put("pin",userData.getUserPin());
        CheckFlowRequestBean bean = new CheckFlowRequestBean();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskNo(mBean.getTaskNo());
        bean.setStep(checkFlowSwitchEventBusBean.getTargetNode().getValue());
        bean.setCurrentNode(checkFlowSwitchEventBusBean.getCurrentNode().getValue());
        bean.setPin(userData.getUserPin());
        hashMap.put("data",bean);
        mRepository.updateTaskNode(hashMap, new NetObserver<Boolean>(this) {

            @Override
            protected void onComplete(boolean error) {
                mShowLoadingDialog.setValue(false);
            }

            @Override
            protected void onSuccess(Boolean data) {
                // 切换页面和tab
                switchFragment(checkFlowSwitchEventBusBean.getTab());
                switchTabUI(checkFlowSwitchEventBusBean.getTab());
                // 通知盘点列表刷新
                EventBean<Object> eventBean = new EventBean<>();
                eventBean.setType(EventBean.EVENT_REFRESH_LIST);
                EventBusManager.post(eventBean);
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 初始化页面（决定4个页面切换到某个页面）
     */
    public void initFragment(int currentNode) {
        if(currentNode == CheckTaskNodeEnum.CREATE.getValue()) {
            // 切换到「盘点商品」
            switchFragment(1);
            switchTabUI(1);
        }else if(currentNode == CheckTaskNodeEnum.MISSED_CHECK.getValue()) {
            // 切换到「初盘核对」
            switchFragment(2);
            switchTabUI(2);
        }else if(currentNode == CheckTaskNodeEnum.REVIEW.getValue() || currentNode == CheckTaskNodeEnum.DIFF_HANDLE.getValue()) {
            // 切换到「复盘修改」
            switchFragment(3);
            switchTabUI(3);
        }else if(currentNode == CheckTaskNodeEnum.PROFIT.getValue()) {
            // 切换到「损益单」
            switchFragment(4);
            switchTabUI(4);
        }
    }

    /**
     * 切换页面
     */
    public void switchFragment(int tab) {
        mFragmentType.set(tab);
    }

    /**
     * 切换页面tab的ui状态
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void switchTabUI(int tab) {
        Context context = MyApplication.getInstance();
        CheckFlowTabUIBean tab1 = new CheckFlowTabUIBean();
        CheckFlowTabUIBean tab2 = new CheckFlowTabUIBean();
        CheckFlowTabUIBean tab3 = new CheckFlowTabUIBean();
        CheckFlowTabUIBean tab4 = new CheckFlowTabUIBean();
        if(tab == 1) {
            // 设置tab1
            tab1.setTabIcon(context.getDrawable(R.drawable.check_ic_step1_checked));
            tab1.setTabTextColorDark(true);
            tab1.setTabClickable(true);
            // 设置tab2
            tab2.setTabIcon(context.getDrawable(R.drawable.check_ic_step2_unchecked));
            tab2.setTabTextColorDark(false);
            tab2.setTabClickable(false);
            // 设置tab3
            tab3.setTabIcon(context.getDrawable(R.drawable.check_ic_step3_unchecked));
            tab3.setTabTextColorDark(false);
            tab3.setTabClickable(false);
            // 设置tab4
            tab4.setTabIcon(context.getDrawable(R.drawable.check_ic_step4_unchecked));
            tab4.setTabTextColorDark(false);
            tab4.setTabClickable(false);
        }else if(tab == 2) {
            // 设置tab1
            tab1.setTabIcon(context.getDrawable(R.drawable.check_ic_down));
            tab1.setTabTextColorDark(true);
            tab1.setTabClickable(true);
            // 设置tab2
            tab2.setTabIcon(context.getDrawable(R.drawable.check_ic_step2_checked));
            tab2.setTabTextColorDark(true);
            tab2.setTabClickable(true);
            // 设置tab3
            tab3.setTabIcon(context.getDrawable(R.drawable.check_ic_step3_unchecked));
            tab3.setTabTextColorDark(false);
            tab3.setTabClickable(false);
            // 设置tab4
            tab4.setTabIcon(context.getDrawable(R.drawable.check_ic_step4_unchecked));
            tab4.setTabTextColorDark(false);
            tab4.setTabClickable(false);
        }else if(tab == 3) {
            // 设置tab1
            tab1.setTabIcon(context.getDrawable(R.drawable.check_ic_down));
            tab1.setTabTextColorDark(true);
            tab1.setTabClickable(true);
            // 设置tab2
            tab2.setTabIcon(context.getDrawable(R.drawable.check_ic_down));
            tab2.setTabTextColorDark(true);
            tab2.setTabClickable(true);
            // 设置tab3
            tab3.setTabIcon(context.getDrawable(R.drawable.check_ic_step3_checked));
            tab3.setTabTextColorDark(true);
            tab3.setTabClickable(true);
            // 设置tab4
            tab4.setTabIcon(context.getDrawable(R.drawable.check_ic_step4_unchecked));
            tab4.setTabTextColorDark(false);
            tab4.setTabClickable(false);
        }else if(tab == 4) {
            // 设置tab1
            tab1.setTabIcon(context.getDrawable(R.drawable.check_ic_down));
            tab1.setTabTextColorDark(true);
            tab1.setTabClickable(true);
            // 设置tab2
            tab2.setTabIcon(context.getDrawable(R.drawable.check_ic_down));
            tab2.setTabTextColorDark(true);
            tab2.setTabClickable(true);
            // 设置tab3
            tab3.setTabIcon(context.getDrawable(R.drawable.check_ic_down));
            tab3.setTabTextColorDark(true);
            tab3.setTabClickable(true);
            // 设置tab4
            tab4.setTabIcon(context.getDrawable(R.drawable.check_ic_step4_checked));
            tab4.setTabTextColorDark(true);
            tab4.setTabClickable(true);
        }
        mTab1UI.set(tab1);
        mTab2UI.set(tab2);
        mTab3UI.set(tab3);
        mTab4UI.set(tab4);
    }
}
