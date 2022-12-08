package com.jd.saas.pdacheck.flow.step4;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.flow.CheckFlowMainActivity;
import com.jd.saas.pdacheck.flow.CheckFlowSwitchEventBusBean;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewQtyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortKeyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortOption;
import com.jd.saas.pdacheck.flow.step4.adapter.CheckProfitLeftListAdapter;
import com.jd.saas.pdacheck.flow.step4.adapter.CheckProfitRightListAdapter;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitHeaderResultBean;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitListItemBean;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitListResultBean;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitRequestBean;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitStatusEnum;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckListRepository;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.HashMap;

/**
 * 盘点第四步：损益单VM
 *
 * @author majiheng
 */
public class CheckFlowStep4ViewModel extends NetViewModel {

    // 网络
    private final CheckListRepository mRepository = new CheckListRepository();
    // 是否显示加载dialog
    public MutableLiveData<Boolean> mShowLoadingDialog = new MutableLiveData<>(false);
    // 盘点列表传递过来的bean
    private CheckListBean mListBean;
    // 单头详情
    public ObservableField<String> mLossAmount = new ObservableField<>("0");// 盘损总金额
    public ObservableField<String> mGainAmount = new ObservableField<>("0");// 盘盈总金额
    public ObservableField<String> mTotalGalPrice = new ObservableField<>("0");// 损益总金额
    public ObservableField<String> mLossCouCount = new ObservableField<>("0");// 盘亏总条数
    public ObservableField<String> mGainCouCount = new ObservableField<>("0");// 盘盈总条数
    public ObservableField<String> mNoDiffCount = new ObservableField<>("0");// 无差异总条数
    // 盘亏、盘盈、无差异 按钮单选状态
    public ObservableField<CheckReviewQtyType> mCheckedBtn = new ObservableField<>(CheckReviewQtyType.LOSS);
    // 左侧列表adapter
    private CheckProfitLeftListAdapter mLeftListAdapter;
    // 右侧列表adapter
    private CheckProfitRightListAdapter mRightListAdapter;
    // 是否刷新的标示
    public MutableLiveData<Boolean> mRefresh = new MutableLiveData<>(false);
    // 一页分页数
    private final int PAGE_SIZE = 20;
    // 双列表的factory
    private final CheckProfitListFactory mFactory = new CheckProfitListFactory();
    // 双列表的分页监听
    public LiveData<PagedList<CheckProfitListItemBean>> mListsLiveData;
    // 当前弹窗筛选类型
    private Pair<CheckReviewSortKeyType, CheckReviewSortOption> mSortType = new Pair<>(null, null);
    // 显示筛选弹窗
    public final MutableLiveData<Boolean> mShowSortTypeDialog = new MutableLiveData<>();
    // 当前损益单的状态
    private int mCurrentCouGalStatus = -1;
    // 错误布局显/隐，默认显示
    public ObservableField<Boolean> mShowErr = new ObservableField<>(true);
    // 错误提示语
    public ObservableField<String> mErrNotice = new ObservableField<>("");
    // 错误btn文案
    public ObservableField<String> mErrBtn = new ObservableField<>("");

    /**
     * 初始化页面bean
     */
    public void init(CheckListBean bean) {
        mListBean = bean;
        mListsLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();
    }

    /**
     * 错误重试按钮
     */
    public void errClick() {
        if(mCurrentCouGalStatus == CheckProfitStatusEnum.NO_GAL.getValue()) {
            // 还未生成损益，创建损益
            createCouGal();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_FAIL.getValue()) {
            // 生成损益失败，创建损益
            createCouGal();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_SUCCESS.getValue()
                || mCurrentCouGalStatus == CheckProfitStatusEnum.AUDIT_ING.getValue()
                || mCurrentCouGalStatus == CheckProfitStatusEnum.SUBMIT_AUDIT.getValue()){
            // 损益申请已提交，获取损益详情
            // 获取损益单头信息
            getHeader();
            // 获取损益列表信息
            refreshList();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_NONE.getValue()) {
            // 无损益
            // 完成当前盘点任务
            missionComplete();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_ING.getValue()) {
            // 损益单生成中，重复获取损益单状态
            getCouGalStatus();
        }
    }

    /**
     * 完成盘点任务
     */
    private void missionComplete() {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId",userData.getTenantId());
        hashMap.put("pin",userData.getUserPin());
        CheckProfitRequestBean bean = new CheckProfitRequestBean();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskNo(mListBean.getTaskNo());
        hashMap.put("data",bean);
        mRepository.closeStockTask(hashMap, new NetObserver<Boolean>(this) {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(Boolean missionComplete) {
                if(missionComplete) {
                    // 提示盘点任务结束
                    MyToast.show(R.string.check_profit_complete_notice,false);
                    // 通知盘点列表刷新
                    EventBean<Object> eventBean = new EventBean<>();
                    eventBean.setType(EventBean.EVENT_REFRESH_LIST);
                    EventBusManager.post(eventBean);
                    // 关闭当前整个盘点流程页面
                    AppManager.getInstance().finishActivity(CheckFlowMainActivity.class);
                }else {
                    // 结束当前盘点任务失败
                    MyToast.show(R.string.check_profit_complete_err,false);
                }
            }
        });
    }

    /**
     * 查询当前盘点任务损益单状态
     */
    public void getCouGalStatus() {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId",userData.getTenantId());
        hashMap.put("pin",userData.getUserPin());
        CheckProfitRequestBean bean = new CheckProfitRequestBean();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskNo(mListBean.getTaskNo());
        hashMap.put("data",bean);
        mRepository.getCouGalStatus(hashMap, new NetObserver<Integer>(this) {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(Integer integer) {
                // 当前损益单状态
                mCurrentCouGalStatus = integer;
                if(integer == CheckProfitStatusEnum.NO_GAL.getValue()) {
                    // 还未生成损益
                    // 显示生成损益按钮布局
                    mShowErr.set(true);
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_create_notice));
                    mErrBtn.set(context.getString(R.string.check_profit_create));
                }else if(integer == CheckProfitStatusEnum.GAL_SUCCESS.getValue()
                        || integer == CheckProfitStatusEnum.AUDIT_ING.getValue()
                        || integer == CheckProfitStatusEnum.SUBMIT_AUDIT.getValue()) {
                    // 生成损益成功
                    // 显示损益单详情布局
                    mShowErr.set(false);
                    // 获取损益单头信息
                    getHeader();
                    // 获取损益列表信息
                    refreshList();
                }else if(integer == CheckProfitStatusEnum.GAL_FAIL.getValue()) {
                    // 生成损益失败
                    // 显示生成损益按钮布局
                    mShowErr.set(true);
                    // 显示生成损益按钮布局
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_create_err));
                    mErrBtn.set(context.getString(R.string.check_profit_create));
                }else if(integer == CheckProfitStatusEnum.GAL_ING.getValue()) {
                    // 损益申请已提交
                    // 显示生成损益按钮布局（按钮点击调用的是获取单头&明细）
                    mShowErr.set(true);
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_creating));
                    mErrBtn.set(context.getString(R.string.check_profit_detail));
                }else if(integer == CheckProfitStatusEnum.GAL_NONE.getValue()) {
                    // 无损益
                    mShowErr.set(true);
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_none));
                    mErrBtn.set(context.getString(R.string.check_profit_complete));
                }else {
                    // 未知错误
                    MyToast.show("CouGalStatus err",false);
                }
            }

            @Override
            protected void onError(NetError error) {
                super.onError(error);
            }
        });
    }

    /**
     * 创建损益单
     */
    public void createCouGal() {
        mShowLoadingDialog.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("pin",userData.getUserPin());
        CheckProfitRequestBean bean = new CheckProfitRequestBean();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskNo(mListBean.getTaskNo());
        hashMap.put("data",bean);
        mRepository.createCouGal(hashMap, new BaseObserver<Boolean>() {

            @Override
            protected void onComplete(boolean error) {
                mShowLoadingDialog.setValue(false);
            }

            @Override
            protected void onSuccess(Boolean success) {
                if(success) {
                    // 重新查询损益单状态
                    getCouGalStatus();
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 获取单头详情
     */
    public void getHeader() {
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId",userData.getTenantId());
        CheckProfitRequestBean bean = new CheckProfitRequestBean();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskNo(mListBean.getTaskNo());
        bean.setSelectCountType(2);
        hashMap.put("data",bean);
        mRepository.getProfitHeader(hashMap, new BaseObserver<CheckProfitHeaderResultBean>() {

            @Override
            protected void onSuccess(CheckProfitHeaderResultBean bean) {
                if(null != bean) {
                    mLossAmount.set(bean.getLossAmount());
                    mGainAmount.set(bean.getGainAmount());
                    mTotalGalPrice.set(bean.getTotalGalPrice());
                    mLossCouCount.set(bean.getLossCouCount());
                    mGainCouCount.set(bean.getGainCouCount());
                    mNoDiffCount.set(bean.getNoDiffCount());
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 修改盘亏，盘盈，无差异字体颜色
     */
    public void filter(CheckReviewQtyType filterEnum) {
        mCheckedBtn.set(filterEnum);
        refreshList();
    }

    /**
     * 打开筛选弹窗
     */
    public void showSortTypeDialog() {
        mShowSortTypeDialog.setValue(true);
    }

    /**
     * 获取当前筛选状态
     */
    public Pair<CheckReviewSortKeyType, CheckReviewSortOption> getSortType() {
        return mSortType;
    }

    /**
     * 设置当前筛选状态
     */
    public void setSortType(Pair<CheckReviewSortKeyType, CheckReviewSortOption> mSortType) {
        this.mSortType = mSortType;
        refreshList();
    }

    /**
     * 获取左侧列表adapter
     */
    public CheckProfitLeftListAdapter getLeftListAdapter() {
        if(null == mLeftListAdapter) {
            mLeftListAdapter = new CheckProfitLeftListAdapter();
        }
        return mLeftListAdapter;
    }

    /**
     * 获取右侧列表adapter
     */
    public CheckProfitRightListAdapter getRightListAdapter() {
        if(null == mRightListAdapter) {
            mRightListAdapter = new CheckProfitRightListAdapter();
        }
        return mRightListAdapter;
    }

    /**
     * 下拉刷新双列表
     */
    public void refreshList() {
        mFactory.refresh();
    }

    /**
     * 左右俩列表的factory
     */
    private class CheckProfitListFactory extends DataSource.Factory<Integer, CheckProfitListItemBean> {

        private CheckProfitListDataSource mDataSource;

        public void refresh() {
            if(null != mDataSource) {
                mDataSource.invalidate();
            }
        }

        @NonNull
        @Override
        public DataSource<Integer, CheckProfitListItemBean> create() {
            mDataSource = new CheckProfitListDataSource();
            return mDataSource;
        }
    }

    private class CheckProfitListDataSource extends PositionalDataSource<CheckProfitListItemBean> {

        private int mPage;

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<CheckProfitListItemBean> callback) {
            mPage = 1;
            HashMap<String, Object> hashMap = new HashMap<>();
            UserData userData = UserManager.getInstance().getUserData();
            hashMap.put("tenantId",userData.getTenantId());
            CheckProfitRequestBean bean = new CheckProfitRequestBean();
            bean.setTenantId(userData.getTenantId());
            bean.setWhId(userData.getShopId());
            bean.setTaskNo(mListBean.getTaskNo());
            // 设置3个按钮筛选条件
            bean.setQtyFlag(mCheckedBtn.get().getValue());
            bean.setPageNo(mPage);
            // 设置弹窗筛选条件
            if(null != mSortType.first && null != mSortType.second) {
                // 用户进行了弹窗筛选
                bean.setSearchKey(String.valueOf(mSortType.first.getValue()));
                bean.setSearchValue(String.valueOf(CheckReviewSortOption.getFlag(mSortType.second)));
                bean.setPageSize(CheckReviewSortOption.getLimit(mSortType.second));
            }else {
                // 用户没有弹窗筛选（默认）
                bean.setPageSize(PAGE_SIZE);
            }
            hashMap.put("data",bean);
            // 下拉刷新开始
            mRefresh.postValue(true);
            mRepository.getProfitList(hashMap, new BaseObserver<CheckProfitListResultBean>() {

                @Override
                protected void onComplete(boolean error) {
                    mRefresh.setValue(false);
                }

                @Override
                protected void onSuccess(CheckProfitListResultBean bean) {
                    if(null != bean && null != bean.getItemList()) {
                        if(null != mSortType.first && null != mSortType.second) {
                            // 用户进行了弹窗筛选
                            callback.onResult(bean.getItemList(), 0, bean.getItemList().size());
                        }else {
                            // 用户没有弹窗筛选（默认）
                            callback.onResult(bean.getItemList(), 0, bean.getTotal());
                        }
                    }
                }

                @Override
                protected void onError(NetError error) {
                    MyToast.show(error.getMsg(),false);
                }
            });
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<CheckProfitListItemBean> callback) {
            mPage = mPage + 1;
            HashMap<String, Object> hashMap = new HashMap<>();
            UserData userData = UserManager.getInstance().getUserData();
            hashMap.put("tenantId",userData.getTenantId());
            CheckProfitRequestBean bean = new CheckProfitRequestBean();
            bean.setTenantId(userData.getTenantId());
            bean.setWhId(userData.getShopId());
            bean.setTaskNo(mListBean.getTaskNo());
            bean.setQtyFlag(mCheckedBtn.get().getValue());
            bean.setPageNo(mPage);
            bean.setPageSize(PAGE_SIZE);
            hashMap.put("data",bean);
            mRepository.getProfitList(hashMap, new BaseObserver<CheckProfitListResultBean>() {

                @Override
                protected void onSuccess(CheckProfitListResultBean bean) {
                    if (null != bean && null != bean.getItemList()) {
                        callback.onResult(bean.getItemList());
                    }
                }

                @Override
                protected void onError(NetError error) {
                    MyToast.show(error.getMsg(),false);
                }
            });
        }
    }

    /**
     * 撤销损益
     */
    public void cancel(Context context) {
        new SimpleAlertDialog.Builder(context, R.string.check_profit_cancel_confirm)
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShowLoadingDialog.setValue(true);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        UserData userData = UserManager.getInstance().getUserData();
                        hashMap.put("pin", userData.getUserPin());
                        CheckProfitRequestBean bean = new CheckProfitRequestBean();
                        bean.setTaskNo(mListBean.getTaskNo());
                        bean.setTenantId(userData.getTenantId());
                        bean.setWhId(userData.getShopId());
                        hashMap.put("data", bean);
                        mRepository.cancelProfit(hashMap, new BaseObserver<Boolean>() {

                            @Override
                            protected void onComplete(boolean error) {
                                mShowLoadingDialog.setValue(false);
                            }

                            @Override
                            protected void onSuccess(Boolean aBoolean) {
                                if(aBoolean) {
                                    // 提交撤销成功提示
                                    MyToast.show(R.string.check_profit_cancel_success, false);
                                    // 跳转到tab3「复盘修改」页面
                                    EventBean<CheckFlowSwitchEventBusBean> eventBean = new EventBean<>();
                                    eventBean.setType(EventBean.EVENT_CHECK_SWITCH_TABS);
                                    CheckFlowSwitchEventBusBean data = new CheckFlowSwitchEventBusBean();
                                    data.setTab(3);
                                    data.setCurrentNode(CheckTaskNodeEnum.REVIEW);
                                    data.setTargetNode(CheckTaskNodeEnum.REVIEW);
                                    eventBean.setData(data);
                                    EventBusManager.post(eventBean);
                                }
                            }

                            @Override
                            protected void onError(NetError error) {
                                MyToast.show(error.getMsg(),false);
                            }
                        });
                    }
                }).build().show();
    }

    /**
     * 提交审批
     */
    public void commit(Context context) {
        new SimpleAlertDialog.Builder(context, R.string.check_profit_commit_confirm)
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShowLoadingDialog.setValue(true);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        UserData userData = UserManager.getInstance().getUserData();
                        hashMap.put("pin", userData.getUserPin());
                        CheckProfitRequestBean bean = new CheckProfitRequestBean();
                        bean.setTenantId(userData.getTenantId());
                        bean.setWhId(userData.getShopId());
                        bean.setPin(userData.getUserPin());
                        bean.setTaskNo(mListBean.getTaskNo());
                        hashMap.put("data", bean);
                        mRepository.submitProfit(hashMap, new BaseObserver<Boolean>() {

                            @Override
                            protected void onComplete(boolean error) {
                                mShowLoadingDialog.setValue(false);
                            }

                            @Override
                            protected void onSuccess(Boolean aBoolean) {
                                // 提交成功提示
                                MyToast.show(R.string.check_profit_commit_success, false);
                                // 关闭当前整个盘点流程页面
                                AppManager.getInstance().finishActivity(CheckFlowMainActivity.class);
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
                }).build().show();
    }
}
