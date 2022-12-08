package com.jd.saas.pdamain.home;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.databinding.ObservableField;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.notification.NotificationActivity;
import com.jd.saas.pdacommon.notification.NotificationUtil;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.sp.SPUtils;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.upgrade.JdAvatar;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.user.UserTenantConfigBean;
import com.jd.saas.pdamain.R;
import com.jd.saas.pdamain.home.adapter.MainHomeMenuAdapter;
import com.jd.saas.pdamain.home.bean.MainMenuCangBean;
import com.jd.saas.pdamain.home.bean.MainMenuDataBean;
import com.jd.saas.pdamain.home.bean.MainMenuItemBean;
import com.jd.saas.pdamain.home.bean.MainPickingListBean;
import com.jd.saas.pdamain.home.bean.MainPickingRequestBean;
import com.jd.saas.pdamain.home.bean.MainShopCangBean;
import com.jd.saas.pdamain.home.bean.MainShopListBean;
import com.jd.saas.pdamain.home.bean.MainShopListRequestBean;
import com.jd.saas.pdamain.net.MainRepository;
import com.jd.saas.pdamain.router.MainRouterPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页vm
 *
 * @author majiheng
 */
public class MainHomeViewModel extends NetViewModel {

    // 网络请求
    private final MainRepository mMainRepository = new MainRepository();
    // 网络请求到的门店列表
    private List<MainShopListBean> mShopList;
    // 获取到的第一个门店
    public ObservableField<String> mFirstShopName = new ObservableField<>("");
    // 菜单列表adapter
    private MainHomeMenuAdapter mMenuListAdapter;
    // 「拣货」消息轮训定时器
    private Timer mTimer;
    // 「拣货」菜单是否存在
    private boolean mPickingMenuExist = false;
    // 判断「拣货」消息轮询是否需要在onResume生命周期中重启
    private boolean mRestartPickingNotification = false;

    /**
     * 打开个人中心
     */
    public void personalGo(Context context) {
        RouterClient.getInstance().forward(context, "pda://native.PersonalModule/PersonalHomePage");
    }

    /**
     * 获取门店信息：是本地/远端？
     */
    public void getShopInLocalOrRemote(Context context) {
        UserData userData = UserManager.getInstance().getUserData();
        if (!TextUtils.isEmpty(userData.getShopId())) {
            // 用户手动修改保存到local门店id
            mFirstShopName.set(userData.getShopName());
        } else {
            // 用户没有手动修改过门店id
            getShopList(context);
        }
    }

    private void getShopList(BaseObserver<List<MainShopListBean>> observer) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
        MainShopCangBean cangBean = new MainShopCangBean();
        cangBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", cangBean);
        MainShopListRequestBean bean = new MainShopListRequestBean();
        bean.setPin(UserManager.getInstance().getUserData().getUserPin());
        bean.setTenantId(UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("data", bean);
        mMainRepository.getShopList(hashMap, observer);
    }


    /**
     * 跳转到门店选择页面
     */
    public void skipStoreActivity() {
        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(),
                MainRouterPath.HOST_PATH_MAIN_STORE);
    }

    /**
     * 获取门店列表
     */
    public void getShopList(Context context) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        getShopList(new BaseObserver<List<MainShopListBean>>() {

            @Override
            protected void onComplete(boolean error) {
                progressDialog.dismiss();
            }

            @Override
            protected void onSuccess(List<MainShopListBean> bean) {
                if (null != bean) {
                    mShopList = bean;
                    if (mShopList.size() > 0) {
                        UserData userDataTemp = UserManager.getInstance().getUserData();
                        if (TextUtils.isEmpty(userDataTemp.getShopId())) {
                            // 更新返回的第一个门店id到本地用户保存
                            UserData userData = UserManager.getInstance().getUserData();
                            userData.setShopId(mShopList.get(0).getStoreId() + "");
                            userData.setShopName(mShopList.get(0).getStoreName());
                            UserManager.getInstance().setUserData(userData);
                            mFirstShopName.set(mShopList.get(0).getStoreName());
                        }
                    } else {
                        MyToast.show(AppTypeUtil.getAppType() == 1 ? context.getString(R.string.main_shop_empty_err) : context.getString(R.string.main_cang_empty_err), false);
                    }
                } else {
                    MyToast.show(AppTypeUtil.getAppType() == 1 ? context.getString(R.string.main_shop_empty_err) : context.getString(R.string.main_cang_empty_err), false);
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(), false);
            }
        });
    }

    /**
     * 获取菜单列表
     */
    public void getMenus(Context context) {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
        MainMenuCangBean cangBean = new MainMenuCangBean();
        cangBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", cangBean);
        MainMenuDataBean dataBean = new MainMenuDataBean();
        dataBean.setAppCode(AppTypeUtil.getAppType() == 1 ? "storepda" : "warehousepda");
        hashMap.put("data",dataBean);
        mMainRepository.getMenus(hashMap, new NetObserver<List<MainMenuItemBean>>(this) {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(List<MainMenuItemBean> list) {
                if (list == null || list.isEmpty()) {
                    // 没有菜单权限
                    new SimpleAlertDialog.Builder(context, R.string.main_menu_empty).build().show();
                } else {
                    // 有菜单权限
                    getMenuListAdapter().refreshList(list);
                    // 菜单检查
                    menuCheck(list);
                }
            }

            @Override
            protected void onError(NetError error) {
                super.onError(error);
            }
        });
    }

    /**
     * 获取租户全局配置信息
     */
    public void getConfig() {
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId", userData.getTenantId());
        List<String> data = new ArrayList<>();
        // neg_stock_scene是查询是否允许负库存的菜单模块配置
        data.add("neg_stock_scene");
        hashMap.put("data",data);
        mMainRepository.getConfig(hashMap, new BaseObserver<UserTenantConfigBean>() {

            @Override
            protected void onComplete(boolean error) {

            }

            @Override
            protected void onSuccess(UserTenantConfigBean bean) {
                if (null != bean) {
                    UserData userData = UserManager.getInstance().getUserData();
                    userData.setUserTenantConfigBean(bean);
                    UserManager.getInstance().setUserData(userData);
                }
            }

            @Override
            protected void onError(NetError error) {

            }
        });
    }

    /**
     * 检查指定菜单是否存在
     */
    private void menuCheck(List<MainMenuItemBean> menuList) {
        mRestartPickingNotification = true;
        for (MainMenuItemBean menuOuterBean : menuList) {
            for (MainMenuItemBean menuInnerBean : menuOuterBean.getChildren()) {
                if (menuInnerBean.getResourceCode().equalsIgnoreCase("83e884a4-9492-4612-a28d-45e7dca382ef")) {
                    // 菜单中包含「拣货」
                    mPickingMenuExist = true;
                    launchPickingNotification();
                }
            }
        }
    }

    /**
     * 启动「拣货」消息通知，每隔10秒请求一下
     */
    private void launchPickingNotification() {
        mTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getPickingList();
            }
        };
        mTimer.schedule(timerTask, 0, 10000);
    }


    /**
     * 停止消息轮询
     */
    public void stopPickingNotification() {
        if (null != mTimer) {
            mTimer.cancel();
        }
    }

    /**
     * 更新本地数据
     */
    public void updateUserData(MainShopListBean evBean) {
        if (evBean == null) {
            return;
        }
        mFirstShopName.set(evBean.getStoreName());
        UserData userData = UserManager.getInstance().getUserData();
        userData.setShopId(evBean.getStringStoreId());
        userData.setShopName(evBean.getStoreName());
        UserManager.getInstance().setUserData(userData);
    }

    /**
     * 重启消息轮询定时器，为了防止app进入doze模式or页面被系统销毁，定时器失效
     */
    public void restartPickingNotification() {
        // 判断是否重启消息轮询&是否有拣货菜单
        if (mRestartPickingNotification && mPickingMenuExist) {
            stopPickingNotification();
            launchPickingNotification();
        }
    }

    /**
     * 请求「拣货」列表
     */
    private void getPickingList() {
        if (TextUtils.isEmpty(UserManager.getInstance().getUserData().getShopId())) {
            // 如果门店id是空，就不请求「拣货」列表
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
        MainPickingRequestBean requestBean = new MainPickingRequestBean();
        requestBean.setRefNo("");
        requestBean.setWhId(UserManager.getInstance().getUserData().getShopId());
        requestBean.setTab(1);
        requestBean.setDoType(1);
        requestBean.setPage(1);
        requestBean.setPageSize(1);
        requestBean.setSortType(1);
        hashMap.put("data", requestBean);
        mMainRepository.getPickingList(hashMap, new BaseObserver<MainPickingListBean>() {

            @Override
            protected void onSuccess(MainPickingListBean mainPickingListBean) {
                if (null != mainPickingListBean) {
                    if (null != mainPickingListBean.getItemList() && !mainPickingListBean.getItemList().isEmpty()) {
                        String orderId = mainPickingListBean.getItemList().get(0).getRefNo();
                        String orderTemp = SPUtils.getString("PickingOrderId", "");
                        if (!TextUtils.isEmpty(orderId) && !orderId.equalsIgnoreCase(orderTemp)) {
                            // 弹出消息通知，有新订单，需要用户处理
                            Context context = MyApplication.getInstance().getApplicationContext();
                            // 消息通知activity中转页面
                            Intent intent = new Intent(context, NotificationActivity.class);
                            intent.putExtra(NotificationActivity.NOTIFICATION_ACTION, 1);
                            PendingIntent pendingIntent = PendingIntent.getActivity(context, 233, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            // 弹出状态栏通知
                            NotificationUtil notificationUtil = new NotificationUtil(MyApplication.getInstance());
                            notificationUtil.sendNotification(context.getString(R.string.main_home_new_order_notification_title), context.getString(R.string.main_home_new_order_notification_content), pendingIntent);
                            // 赋值给sp暂存
                            SPUtils.saveString("PickingOrderId", orderId);
                        }
                    }
                }
            }

            @Override
            protected void onError(NetError error) {
                // do nothing
            }
        });
    }

    /**
     * 获取菜单列表的adapter
     */
    public MainHomeMenuAdapter getMenuListAdapter() {
        if (null == mMenuListAdapter) {
            mMenuListAdapter = new MainHomeMenuAdapter();
        }
        return mMenuListAdapter;
    }

    /**
     * 检查App更新
     */
    public void appVersionCheck() {
        JdAvatar.forceCheck();
    }
}
