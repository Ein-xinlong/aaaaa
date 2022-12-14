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
 * ???????????????????????????VM
 *
 * @author majiheng
 */
public class CheckFlowStep4ViewModel extends NetViewModel {

    // ??????
    private final CheckListRepository mRepository = new CheckListRepository();
    // ??????????????????dialog
    public MutableLiveData<Boolean> mShowLoadingDialog = new MutableLiveData<>(false);
    // ???????????????????????????bean
    private CheckListBean mListBean;
    // ????????????
    public ObservableField<String> mLossAmount = new ObservableField<>("0");// ???????????????
    public ObservableField<String> mGainAmount = new ObservableField<>("0");// ???????????????
    public ObservableField<String> mTotalGalPrice = new ObservableField<>("0");// ???????????????
    public ObservableField<String> mLossCouCount = new ObservableField<>("0");// ???????????????
    public ObservableField<String> mGainCouCount = new ObservableField<>("0");// ???????????????
    public ObservableField<String> mNoDiffCount = new ObservableField<>("0");// ??????????????????
    // ??????????????????????????? ??????????????????
    public ObservableField<CheckReviewQtyType> mCheckedBtn = new ObservableField<>(CheckReviewQtyType.LOSS);
    // ????????????adapter
    private CheckProfitLeftListAdapter mLeftListAdapter;
    // ????????????adapter
    private CheckProfitRightListAdapter mRightListAdapter;
    // ?????????????????????
    public MutableLiveData<Boolean> mRefresh = new MutableLiveData<>(false);
    // ???????????????
    private final int PAGE_SIZE = 20;
    // ????????????factory
    private final CheckProfitListFactory mFactory = new CheckProfitListFactory();
    // ????????????????????????
    public LiveData<PagedList<CheckProfitListItemBean>> mListsLiveData;
    // ????????????????????????
    private Pair<CheckReviewSortKeyType, CheckReviewSortOption> mSortType = new Pair<>(null, null);
    // ??????????????????
    public final MutableLiveData<Boolean> mShowSortTypeDialog = new MutableLiveData<>();
    // ????????????????????????
    private int mCurrentCouGalStatus = -1;
    // ???????????????/??????????????????
    public ObservableField<Boolean> mShowErr = new ObservableField<>(true);
    // ???????????????
    public ObservableField<String> mErrNotice = new ObservableField<>("");
    // ??????btn??????
    public ObservableField<String> mErrBtn = new ObservableField<>("");

    /**
     * ???????????????bean
     */
    public void init(CheckListBean bean) {
        mListBean = bean;
        mListsLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();
    }

    /**
     * ??????????????????
     */
    public void errClick() {
        if(mCurrentCouGalStatus == CheckProfitStatusEnum.NO_GAL.getValue()) {
            // ?????????????????????????????????
            createCouGal();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_FAIL.getValue()) {
            // ?????????????????????????????????
            createCouGal();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_SUCCESS.getValue()
                || mCurrentCouGalStatus == CheckProfitStatusEnum.AUDIT_ING.getValue()
                || mCurrentCouGalStatus == CheckProfitStatusEnum.SUBMIT_AUDIT.getValue()){
            // ??????????????????????????????????????????
            // ????????????????????????
            getHeader();
            // ????????????????????????
            refreshList();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_NONE.getValue()) {
            // ?????????
            // ????????????????????????
            missionComplete();
        }else if(mCurrentCouGalStatus == CheckProfitStatusEnum.GAL_ING.getValue()) {
            // ????????????????????????????????????????????????
            getCouGalStatus();
        }
    }

    /**
     * ??????????????????
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
                    // ????????????????????????
                    MyToast.show(R.string.check_profit_complete_notice,false);
                    // ????????????????????????
                    EventBean<Object> eventBean = new EventBean<>();
                    eventBean.setType(EventBean.EVENT_REFRESH_LIST);
                    EventBusManager.post(eventBean);
                    // ????????????????????????????????????
                    AppManager.getInstance().finishActivity(CheckFlowMainActivity.class);
                }else {
                    // ??????????????????????????????
                    MyToast.show(R.string.check_profit_complete_err,false);
                }
            }
        });
    }

    /**
     * ???????????????????????????????????????
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
                // ?????????????????????
                mCurrentCouGalStatus = integer;
                if(integer == CheckProfitStatusEnum.NO_GAL.getValue()) {
                    // ??????????????????
                    // ??????????????????????????????
                    mShowErr.set(true);
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_create_notice));
                    mErrBtn.set(context.getString(R.string.check_profit_create));
                }else if(integer == CheckProfitStatusEnum.GAL_SUCCESS.getValue()
                        || integer == CheckProfitStatusEnum.AUDIT_ING.getValue()
                        || integer == CheckProfitStatusEnum.SUBMIT_AUDIT.getValue()) {
                    // ??????????????????
                    // ???????????????????????????
                    mShowErr.set(false);
                    // ????????????????????????
                    getHeader();
                    // ????????????????????????
                    refreshList();
                }else if(integer == CheckProfitStatusEnum.GAL_FAIL.getValue()) {
                    // ??????????????????
                    // ??????????????????????????????
                    mShowErr.set(true);
                    // ??????????????????????????????
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_create_err));
                    mErrBtn.set(context.getString(R.string.check_profit_create));
                }else if(integer == CheckProfitStatusEnum.GAL_ING.getValue()) {
                    // ?????????????????????
                    // ?????????????????????????????????????????????????????????????????????&?????????
                    mShowErr.set(true);
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_creating));
                    mErrBtn.set(context.getString(R.string.check_profit_detail));
                }else if(integer == CheckProfitStatusEnum.GAL_NONE.getValue()) {
                    // ?????????
                    mShowErr.set(true);
                    Context context = MyApplication.getInstance().getApplicationContext();
                    mErrNotice.set(context.getString(R.string.check_profit_none));
                    mErrBtn.set(context.getString(R.string.check_profit_complete));
                }else {
                    // ????????????
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
     * ???????????????
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
                    // ???????????????????????????
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
     * ??????????????????
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
     * ?????????????????????????????????????????????
     */
    public void filter(CheckReviewQtyType filterEnum) {
        mCheckedBtn.set(filterEnum);
        refreshList();
    }

    /**
     * ??????????????????
     */
    public void showSortTypeDialog() {
        mShowSortTypeDialog.setValue(true);
    }

    /**
     * ????????????????????????
     */
    public Pair<CheckReviewSortKeyType, CheckReviewSortOption> getSortType() {
        return mSortType;
    }

    /**
     * ????????????????????????
     */
    public void setSortType(Pair<CheckReviewSortKeyType, CheckReviewSortOption> mSortType) {
        this.mSortType = mSortType;
        refreshList();
    }

    /**
     * ??????????????????adapter
     */
    public CheckProfitLeftListAdapter getLeftListAdapter() {
        if(null == mLeftListAdapter) {
            mLeftListAdapter = new CheckProfitLeftListAdapter();
        }
        return mLeftListAdapter;
    }

    /**
     * ??????????????????adapter
     */
    public CheckProfitRightListAdapter getRightListAdapter() {
        if(null == mRightListAdapter) {
            mRightListAdapter = new CheckProfitRightListAdapter();
        }
        return mRightListAdapter;
    }

    /**
     * ?????????????????????
     */
    public void refreshList() {
        mFactory.refresh();
    }

    /**
     * ??????????????????factory
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
            // ??????3?????????????????????
            bean.setQtyFlag(mCheckedBtn.get().getValue());
            bean.setPageNo(mPage);
            // ????????????????????????
            if(null != mSortType.first && null != mSortType.second) {
                // ???????????????????????????
                bean.setSearchKey(String.valueOf(mSortType.first.getValue()));
                bean.setSearchValue(String.valueOf(CheckReviewSortOption.getFlag(mSortType.second)));
                bean.setPageSize(CheckReviewSortOption.getLimit(mSortType.second));
            }else {
                // ????????????????????????????????????
                bean.setPageSize(PAGE_SIZE);
            }
            hashMap.put("data",bean);
            // ??????????????????
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
                            // ???????????????????????????
                            callback.onResult(bean.getItemList(), 0, bean.getItemList().size());
                        }else {
                            // ????????????????????????????????????
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
     * ????????????
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
                                    // ????????????????????????
                                    MyToast.show(R.string.check_profit_cancel_success, false);
                                    // ?????????tab3????????????????????????
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
     * ????????????
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
                                // ??????????????????
                                MyToast.show(R.string.check_profit_commit_success, false);
                                // ????????????????????????????????????
                                AppManager.getInstance().finishActivity(CheckFlowMainActivity.class);
                                // ????????????????????????
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
