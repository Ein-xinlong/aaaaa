package com.example.pdalogin.enterprise;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pdalogin.R;
import com.example.pdalogin.adapter.LoginChooseEnterpriseAdapter;
import com.example.pdalogin.bean.LoginChoseEnterpriseBean;
import com.example.pdalogin.bean.LoginJnosTokenResponseUserAccountBean;
import com.example.pdalogin.net.LoginRepository;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.upgrade.JdAvatar;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.HashMap;
import java.util.List;

import jd.jnos.loginsdk.JNosLoginHelper;
import jd.jnos.loginsdk.common.listener.ErrorResult;
import jd.jnos.loginsdk.common.listener.OnCommonCallback;

/***
 * 选择企业ViewModel
 */
public class LoginChooseEnterpriseViewModel extends NetViewModel {

    // 网络请求
    private final LoginRepository mLoginRepository = new LoginRepository();
    private LoginChooseEnterpriseAdapter mLoginChooseEnterpriseAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    // 是否显示悬浮加载dialog
    public MutableLiveData<Boolean> mShowLoadingDialog = new MutableLiveData<>(false);

    /**
     * 创建企业列表适配器
     */
    public LoginChooseEnterpriseAdapter getLoginChooseEnterpriseAdapter() {
        if (null == mLoginChooseEnterpriseAdapter) {
            mLoginChooseEnterpriseAdapter = new LoginChooseEnterpriseAdapter();
            mLoginChooseEnterpriseAdapter.setOnItemClick(new LoginChooseEnterpriseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(LoginChoseEnterpriseBean bean) {
                    getJNOSTokenByAccountId(bean);
                }
            });
        }
        return mLoginChooseEnterpriseAdapter;
    }

    /**
     * 创建企业布局管理器
     */
    public LinearLayoutManager getLinearLayoutManager(Context context) {
        if (null == mLinearLayoutManager) {
            mLinearLayoutManager = new LinearLayoutManager(context);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return mLinearLayoutManager;
    }

    /**
     * 获取最新的JNOSToken
     */
    private void getJNOSTokenByAccountId(LoginChoseEnterpriseBean loginChoseEnterpriseBean) {
        mShowLoadingDialog.setValue(true);
        JNosLoginHelper.INSTANCE.getLoginHelper().chooseOrg(loginChoseEnterpriseBean.getAccountId(), false, new OnCommonCallback<Object>() {
            @Override
            public void onSuccess() {
                mShowLoadingDialog.setValue(false);
                // 当账号属于「单个组织」时，获取的是token
                String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getToken();
                // 获取用户信息
                getUserInfoByJNOSToken(jnosToken,loginChoseEnterpriseBean);
            }

            @Override
            public void onSuccess(Object o) {
                mShowLoadingDialog.setValue(false);
                // 当账号属于「多组织」时，获取的是middle token
                String jnosToken = JNosLoginHelper.INSTANCE.getLoginHelper().getMiddleToken();
                // 获取用户信息
                getUserInfoByJNOSToken(jnosToken,loginChoseEnterpriseBean);
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
    }

    /**
     * 获取用户信息：mobile、userName(中文)、userPin
     */
    private void getUserInfoByJNOSToken(String JNOSToken,LoginChoseEnterpriseBean loginChoseEnterpriseBean) {
        // 刷新本地token为最新
        UserData userData = UserManager.getInstance().getUserData();
        userData.setToken(JNOSToken);
        UserManager.getInstance().setUserData(userData);
        // 请求网络获取用户信息
        mShowLoadingDialog.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        mLoginRepository.getAccountByJNOSToken(hashMap, new NetObserver<LoginJnosTokenResponseUserAccountBean>(this) {

            @Override
            protected void onComplete(boolean error) {
                mShowLoadingDialog.setValue(false);
            }

            @Override
            protected void onSuccess(LoginJnosTokenResponseUserAccountBean bean) {
                // 保存租户信息tenantId、用户名、用户pin、用户mobile到本地
                UserData userData = UserManager.getInstance().getUserData();
                userData.setTenantId(loginChoseEnterpriseBean.getTenantId() + "");
                userData.setUserName(bean.getUserName());
                userData.setUserPin(bean.getUserPin());
                userData.setMobile(bean.getMobile());
                UserManager.getInstance().setUserData(userData);
                // 更新Avatar监控
                JdAvatar.updateUserId(bean.getUserPin());
                // 跳转到首页
                RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), "pda://native.MainModule/MainNewPage");
                AppManager.getInstance().finishActivity();
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 请求租户列表
     */
    public void requestData() {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        mLoginRepository.select(hashMap, new NetObserver<List<LoginChoseEnterpriseBean>>(this) {
            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(List<LoginChoseEnterpriseBean> list) {
                if (!list.isEmpty()) {
                    if(list.size() == 1) {
                        // 只有一条租户信息
                        LoginChoseEnterpriseBean single = list.get(0);
                        getJNOSTokenByAccountId(single);
                    }else {
                        // 多条租户信息
                        getLoginChooseEnterpriseAdapter().refreshList(list);
                    }
                }else {
                    MyToast.show(R.string.login_company_empty,false);
                }
            }
        });
    }
}
