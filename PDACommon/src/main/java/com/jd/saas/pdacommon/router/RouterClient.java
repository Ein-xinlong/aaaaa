package com.jd.saas.pdacommon.router;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.router.beans.HostDO;
import com.jd.saas.pdacommon.router.beans.RouterProtocolDO;
import com.jd.saas.pdacommon.router.enums.HostTypeEnum;
import com.jd.saas.pdacommon.router.exception.InvokeException;
import com.jd.saas.pdacommon.router.formate.ProtocolFormat;
import com.jd.saas.pdacommon.router.handle.AbstractForwardHandle;
import com.jd.saas.pdacommon.router.handle.NativeForwardHandle;
import com.jd.saas.pdacommon.router.jdrouter.JDRouterComponent;
import com.jd.saas.pdacommon.router.jdrouter.RouterComponent;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jingdong.amon.router.module.Letter;

import static com.jd.saas.pdacommon.router.enums.HostTypeEnum.enumValueOf;

/**
 * @author majiheng
 * Description:路由调用入口，未来做为页面跳转中心（web、rn、native等）
 * ****该对象需要共用所以需要在baseCommon层提供统一服务****
 */
public class RouterClient {
    private static RouterClient routerClient = null;

    private RouterClient() {
    }

    /**
     * 实例构造方法，单例模式确保全局只有一个对象
     *
     * @return
     */
    public static RouterClient getInstance() {
        if (routerClient == null) {
            synchronized (RouterClient.class) {
                if (routerClient == null) {
                    routerClient = new RouterClient();
                }
            }
        }
        return routerClient;
    }

    /**
     * 对外暴露router其它能力 带参数
     *
     * @param context
     * @param model       不包含type
     * @param path
     * @param paramBundle
     * @return
     */
    public Letter externalRouter(Context context, String model, String path, Bundle paramBundle) {
        RouterComponent routerComponent = new JDRouterComponent();
        return routerComponent.getRouterLetter(context, model, path, paramBundle);
    }

    /**
     * 对外暴露router其它能力 不带参数
     *
     * @param context
     * @param model   不包含type
     * @param path
     * @return
     */
    public Letter externalRouter(Context context, String model, String path) {
        return externalRouter(context, model, path, null);
    }

    /**
     * 对外暴露router其它能力 入参为完整路由地址
     *
     * @param context
     * @param routerUrl host 不能配置type，只有跳转时需要
     * @return
     */
    public Letter externalRouter(Context context, String routerUrl) {
        RouterComponent routerComponent = new JDRouterComponent();
        return routerComponent.getRouterLetter(context, routerUrl);
    }

    /**
     * 跳转入口 带参数
     * 该方法只做相关地址分发操作，自己没有实际处理能力
     *
     * @param context
     * @param typeEnum
     * @param model
     * @param path
     * @param paramBundle
     */
    public void forward(Context context, HostTypeEnum typeEnum, String model, String path, Bundle paramBundle) {
        executeForward(context, typeEnum, model, path, paramBundle);
    }

    /**
     * 跳转入口 带参数
     * 该方法只做相关地址分发操作，自己没有实际处理能力
     *
     * @param context
     * @param typeEnum
     * @param model
     * @param path
     */
    public void forward(Context context, HostTypeEnum typeEnum, String model, String path) {
        forward(context, typeEnum, model, path, null);
    }

    /**
     * 跳转入口
     * 该方法只做相关地址分发操作，自己没有实际处理能力
     *
     * @param context
     * @param routerUrl
     */
    public void forward(Context context, String routerUrl, Bundle paramBundle) {
        executeForward(context, routerUrl, paramBundle);
    }

    /**
     * 跳转入口
     * 该方法只做相关地址分发操作，自己没有实际处理能力
     *
     * @param context
     * @param routerUrl
     */
    public void forward(Context context, String routerUrl, Bundle paramBundle, int requestCode) {
        executeForward(context, routerUrl, paramBundle, requestCode);
    }

    /**
     * 跳转入口
     * 该方法只做相关地址分发操作，自己没有实际处理能力
     *
     * @param context
     * @param routerUrl
     */
    public void forward(Context context, String routerUrl) {
        executeForward(context, routerUrl, null);
    }

    /**
     * 统一执行跳转相关逻辑，实现方法共用
     *  @param isRouterUrlTarget
     * @param context
     * @param routerUrl
     * @param typeEnum
     * @param model
     * @param path
     * @param paramBundle
     * @param requestCode
     */
    private void execute(boolean isRouterUrlTarget, Context context, String routerUrl, HostTypeEnum typeEnum, String model, String path, Bundle paramBundle, Integer requestCode) {
        try {
            HostTypeEnum hostTypeEnum;
            if (isRouterUrlTarget) {
                RouterProtocolDO protocolDO = ProtocolFormat.routerUrlToObj(routerUrl);
                HostDO hostDO = ProtocolFormat.hostToObj(protocolDO.getHost());
                hostTypeEnum = enumValueOf(hostDO.getType());
            } else {
                hostTypeEnum = typeEnum;
            }
            AbstractForwardHandle forwardHandle;
            switch (hostTypeEnum) {
                case NATIVE:
                    //原生处理
                    forwardHandle = new NativeForwardHandle();
                    if (isRouterUrlTarget) {
                        forwardHandle.execute(context, routerUrl, paramBundle, requestCode);
                    } else {
                        forwardHandle.execute(context, model, path, paramBundle);
                    }
                    break;
                case WEB:
                    //webview处理
                    MyToast.show("路由未实现webview跳转",false);
                    break;
                case RN:
                    //跳转到rn
                    MyToast.show( "路由未实现RN跳转",false);
                    break;
                default:
                    MyToast.show( "没有找到匹配的协议,请检查协议类型!",false);
                    break;
            }
        } catch (Exception e) {
            String msg;
            if (e instanceof InvokeException) {
                msg = ((InvokeException) e).getMsg();
            } else {
                msg = "路由跳转失败!";
                //异常处理 打印日志+提示
                Logger.e("router:",e.toString());
            }
            MyToast.show(e.getMessage(),false);
        }
    }

    public Fragment registerFragment(Context context, String routerUrl) {
        Fragment fragment = null;

        try {
            RouterProtocolDO protocolDO = ProtocolFormat.routerUrlToObj(routerUrl);
            HostDO hostDO = ProtocolFormat.hostToObj(protocolDO.getHost());
            HostTypeEnum hostTypeEnum = enumValueOf(hostDO.getType());
            switch(hostTypeEnum) {
                case NATIVE:
                    AbstractForwardHandle forwardHandle = new NativeForwardHandle();
                    fragment = forwardHandle.execute(routerUrl);
                    break;
                case WEB:
                    MyToast.show( "路由未实现webview fragment注册",false);
                    break;
                case RN:
                    MyToast.show( "路由未实现RN fragment注册",false);
                    break;
                default:
                    MyToast.show( "没有找到匹配的协议,请检查协议类型!",false);
            }
        } catch (Exception var8) {
            String msg;
            if (var8 instanceof InvokeException) {
                msg = ((InvokeException)var8).getMsg();
            } else {
                msg = "路由跳转失败!";
                Logger.e("router:",var8.toString());
            }

            MyToast.show(var8.getMessage(),false);
        }

        return fragment;
    }

    /**
     * 执行跳转相关逻辑
     *
     * @param context
     * @param routerUrl
     */
    private void executeForward(Context context, String routerUrl, Bundle paramBundle) {
        execute(true, context, routerUrl, null, null, null, paramBundle, null);
    }

    /**
     * 执行跳转相关逻辑
     *
     * @param context
     * @param routerUrl
     */
    private void executeForward(Context context, String routerUrl, Bundle paramBundle, int requestCode) {
        execute(true, context, routerUrl, null, null, null, paramBundle, requestCode);
    }

    /**
     * 执行跳转相关逻辑
     *
     * @param context
     * @param typeEnum
     * @param model
     * @param path
     * @param paramBundle
     */
    private void executeForward(Context context, HostTypeEnum typeEnum, String model, String path, Bundle paramBundle) {
        execute(false, context, null, typeEnum, model, path, paramBundle, null);
    }
}
