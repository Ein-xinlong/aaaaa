package com.jd.saas.pdacheck.flow.step3.dialog;

import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacheck.flow.step3.CheckReviewConvertor;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderBean;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuBean;
import com.jd.saas.pdacheck.flow.step3.repo.CheckReviewRepository;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 复核修改商品数量的弹窗vm
 *
 * @author gouhetong
 */
public class CheckReviewEditSkuPreOrderViewModel extends NetViewModel {
    private final CheckReviewRepository repository;
    public CheckReviewSkuBean bean;
    public final MutableLiveData<List<CheckReviewPreOrderBean>> orderList = new MutableLiveData<>();
    public final MutableLiveData<Boolean> closeEvent = new MutableLiveData<>();

    public CheckReviewEditSkuPreOrderViewModel(CheckReviewRepository repository) {
        this.repository = repository;
    }

    public void init(String taskNo, CheckReviewSkuBean skuBean) {
        this.bean = skuBean;
        repository.getPreOrderList(
                new CheckReviewPreOrderListParam(taskNo, skuBean.getSkuId(), skuBean.getLocNo()),
                new BaseObserver<List<CheckReviewPreOrderResult>>() {
                    @Override
                    protected void onSuccess(List<CheckReviewPreOrderResult> result) {
                        if (result == null) {
                            orderList.postValue(new ArrayList<>());
                        } else {
                            orderList.postValue(ListUtils.map(result, CheckReviewConvertor::convert));
                        }
                    }

                    @Override
                    protected void onError(NetError error) {

                    }
                });
    }

    public void submit(List<CheckReviewPreOrderBean> orderBeans) {
        if (orderBeans.isEmpty()) {
            closeEvent.postValue(false);
            return;
        }
        List<CheckReviewPreOrderBean> diff = ListUtils.filter(orderBeans, item -> item.getActualQty().compareTo(item.getInputQty()) != 0);
        if (diff.isEmpty()) {
            closeEvent.postValue(false);
            return;
        }
        showProgress.set(true);
        repository.submitPreOrderList(ListUtils.map(diff, CheckReviewConvertor::convert), new BaseObserver<Boolean>() {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(Boolean aBoolean) {
                closeEvent.postValue(true);
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }
        });

    }
}
