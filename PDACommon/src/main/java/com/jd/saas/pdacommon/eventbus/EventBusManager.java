package com.jd.saas.pdacommon.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * event bus管理类
 *
 * @author majiheng
 */
public class EventBusManager {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void post(Object o) {
        EventBus.getDefault().post(o);
    }

    public static void postSticky(Object o){
        EventBus.getDefault().postSticky(o);
    }
    public static void removeStickyMsg(Object o){
        EventBus.getDefault().removeStickyEvent(o);
    }
}
