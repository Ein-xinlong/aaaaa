package com.jd.saas.pdacheck.record;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableField;

import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.check.CheckGoodsCheckActivity;
import com.jd.saas.pdacheck.check.CheckGoodsCheckViewModel;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckCommitRequestBean;
import com.jd.saas.pdacheck.net.CheckListRepository;
import com.jd.saas.pdacheck.record.adapter.CheckRecordAdapter;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 盘点记录提交vm
 *
 * @author majiheng
 */
public class CheckRecordViewModel extends NetViewModel {

    // 创建网络
    private final CheckListRepository mRepository = new CheckListRepository();
    // list适配器
    private CheckRecordAdapter mAdapter;
    // 全局盘点详情
    public ObservableField<CheckListBean> mDetail = new ObservableField<>();

    /**
     * 获取传递到该页面的bundle中，然后刷新ui
     */
    public void getBundleContent(Bundle bundle) {
        if(null != bundle) {
            CheckListBean bean = (CheckListBean) bundle.get("data");
            refreshUI(bean);
        }else {
            MyToast.show(R.string.check_record_check_record_err,false);
        }
    }

    /**
     * 获取列表adapter
     */
    public CheckRecordAdapter getListAdapter() {
        if (null == mAdapter) {
            mAdapter = new CheckRecordAdapter();
        }
        return mAdapter;
    }

    /**
     * 刷新ui
     */
    public void refreshUI(CheckListBean bean) {
        mDetail.set(bean);
        getListAdapter().refreshList(CheckGoodsCheckViewModel.mCheckedListForCommon);
    }

    /**
     * 提交盘点记录
     */
    public void commit(Context context) {
        if(null != CheckGoodsCheckViewModel.mCheckedListForCommon && CheckGoodsCheckViewModel.mCheckedListForCommon.size() != 0) {
            new SimpleAlertDialog.Builder(context,R.string.check_record_confirm_notice)
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showProgress.set(true);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            UserData userData = UserManager.getInstance().getUserData();
                            hashMap.put("tenantId", userData.getTenantId());
                            hashMap.put("pin", userData.getUserPin());
                            CheckCommitRequestBean bean = new CheckCommitRequestBean();
                            bean.setCreateBy(mDetail.get().getCreateBy());
                            bean.setPin(userData.getUserPin());
                            bean.setTenantId(userData.getTenantId());
                            bean.setWhId(userData.getShopId());
                            bean.setCouNo(mDetail.get().getConNo());
                            bean.setSource("1");// 1:pda设备
                            bean.setTaskNo(mDetail.get().getTaskNo());
                            bean.setSkuType(mDetail.get().getSkuType());
                            bean.setDeptCode("3");
                            bean.setCreateDate(mDetail.get().getCreateDate());
                            // 拆单过程，本来应该网关来做！将来拆单过程放到了网关，移除这块代码即可
                            // 拆单后最终要提交的列表
                            List<CheckCommitRequestBean.PdaStockTakeInfoDetailBO> finalCommitList = new ArrayList<>();
                            for(CheckCommitRequestBean.PdaStockTakeInfoDetailBO fatherBean : CheckGoodsCheckViewModel.mCheckedListForCommon) {
                                if(null != fatherBean.getBoxProducts() && fatherBean.getBoxProducts().size() != 0) {
                                    // 组合商品-拆单
                                    List<CheckCommitRequestBean.PdaStockTakeInfoDetailBO> sonBeans = fatherBean.getBoxProducts();
                                    finalCommitList.addAll(sonBeans);
                                }else {
                                    // 普通商品
                                    finalCommitList.add(fatherBean);
                                }
                            }
                            bean.setCouDetailList(finalCommitList);
                            hashMap.put("data", bean);
                            mRepository.commit(hashMap, new NetObserver<Object>(CheckRecordViewModel.this) {

                                @Override
                                protected void onComplete(boolean error) {
                                    showProgress.set(false);
                                }

                                @Override
                                protected void onSuccess(Object o) {
                                    // 提示用户成功
                                    MyToast.show(R.string.check_record_confirm_success,false);
                                    // 清空提交列表
                                    CheckGoodsCheckViewModel.mCheckedListForCommon.clear();
                                    // 通知盘点列表页&预盘点列表页刷新列表
                                    EventBean eventBean = new EventBean();
                                    eventBean.setType(EventBean.EVENT_REFRESH_LIST);
                                    EventBusManager.post(eventBean);
                                    // 关闭盘点商品页面
                                    AppManager.getInstance().finishActivity(CheckGoodsCheckActivity.class);
                                    // 关闭当前页面
                                    AppManager.getInstance().finishActivity(CheckRecordActivity.class);
                                }
                            });
                        }
                    }).build().show();
        }else {
            MyToast.show(R.string.check_record_confirm_err,false);
        }
    }
}
