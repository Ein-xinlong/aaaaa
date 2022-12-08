package com.jd.saas.padinventory.adjustment;

import android.content.Context;

import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.padinventory.net.AdjustmentRepository;
import com.jd.saas.padinventory.net.InventoryPdaTwoBean;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.HashMap;

/**
 * 调整单详情ViewModel
 *
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
public class InventoryAdjustmentViewModel extends NetViewModel {
    private InventoryAdjustmentAdapter mInventoryAdjustmentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private AdjustmentRepository mAdjustmentRepository = new AdjustmentRepository();
    public ObservableField<String> mOddNumber = new ObservableField<>();
    public ObservableField<String> mApplyName = new ObservableField<>();
    public ObservableField<String> mApplyTime = new ObservableField<>();

    public ObservableField<Boolean> mForty=new ObservableField<>(false);//判断status是否是40
    public ObservableField<Boolean> mFifty=new ObservableField<>(false);//判断status是否为50
    /**
     * 创建适配器
     */
    public InventoryAdjustmentAdapter getmInventoryAdjustmentAdapter() {
        if (null == mInventoryAdjustmentAdapter) {
            mInventoryAdjustmentAdapter = new InventoryAdjustmentAdapter();
        }
        return mInventoryAdjustmentAdapter;
    }

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
     * 网络请求添加数据
     */
    public void addList(String galNo) {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        InventoryAdjustmentRepostBean bean = new InventoryAdjustmentRepostBean();
        bean.setGalNo(galNo);
        bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
        InventoryPdaTwoBean inventoryPdaTwoBean =new InventoryPdaTwoBean();
        inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", inventoryPdaTwoBean);
        hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("data", bean);
        mAdjustmentRepository.detail(hashMap, new NetObserver<InventoryAdjustmentItemBean>(this) {
            @Override
            protected void onSuccess(InventoryAdjustmentItemBean inventoryAdjustmentItemBean) {
                if (inventoryAdjustmentItemBean != null) {
                    mOddNumber.set(inventoryAdjustmentItemBean.getGalNo());
                    mApplyName.set(inventoryAdjustmentItemBean.getCreateName());
                    mApplyTime.set(inventoryAdjustmentItemBean.getCreateStartTime());
                    String status = inventoryAdjustmentItemBean.getStatus();
                    if (status.equals("40")){
                        mForty.set(true);
                    }else if (status.equals("50")){
                        mFifty.set(true);
                    }
                    getmInventoryAdjustmentAdapter().refreshList(inventoryAdjustmentItemBean.getSkuBoPageExt().getData());
                }
            }
            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

        });
    }

}
