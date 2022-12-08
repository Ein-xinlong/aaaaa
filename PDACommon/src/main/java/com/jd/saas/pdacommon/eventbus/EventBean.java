package com.jd.saas.pdacommon.eventbus;

/**
 * event bus事件携带bean
 *
 * @author majiheng
 */
public class EventBean<T> {

    public static final int EVENT_REFRESH_LIST = 0;
    public static final int HOME_STORE_TEXT = 1;
    public static final int FINISH_FLUTTER_ACTIVITY = 2;
    public static final int EVENT_CHECK_SWITCH_TABS = 3;
    public static final int EVENT_CHECK_NEW_TASK = 4;

    private int type;
    private T data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
