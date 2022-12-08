package com.jd.saas.pdacheck.newtask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckListRepository;
import com.jd.saas.pdacheck.newtask.bean.CheckNewTaskCancellationData;
import com.jd.saas.pdacheck.newtask.bean.CheckNewTaskDetailBean;
import com.jd.saas.pdacheck.newtask.bean.CheckNewTaskRequestData;
import com.jd.saas.pdacheck.newtask.bean.CheckNewTaskUpdateRequestData;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.calendar.CalendarBottomSheetDialog;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * 新建盘点任务viewModel
 *
 * @author ext.anxinlong
 */
public class CheckNewTaskViewModel extends NetViewModel {
    private final CheckListRepository mRepository = new CheckListRepository();
    private String checkScope;//盘点范围
    private String whetherShowInventory;//是否显示库存快照
    private String finishCheck;//完成盘点时，是否展示库存
    private ArrayList<String> catList = new ArrayList<>();//指定类目结合
    // 日期string
    public ObservableField<String> mDate = new ObservableField<>(MyApplication.getInstance().getApplicationContext().getString(R.string.check_new_task_data_selected));
    //控制底部按钮显示隐藏
    public MutableLiveData<Integer> createOrSave = new MutableLiveData<>(0);
    //盘点范围的状态 0 默认都不选中 1 库存盘点选中 2 指定类目选中
    public ObservableField<Integer> scopeStatus = new ObservableField<>(0);
    //是否显示库存快照的状态 0 默认都不选中 1 显示选中 2 不显示选中
    public ObservableField<Integer> showInventoryStatus = new ObservableField<>(0);
    //库存显示状态 0 默认都不选中 1默认为0选中 2默认当前库存选中
    public ObservableField<Integer> finishCheckStatus = new ObservableField<>(0);
    //是否可以编辑
    public MutableLiveData<Boolean> isEnabled = new MutableLiveData<>(true);
    // 开始时间
    private String mStartTime = null;
    // 结束时间
    private String mEndTime = null;

    private CheckListBean checkBean;

    public MutableLiveData<Boolean> isShowDialog = new MutableLiveData<>(false);//控制底部按钮显示隐藏
    /**
     * 日期选择
     */
    public void dateSelect(View view) {
        CalendarBottomSheetDialog bottomSheetDialog = new CalendarBottomSheetDialog(view.getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setOnDateSelected(this::onDateSelected);
        bottomSheetDialog.show();
    }

    /**
     * 选择盘点范围
     */
    public void checkScopeSelect(int i, View view) {
        if (i == 1) {
            checkScope = "5";
            catList.clear();
        } else {
            checkScope = "3";
            Bundle paramBundle = new Bundle();
            paramBundle.putStringArrayList(CheckRouterPath.CHECK_CATEGORY_PARAM_SELECT, catList);
            RouterClient.getInstance().forward(view.getContext(), CheckRouterPath.CHECK_PATH_CATEGORY, paramBundle, 100);
        }
    }


    /**
     * 选择是否展示库存快照
     */
    public void isShowInventorySelect(boolean i) {
        if (i) {
            whetherShowInventory = "1";
        } else {
            whetherShowInventory = "0";
        }
    }

    /**
     * 完成盘点时，若商品未填写数据
     */
    public void finishCheckSelect(int i) {
        if (i == 0) {
            finishCheck = "0";
        } else {
            finishCheck = "1";
        }
    }

    /**
     * 创建任务
     */
    public void createTask() {
        if (mStartTime == null || mEndTime == null) {
            MyToast.show(R.string.check_new_task_toast_select_time, false);
            return;
        }
        if (checkScope == null) {
            MyToast.show(R.string.check_new_task_toast_select_check_cope, false);
            return;
        }
        if (whetherShowInventory == null) {
            MyToast.show(R.string.check_new_task_toast_select_whether_inventory, false);
            return;
        }
        if (finishCheck == null) {
            MyToast.show(R.string.check_new_task_toast_select_finish_check, false);
            return;
        }
        if (Objects.equals(checkScope, "3")&&catList.size()==0) {
            MyToast.show(R.string.check_new_task_select_category,false);
            return;
        }
        isShowDialog.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("pin", userData.getUserPin());
        hashMap.put("tenantId", userData.getTenantId());
        CheckNewTaskRequestData bean = new CheckNewTaskRequestData();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskScope(checkScope);
        bean.setStartDate(mStartTime);
        bean.setEndDate(mEndTime);
        bean.setShowStock(whetherShowInventory);
        bean.setMissRule(finishCheck);
        bean.setCatList(catList);
        hashMap.put("data", bean);
        mRepository.getNewTask(hashMap, new BaseObserver<Boolean>() {
            @Override
            protected void onSuccess(Boolean bool) {

                if (bool) {
                    MyToast.show(R.string.check_new_task_toast_select_create_success, false);
                    finishActivity();
                } else {
                    MyToast.show(R.string.check_new_task_toast_select_create_lose, false);
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }

            @Override
            protected void onComplete(boolean error) {
                isShowDialog.setValue(false);
            }
        });
    }

    /**
     * 查询任务信息以及详情
     */
    public void updateTask(CheckListBean checkListBean) {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("pin", userData.getUserPin());
        hashMap.put("tenantId", userData.getTenantId());
        CheckNewTaskUpdateRequestData bean = new CheckNewTaskUpdateRequestData();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskNo(checkListBean.getTaskNo());
        bean.setFlag(checkListBean.getTaskScope() == 3 ? 1 : 0);
        hashMap.put("data", bean);
        mRepository.getTaskDetail(hashMap, new BaseObserver<CheckNewTaskDetailBean>() {
            @Override
            protected void onSuccess(CheckNewTaskDetailBean bean) {
                if (bean != null) {
                    mDate.set(bean.getStartEndDateStr());
                    if (bean.getTaskScope() == 5) {//3是指定类目的  5是整库
                        scopeStatus.set(1);
                    } else {
                        scopeStatus.set(2);
                    }
                    if (bean.getShowStock() == 1) {//是否显示库存快照：0-不显示，1-显示
                        showInventoryStatus.set(1);
                    } else {
                        showInventoryStatus.set(2);
                    }
                    if (bean.getMissRule() == 0) {//0-默认库存为0，1-默认当前库存
                        finishCheckStatus.set(1);
                    } else {
                        finishCheckStatus.set(2);
                    }
                    if (bean.getCatList() != null) {
                        catList = bean.getCatList();
                    }

                    mStartTime = bean.getStartDate();
                    mEndTime = bean.getEndDate();
                    checkScope = bean.getTaskScope() + "";
                    whetherShowInventory = bean.getShowStock() + "";
                    finishCheck = bean.getMissRule() + "";
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }
        });
    }

    /**
     * 保存任务
     */
    public void saveTask() {
        if (mStartTime == null || mEndTime == null) {
            MyToast.show(R.string.check_new_task_toast_select_time, false);
            return;
        }
        if (Objects.equals(checkScope, "3")&&catList.size()==0) {
            MyToast.show(R.string.check_new_task_select_category,false);
            return;
        }
        isShowDialog.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("pin", userData.getUserPin());
        hashMap.put("tenantId", userData.getTenantId());
        CheckNewTaskRequestData bean = new CheckNewTaskRequestData();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskScope(checkScope);
        bean.setStartDate(mStartTime);
        bean.setEndDate(mEndTime);
        bean.setShowStock(whetherShowInventory);
        bean.setMissRule(finishCheck);
        bean.setCatList(catList);
        bean.setTaskNo(checkBean.getTaskNo());
        hashMap.put("data", bean);
        mRepository.getUpdateTask(hashMap, new BaseObserver<Boolean>() {
            @Override
            protected void onSuccess(Boolean bool) {
                if (bool) {
                    MyToast.show(R.string.check_new_task_toast_select_save_success, false);
                    finishActivity();
                } else {
                    MyToast.show(R.string.check_new_task_toast_select_save_lose, false);
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }

            @Override
            protected void onComplete(boolean error) {
                isShowDialog.setValue(false);
            }
        });
    }

    /**
     * 取消任务
     */
    public void cancelTask(View view) {
        new SimpleAlertDialog.Builder(view.getContext(), R.string.check_new_task_delete_confirm_notice)
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShowDialog.setValue(true);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        UserData userData = UserManager.getInstance().getUserData();
                        hashMap.put("pin", userData.getUserPin());
                        hashMap.put("tenantId", userData.getTenantId());
                        CheckNewTaskCancellationData bean = new CheckNewTaskCancellationData();
                        bean.setTenantId(userData.getTenantId());
                        bean.setPin(userData.getUserPin());
                        bean.setWhId(userData.getShopId());
                        bean.setTaskNo(checkBean.getTaskNo());
                        bean.setCurrentNode(checkBean.getCurrentNode());
                        hashMap.put("data", bean);
                        mRepository.getDeleteTask(hashMap, new BaseObserver<Boolean>() {
                            @Override
                            protected void onSuccess(Boolean o) {
                                if (o!=null){
                                    if (o) {
                                        MyToast.show(R.string.check_new_task_toast_select_cancel_success, false);
                                        finishActivity();
                                    } else {
                                        MyToast.show(R.string.check_new_task_toast_select_cancel_lose, false);
                                    }
                                }

                            }

                            @Override
                            protected void onError(NetError error) {
                                MyToast.show(error.getMsg(), false);
                            }

                            @Override
                            protected void onComplete(boolean error) {
                                isShowDialog.setValue(false);
                            }
                        });
                    }
                }).build().show();

    }


    /**
     * 更新状态
     * */
    public void updateStatus(CheckListBean bean){
        checkBean=bean;
        createOrSave.setValue(1);
        if (bean.getStatus() == 10) {
            isEnabled.setValue(true);
        } else if (bean.getStatus() == 30) {
            isEnabled.setValue(false);
            createOrSave.setValue(2);
        } else {
            isEnabled.setValue(false);
        }
        updateTask(bean);
    }

    public void setCatList(ArrayList<String> catList) {
        this.catList = catList;
    }

    public void finishActivity() {
        EventBean eventBean = new EventBean();
        eventBean.setType(EventBean.EVENT_REFRESH_LIST);
        EventBusManager.post(eventBean);
        AppManager.getInstance().finishActivity(CheckNewTaskActivity.class);
    }

    private void onDateSelected(String startDate, String endDate) {
        if (TextUtils.isEmpty(startDate)) {
            mDate.set(MyApplication.getInstance().getString(R.string.check_new_task_data_selected));
            mStartTime = null;
            mEndTime = null;
            mDate.set(MyApplication.getInstance().getApplicationContext().getString(R.string.check_new_task_data_selected));
        } else {
            mStartTime = startDate + "T00:00:00.422+0800";
            mEndTime = endDate + "T23:59:59.422+0800";
            mDate.set(startDate + "~" + endDate);
        }
    }
}
