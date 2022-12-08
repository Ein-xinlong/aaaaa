package com.jd.saas.pdamain.store;

import android.text.Editable;
import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdamain.store.adapter.MainStoreAdapter;
import com.jd.saas.pdamain.home.bean.MainShopCangBean;
import com.jd.saas.pdamain.home.bean.MainShopListBean;
import com.jd.saas.pdamain.home.bean.MainShopListRequestBean;
import com.jd.saas.pdamain.net.MainRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainStoreViewModel extends NetViewModel {
    // 网络请求
    private final MainRepository mMainRepository = new MainRepository();
    // 搜索内容
    public ObservableField<String> mSearchContent = new ObservableField<>("");
    // 门店列表adapter
    private MainStoreAdapter mMenuListAdapter;
    //适配器list
    private List<MainShopListBean> adapterBeanList = new ArrayList<>();

    // 是否刷新的标示
    public MutableLiveData<Boolean> mRefresh = new MutableLiveData<>(false);
    /**
     * 获取菜单列表的adapter
     */
    public MainStoreAdapter getStoreListAdapter() {
        if (null == mMenuListAdapter) {
            mMenuListAdapter = new MainStoreAdapter();
            mMenuListAdapter.setOnCheckedChange(new MainStoreAdapter.OnCheckedChange() {
                @Override
                public void checkedChange(MainShopListBean bean) {
                    //把对象传到首页
                    EventBean eventBean = new EventBean();
                    eventBean.setType(EventBean.HOME_STORE_TEXT);
                    eventBean.setData(bean);
                    EventBusManager.post(eventBean);
                    AppManager.getInstance().finishActivity();//结束掉当前页面
                }
            });
        }
        return mMenuListAdapter;
    }

    /**
     * et每次输入字符都调用该方法
     */
    public void searchContent(Editable editable) {
        mSearchContent.set(editable.toString());

        if (TextUtils.isEmpty(mSearchContent.get())) {
            mMenuListAdapter.refreshList(adapterBeanList);
        } else {
            List<MainShopListBean> list = new ArrayList<>();
            for (int i = 0; i < adapterBeanList.size(); i++) {
                if (adapterBeanList.get(i).getStoreName().contains(editable.toString()) || adapterBeanList.get(i).getStringStoreId().contains(editable.toString())) {
                    list.add(adapterBeanList.get(i));
                }
            }
            mMenuListAdapter.refreshList(list);

        }
    }

    /**
     * 获取门店列表
     */
    public void getShopList() {
        mRefresh.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
        MainShopCangBean cangBean = new MainShopCangBean();
        cangBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", cangBean);
        MainShopListRequestBean bean = new MainShopListRequestBean();
        bean.setPin(UserManager.getInstance().getUserData().getUserPin());
        bean.setTenantId(UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("data", bean);
        mMainRepository.getShopList(hashMap, new BaseObserver<List<MainShopListBean>>() {

            @Override
            protected void onComplete(boolean error) {
                mRefresh.setValue(false);
            }

            @Override
            protected void onSuccess(List<MainShopListBean> bean) {
                if (null != bean) {
                    adapterBeanList.clear();
                    adapterBeanList.addAll(bean);
                    mMenuListAdapter.refreshList(bean);
                }
            }

            @Override
            protected void onError(NetError error) {
                mRefresh.setValue(false);
                MyToast.show(error.getMsg(), false);
            }
        });
    }
}
