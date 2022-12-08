package com.jd.saas.pdacheck.list;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitRequestBean;
import com.jd.saas.pdacheck.list.adapter.CheckListAdapter;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckListRepository;
import com.jd.saas.pdacheck.net.CheckListRequestData;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.dialog.linkage.model.ClientInfoBean;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.HashMap;
import java.util.List;

/**
 * 盘存盘点-任务列表界面
 *
 * @author ext.mengmeng
 */
public class CheckListViewModel extends NetViewModel {

    private final CheckListRepository mRepository = new CheckListRepository();
    // 是否刷新的标示
    public MutableLiveData<Boolean> mRefresh = new MutableLiveData<>(false);
    // 任务列表adapter
    private CheckListAdapter mCheckListAdapter;
    // 显示「撤回任务」二次确认对话框
    public MutableLiveData<CheckListBean> mRollBackDialog = new MutableLiveData<>();
    // 审批被驳回，重新开始盘点弹窗
    public MutableLiveData<CheckListBean> mShowAlertDialog = new MutableLiveData<>();

    /**
     * 获取任务列表的adapter
     */
    public CheckListAdapter getTaskListAdapter() {
        if(null == mCheckListAdapter) {
            mCheckListAdapter = new CheckListAdapter();
            mCheckListAdapter.setOnItemClickListener(new CheckListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(CheckListBean bean) {
                    if(bean.getFlowStatus() == 0) {
                        MyToast.show(R.string.check_review,false);
                        return;
                    }
                    if(bean.getFlowStatus() == 2 && bean.getCurrentNode() == CheckTaskNodeEnum.REJECT.getValue()) {
                        // 审批流驳回
                        mShowAlertDialog.setValue(bean);
                        return;
                    }
                    if(bean.getStatus() == 30) {
                        MyToast.show(R.string.check_done,false);
                        return;
                    }
                    // 跳转到盘点流程页面
                    checkFlowPathMainGo(bean);
                }

                @Override
                public void onItemBtn1Click(CheckListBean bean) {
                    // 跳转到盘点任务页面
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",bean);
                    RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), CheckRouterPath.CHECK_NEW_TASK_PATH_MAIN, bundle);
                }

                @Override
                public void onItemBtn2Click(CheckListBean bean) {
                    // 展示撤回审批二次确认弹窗
                    mRollBackDialog.setValue(bean);
                }
            });
        }
        return mCheckListAdapter;
    }

    /**
     * 获取盘点列表
     */
    public void getTaskList() {
        mRefresh.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId", userData.getTenantId());
        hashMap.put("pin", userData.getUserPin());
        CheckListRequestData bean = new CheckListRequestData();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        hashMap.put("data", bean);
        mRepository.getTaskList(hashMap, new BaseObserver<List<CheckListBean>>() {
            @Override
            protected void onSuccess(List<CheckListBean> checkListBeans) {
                // 刷新列表
                getTaskListAdapter().refreshList(checkListBeans);
            }

            @Override
            protected void onComplete(boolean error) {
                mRefresh.setValue(false);
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 创建盘点任务
     */
    public void createTask() {
       RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), CheckRouterPath.CHECK_NEW_TASK_PATH_MAIN);
    }

    /**
     * 撤回任务
     */
    public void rollback(CheckListBean bean) {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId",userData.getTenantId());
        hashMap.put("pin",userData.getUserPin());
        ClientInfoBean clientInfo = new ClientInfoBean();
        clientInfo.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo",clientInfo);
        hashMap.put("data",bean.getInstanceId());
        mRepository.rollback(hashMap, new NetObserver<Boolean>(this) {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(Boolean rollback) {
                if(rollback) {
                    // Toast提示用户撤销成功
                    MyToast.show(R.string.check_roll_back_success,false);
                    // 请求成功，刷新列表
                    getTaskList();
                }else {
                    MyToast.show(R.string.check_roll_back_fail,false);
                }
            }

            @Override
            protected void onError(NetError error) {
                super.onError(error);
                // 为啥接口失败也刷新列表？因为后端MQ无法及时刷新，只能这样
                getTaskList();
            }
        });
    }

    /**
     * 撤销损益
     */
    public void cancelProfit(CheckListBean checkListBean) {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("pin", userData.getUserPin());
        CheckProfitRequestBean bean = new CheckProfitRequestBean();
        bean.setTaskNo(checkListBean.getTaskNo());
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        hashMap.put("data", bean);
        mRepository.cancelProfit(hashMap, new BaseObserver<Boolean>() {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(Boolean aBoolean) {
                if(aBoolean) {
                    // 撤销损益成功
                    // 设置当前条目的节点为「复盘修改」
                    checkListBean.setCurrentNode(CheckTaskNodeEnum.REVIEW.getValue());
                    // 跳转到盘点流程页面
                    checkFlowPathMainGo(checkListBean);
                    // 刷新当前任务列表
                    getTaskList();
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 跳转到「盘点流程」页面
     */
    private void checkFlowPathMainGo(CheckListBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",bean);
        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), CheckRouterPath.CHECK_FLOW_PATH_MAIN,bundle);
    }
}
