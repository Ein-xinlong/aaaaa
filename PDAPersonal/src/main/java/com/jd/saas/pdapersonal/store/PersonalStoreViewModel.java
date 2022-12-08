package com.jd.saas.pdapersonal.store;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.exchange.bean.PersonalCompanyItemBean;
import com.jd.saas.pdapersonal.net.PersonalRepository;
import com.jd.saas.pdapersonal.store.adapter.PersonalStoreAdapter;
import com.jd.saas.pdapersonal.store.bean.PersonalShopCangBean;
import com.jd.saas.pdapersonal.store.bean.PersonalNewShopListBean;
import com.jd.saas.pdapersonal.store.bean.PersonalShopListRequestBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jd.jnos.loginsdk.JNosLoginHelper;
import jd.jnos.loginsdk.common.listener.ErrorResult;
import jd.jnos.loginsdk.common.listener.OnCommonCallback;

public class PersonalStoreViewModel extends NetViewModel {
    // 网络请求
    private final PersonalRepository mMainRepository = new PersonalRepository();
    // 搜索内容
    public ObservableField<String> mSearchContent = new ObservableField<>("");
    // 门店列表adapter
    private PersonalStoreAdapter mMenuListAdapter;
    //适配器list
    private List<PersonalNewShopListBean> adapterBeanList = new ArrayList<>();
    // 是否刷新的标示
    public MutableLiveData<Boolean> mRefresh = new MutableLiveData<>(false);
    //接受传过来的数据
    private PersonalCompanyItemBean itemBean;
    // 是否显示悬浮加载dialog
    public MutableLiveData<Boolean> mShowLoadingDialog = new MutableLiveData<>(false);

    /**
     * 获取门店列表的adapter
     */
    public PersonalStoreAdapter getStoreListAdapter(Context context) {
        if (null == mMenuListAdapter) {
            mMenuListAdapter = new PersonalStoreAdapter();
            mMenuListAdapter.setOnCheckedChange(new PersonalStoreAdapter.OnCheckedChange() {
                @Override
                public void checkedChange(PersonalNewShopListBean bean) {
                    getJNOSTokenByAccountId(bean);
                }
            });
        }
        return mMenuListAdapter;
    }

    /**
     * 获取最新的JNOSToken
     * */
    private void getJNOSTokenByAccountId(PersonalNewShopListBean bean) {
        if(null != itemBean) {
            mShowLoadingDialog.setValue(true);
            JNosLoginHelper.INSTANCE.getLoginHelper().chooseOrg(itemBean.getAccountId(), false, new OnCommonCallback<Object>() {
                @Override
                public void onSuccess() {
                    mShowLoadingDialog.setValue(false);
                    // 当账号属于「单个组织」时，获取的是token
                    String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getToken();
                    // 获取用户信息
                    setUserInfoAndReboot(jnosToken,bean);
                }

                @Override
                public void onSuccess(Object o) {
                    mShowLoadingDialog.setValue(false);
                    // 当账号属于「多组织」时，获取的是middle token
                    String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getMiddleToken();
                    // 获取用户信息
                    setUserInfoAndReboot(jnosToken,bean);
                }

                @Override
                public void onError(@NonNull ErrorResult errorResult) {
                    mShowLoadingDialog.setValue(false);
                    MyToast.show(errorResult.getErrorMsg(),false);
                }

                @Override
                public void onFail(@NonNull ErrorResult errorResult) {
                    mShowLoadingDialog.setValue(false);
                    MyToast.show(errorResult.getErrorMsg(),false);
                }
            });
        }else {
            MyToast.show("AccountId is null.",false);
        }
    }

    /**
     * 保存门店租户信息并且重启app
     * */
    private void setUserInfoAndReboot(String JNOSToken, PersonalNewShopListBean bean) {
        //更新token、门店id&名称、租户
        UserData userData = UserManager.getInstance().getUserData();
        userData.setToken(JNOSToken);
        userData.setShopId(bean.getStringStoreId());
        userData.setShopName(bean.getStoreName());
        userData.setTenantId(bean.getTenantId() + "");
        UserManager.getInstance().setUserData(userData);
        // 关闭所有页面
        AppManager.getInstance().finishAllActivity();
        // 跳转到首页
        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), "pda://native.MainModule/MainNewPage");
    }

    /**
     * et每次输入字符都调用该方法
     */
    public void searchContent(Editable editable) {
        mSearchContent.set(editable.toString());
        if (TextUtils.isEmpty(mSearchContent.get())) {
            mMenuListAdapter.refreshList(adapterBeanList);
        } else {
            List<PersonalNewShopListBean> list = new ArrayList<>();
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
    public void getShopList(Bundle bundle) {
        if (bundle.get("data") == null) {
            return;
        }
        itemBean = (PersonalCompanyItemBean) bundle.get("data");
        mRefresh.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
        PersonalShopCangBean cangBean = new PersonalShopCangBean();
        cangBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", cangBean);
        PersonalShopListRequestBean bean = new PersonalShopListRequestBean();
        bean.setPin(UserManager.getInstance().getUserData().getUserPin());
        bean.setTenantId(itemBean.getTenantId() + "");
        bean.setAccountId(itemBean.getAccountId());
        hashMap.put("data", bean);
        mMainRepository.getShopList(hashMap, new BaseObserver<List<PersonalNewShopListBean>>() {

            @Override
            protected void onComplete(boolean error) {
                mRefresh.setValue(false);
            }

            @Override
            protected void onSuccess(List<PersonalNewShopListBean> bean) {
                if (null != bean) {
                    adapterBeanList.clear();
                    adapterBeanList.addAll(bean);
                    mMenuListAdapter.refreshList(bean);
                } else {
                    Context context = MyApplication.getInstance().getApplicationContext();
                    MyToast.show(AppTypeUtil.getAppType() == 1 ? context.getString(R.string.personal_shop_empty_err) : context.getString(R.string.personal_cang_empty_err), false);
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
