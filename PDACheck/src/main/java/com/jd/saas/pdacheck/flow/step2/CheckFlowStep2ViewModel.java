package com.jd.saas.pdacheck.flow.step2;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.flow.CheckFlowSwitchEventBusBean;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuListReqParam;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuResp;
import com.jd.saas.pdacheck.flow.step2.repo.CheckMissedRepository;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewQtyType;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.ListUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 盘点第二步：初盘核对VM
 *
 * @author gouhetong
 */
public class CheckFlowStep2ViewModel extends NetViewModel {
    // 从盘点列表传递过来的条目bean
    private CheckListBean mCheckListBean;
    // 「提交初盘」按钮是否显示，列表条目是否可点击
    public ObservableField<Boolean> mContentClickable = new ObservableField<>(true);
    private final CheckMissedRepository repository;
    /** 是否过滤数量为0的 */
    public final MutableLiveData<Boolean> filterZeroQty = new MutableLiveData<>(false);
    /** 漏盘商品列表 */
    public final MutableLiveData<List<CheckMissedSkuBean>> skuList = new MutableLiveData<>(new ArrayList<>());
    /** 漏盘商品数量 */
    public final LiveData<Integer> missedSkuCnt = Transformations.map(skuList, List::size);
    public final MutableLiveData<Boolean> isRefresh = new MutableLiveData<>(true);
    public final MutableLiveData<LinkedList<String>> showToastEvent = new MutableLiveData<>();
    /**
     * 准备提交收货的商品信息
     */
    public final MutableLiveData<List<CheckMissedSkuBean>> submitList = new MutableLiveData<>();


    public CheckFlowStep2ViewModel(CheckMissedRepository repository) {
        this.repository = repository;
    }

    /**
     * 初始化方法
     */
    public void init(CheckListBean checkListBean) {
        mCheckListBean = checkListBean;
        // 判断当前节点是否显示「提交初盘」按钮，是否列表条目可点击
        mContentClickable.set(mCheckListBean.getCurrentNode() < CheckTaskNodeEnum.PROFIT.getValue());
        filterZeroQty.setValue(false);
        submitList.setValue(null);
        refresh();
    }

    /**
     * 下拉刷新
     */
    public void refresh() {
        isRefresh.setValue(true);
        Boolean value = filterZeroQty.getValue();
        Integer qryFlag = null;
        if (value != null && value) {
            qryFlag = CheckReviewQtyType.NOT_EQUAL.getValue();
        }
        repository.getSkuList(new CheckMissedSkuListReqParam(mCheckListBean.getTaskNo(), qryFlag), new NetObserver<List<CheckMissedSkuResp>>(this) {
            @Override
            protected void onSuccess(List<CheckMissedSkuResp> result) {
                if (result != null) {
                    List<CheckMissedSkuBean> checkMissedSkuBeans = ListUtils.map(result, CheckMissedConvertor::convert);
                    applyInput2Result(checkMissedSkuBeans);
                    skuList.postValue(checkMissedSkuBeans);
                }
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                isRefresh.setValue(false);
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }
        });
    }

    /**
     * 将本地输入的内容设置到接口返回的数据上
     */
    private void applyInput2Result(List<CheckMissedSkuBean> checkMissedSkuBeans) {
        List<CheckMissedSkuBean> submitSkuList = submitList.getValue();
        if (submitSkuList == null || submitSkuList.isEmpty()) {
            return;
        }
        if (checkMissedSkuBeans == null) {
            submitList.postValue(new ArrayList<>());
            return;
        }
        for (CheckMissedSkuBean skuBean : checkMissedSkuBeans) {
            for (CheckMissedSkuBean submitSku : submitSkuList) {
                if (skuBean.isSameBean(submitSku)) {
                    skuBean.setInputQty(submitSku.getInputQty());
                    break;
                }
            }
        }
        List<CheckMissedSkuBean> filterZero = ListUtils.filter(checkMissedSkuBeans, item ->
                        item != null && item.getInputQty() != null
        );
        submitList.postValue(filterZero);

    }

    /**
     * 提交初盘 提交成功后会跳转下一步
     */
    public void submit() {
        List<CheckMissedSkuBean> submitListValue = submitList.getValue();
        if (submitListValue == null || submitListValue.isEmpty()) {
            goNext();
            return;
        }
        showProgress.set(true);
        repository.submit(mCheckListBean.getTaskNo(), submitListValue, new NetObserver<Boolean>(this) {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    goNext();
                } else {
                    toast(MyApplication.getInstance().getString(R.string.check_missed_submit_fail));
                }
            }

            @Override
            protected void onError(NetError error) {
                toast(error.getMsg());
            }
        });
    }

    private void toast(String msg) {
        LinkedList<String> value = showToastEvent.getValue();
        if (value == null) {
            value = new LinkedList<>();
        }
        value.offer(msg);
        showToastEvent.postValue(value);
    }

    /**
     * 跳转下一步
     */
    private void goNext() {
        EventBean<CheckFlowSwitchEventBusBean> eventBean = new EventBean<>();
        eventBean.setType(EventBean.EVENT_CHECK_SWITCH_TABS);
        CheckFlowSwitchEventBusBean data = new CheckFlowSwitchEventBusBean();
        data.setTab(3);
        data.setCurrentNode(CheckTaskNodeEnum.REVIEW);
        data.setTargetNode(CheckTaskNodeEnum.REVIEW);
        eventBean.setData(data);
        EventBusManager.post(eventBean);
    }

    /**
     * 将编辑后的商品添加到待提交列表
     * <p>
     * 如果已经在列表中则更新商品的输入信息
     *
     * @param bean 编辑后的商品信息
     */
    public void addSku2SubmitList(CheckMissedSkuBean bean) {
        List<CheckMissedSkuBean> submitListValue = submitList.getValue();
        if (submitListValue == null) {
            submitListValue = new ArrayList<>();
        }
        boolean needAdd = true;
        for (int i = 0; i < submitListValue.size(); i++) {
            CheckMissedSkuBean item = submitListValue.get(i);
            if (item.isSameBean(bean)) {
                submitListValue.set(i, bean);
                needAdd = false;
                break;
            }
        }
        if (needAdd) {
            submitListValue.add(bean);
        }
        List<CheckMissedSkuBean> filterZero = ListUtils.filter(submitListValue, item ->
                        item != null && item.getInputQty() != null
        );
        submitList.postValue(filterZero);
    }
}
