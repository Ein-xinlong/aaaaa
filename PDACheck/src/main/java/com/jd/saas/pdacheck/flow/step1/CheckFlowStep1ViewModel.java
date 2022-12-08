package com.jd.saas.pdacheck.flow.step1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.flow.CheckFlowSwitchEventBusBean;
import com.jd.saas.pdacheck.flow.step1.bean.CheckPreOrderBean;
import com.jd.saas.pdacheck.flow.step1.paging.CheckCouListDataSourceFactory;
import com.jd.saas.pdacheck.flow.step1.paging.CheckCouListParamBuilder;
import com.jd.saas.pdacheck.flow.step1.repo.CheckCouRepository;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;

import org.jetbrains.annotations.NotNull;

/**
 * 盘点第一步：预盘点列表VM
 *
 * @author majiheng
 */
public class CheckFlowStep1ViewModel extends NetViewModel {

    private CheckListBean mCheckListBean;
    // 「盘点商品」按钮是否显示，列表条目是否可点击
    public ObservableField<Boolean> mContentClickable = new ObservableField<>(true);
    private final CheckCouRepository repository;
    public final CheckCouListDataSourceFactory factory;
    public LiveData<PagedList<CheckPreOrderBean>> preOrderList;
    private static final int PAGE_SIZE = 10;
    public MutableLiveData<String> taskNo = new MutableLiveData<>();
    public ObservableField<String> taskCreateTime = new ObservableField<>();
    public final MutableLiveData<Boolean> isRefresh = new MutableLiveData<>(true);
    public final MutableLiveData<String> showToastEvent = new MutableLiveData<>();

    /**
     * 初始化
     */
    public void init(CheckListBean bean) {
        mCheckListBean = bean;
        // 判断当前节点是否显示「盘点商品」按钮，是否列表条目可点击
        mContentClickable.set(mCheckListBean.getCurrentNode() < CheckTaskNodeEnum.PROFIT.getValue());
        taskNo.setValue(bean.getTaskNo());
        taskCreateTime.set(bean.getCreateDate());
    }

    public CheckFlowStep1ViewModel(CheckCouRepository repository) {
        this.repository = repository;
        this.factory = new CheckCouListDataSourceFactory(this.repository, makeParamBuilder(), error -> {
            if (error != null) {
                showToastEvent.setValue(error.getMsg());
            }
            isRefresh.setValue(false);
        });
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE).setInitialLoadSizeHint(PAGE_SIZE).build();
        preOrderList = new LivePagedListBuilder<>(factory.map(CheckPreOrderListConvertor::convert), config)
                .setBoundaryCallback(new PagedList.BoundaryCallback<CheckPreOrderBean>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        isRefresh.setValue(false);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull @NotNull CheckPreOrderBean itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        isRefresh.setValue(false);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull @NotNull CheckPreOrderBean itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                    }
                })
                .build();
    }


    public void refresh() {
        isRefresh.setValue(true);
        factory.refresh(makeParamBuilder());
    }


    public CheckCouListParamBuilder makeParamBuilder() {
        return new CheckCouListParamBuilder(taskNo.getValue());
    }

    public void goBack() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",mCheckListBean);
        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), CheckRouterPath.PERFORM_PATH_MAIN,bundle);
    }

    public void goNext() {
        EventBean<CheckFlowSwitchEventBusBean> eventBean = new EventBean<>();
        CheckFlowSwitchEventBusBean switchEventBusBean = new CheckFlowSwitchEventBusBean();
        switchEventBusBean.setTab(2);
        switchEventBusBean.setCurrentNode(CheckTaskNodeEnum.MISSED_CHECK);
        switchEventBusBean.setTargetNode(CheckTaskNodeEnum.MISSED_CHECK);
        eventBean.setType(EventBean.EVENT_CHECK_SWITCH_TABS);
        eventBean.setData(switchEventBusBean);
        EventBusManager.post(eventBean);
    }

    public void deleteDelCouHeader(String couNo) {
        repository.getDelCouHeader(couNo, new BaseObserver<Boolean>() {
            @Override
            protected void onSuccess(Boolean bean) {
                refresh();
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }
        });
    }
}
