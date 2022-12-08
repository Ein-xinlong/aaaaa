package com.jd.saas.padinventory.adjustment.detail;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.create.InventoryCreateActivity;
import com.jd.saas.padinventory.create.InventoryCreateViewModel;
import com.jd.saas.padinventory.net.AdjustmentRepository;
import com.jd.saas.padinventory.net.InventoryPdaTwoBean;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 调整单明细vm
 *
 * @author: ext.anxinlong
 * @date: 2021/6/1
 */
public class InventoryAdjustmentDetailViewModel extends NetViewModel {

    // 网络
    private final AdjustmentRepository mAdjustmentRepository = new AdjustmentRepository();
    // list view相关
    private InventoryAdjustmentDetailAdapter mDetailAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * 创建布局管理器
     */
    public LinearLayoutManager getLinearLayoutManager(Context context) {
        if (null == mLinearLayoutManager) {
            mLinearLayoutManager = new LinearLayoutManager(context);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return mLinearLayoutManager;
    }

    /**
     * 创建适配器
     */
    public InventoryAdjustmentDetailAdapter getDetailAdapter() {
        if (null == mDetailAdapter) {
            mDetailAdapter = new InventoryAdjustmentDetailAdapter();
        }
        return mDetailAdapter;
    }

    /**
     * 展示列表
     */
    public void refreshUI() {
        getDetailAdapter().refreshList(InventoryCreateViewModel.mAdjustmentList);
    }

    /**
     * 关闭当前页
     */
    public void onClick() {
        AppManager.getInstance().finishActivity();
    }

    public void commit() {
        if (InventoryCreateViewModel.mAdjustmentList.get().size() == 0) {
            MyToast.show(R.string.inventory_create_place_commit, false);
            return;
        }
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("storeId", UserManager.getInstance().getUserData().getShopId());
        hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());

        List<InventoryAdjustmentDetailAddBean> list = new ArrayList<>();
        for (int i = 0; i < InventoryCreateViewModel.mAdjustmentList.get().size(); i++) {
            String skuID = InventoryCreateViewModel.mAdjustmentList.get().get(i).getSkuID();//商品编号
            String storage_location_cause = InventoryCreateViewModel.mAdjustmentList.get().get(i).getStorage_location_cause();//reasonCode损益原因
            String storage_location = InventoryCreateViewModel.mAdjustmentList.get().get(i).getStorage_location();//locId库位
            String storage_location_number = InventoryCreateViewModel.mAdjustmentList.get().get(i).getStorage_location_number();//报溢数量proLossNum
            String profitOrLossCode = InventoryCreateViewModel.mAdjustmentList.get().get(i).getProfitOrLossCode();//报溢类型profitOrLossCode
            String storage_location_cause_name = InventoryCreateViewModel.mAdjustmentList.get().get(i).getStorage_location_cause_name();
            InventoryAdjustmentDetailAddBean bean = new InventoryAdjustmentDetailAddBean();
            bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
            bean.setLocId(storage_location);
            bean.setProfitOrLossCode(profitOrLossCode);
            bean.setReasonCode(storage_location_cause);
            bean.setProLossNum(storage_location_number);
            bean.setReason(storage_location_cause_name);
            bean.setSkuId(skuID);
            list.add(bean);
        }
        hashMap.put("data", list);
        InventoryPdaTwoBean inventoryPdaTwoBean = new InventoryPdaTwoBean();
        inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", inventoryPdaTwoBean);
        mAdjustmentRepository.add(hashMap, new NetObserver<Boolean>(this) {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(Boolean success) {
                if (success) {
                    MyToast.show(R.string.inventory_create_commit_fail, false);
                } else {
                    MyToast.show(R.string.inventory_create_commit_success, false);
                    // 清空提交列表
                    InventoryCreateViewModel.mAdjustmentList.get().clear();
                    // 通知商品详情页面刷新列表
                    EventBean eventBean = new EventBean();
                    eventBean.setType(EventBean.EVENT_REFRESH_LIST);
                    EventBusManager.post(eventBean);
                    // 关闭当前页面
                    AppManager.getInstance().finishActivity();
                    AppManager.getInstance().finishActivity(InventoryCreateActivity.class);
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }
        });
    }
}
