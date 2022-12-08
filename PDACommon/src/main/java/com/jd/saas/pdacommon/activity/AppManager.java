package com.jd.saas.pdacommon.activity;

import android.app.ActivityManager;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.log.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Activity管理器
 *
 * @author majiheng
 */
public class AppManager {

    private final String TAG = AppManager.class.getSimpleName();

    private static Stack<BaseActivity> mActivityStack;
    private static AppManager mAppManager;

    private AppManager() {}

    /**
     * 单实例，UI无需考虑多线程同步问题
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    /**
     * 判断当前app里面是否有activity
     */
    public boolean isActivityEmpty() {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        return mActivityStack.empty();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(BaseActivity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }


    /**
     * 获取当前Activity（栈顶Activity）没有找到则返回null
     */
    public BaseActivity findActivity(Class<?> cls) {
        BaseActivity activity = null;
        for (BaseActivity aty : mActivityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }


    /**
     * 不用于外部
     * 结束指定的Activity(重载)
     */
    void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        boolean flag = false;
        BaseActivity repa = null;
        for (Iterator<BaseActivity> it = mActivityStack.iterator(); it.hasNext(); ) { // reparations为Collection
            repa = it.next();
            if (repa.getClass() == cls) {
                it.remove();
                flag = true;
                break;
            }
        }
        if (flag && repa != null) {
            repa.finish();
            repa = null;
        }
    }


    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            BaseActivity activity = mActivityStack.lastElement();
            finishActivity(activity);
        } else {
            Logger.e(TAG,"call finishActivity fail: mActivityStack is null or empty!!!");
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     */
    public void finishOthersActivity(Class<?> cls) {
        for (BaseActivity activity : mActivityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivityStack == null || mActivityStack.size() == 0) {
            return;
        }
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
        finishFlutterActivity();
    }

    /**
     * 单独关闭Flutter容器，使用事件总线关闭
     */
    public void finishFlutterActivity() {
        EventBean eventBean = new EventBean();
        eventBean.setType(EventBean.FINISH_FLUTTER_ACTIVITY);
        EventBusManager.post(eventBean);
    }

    /**
     * app是否在前面运行
     */
    public boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
