package com.jd.saas.pdacheck.flow;

import android.graphics.drawable.Drawable;

/**
 * 页面tab的ui状态bean
 *
 * @author majiheng
 */
public class CheckFlowTabUIBean {

    // tab的图标
    private Drawable tabIcon;
    // tab字体颜色是否是暗色
    private boolean tabTextColorDark;
    // tab是否可点击
    private boolean tabClickable;

    public Drawable getTabIcon() {
        return tabIcon;
    }

    public void setTabIcon(Drawable tabIcon) {
        this.tabIcon = tabIcon;
    }

    public boolean getTabTextColorDark() {
        return tabTextColorDark;
    }

    public void setTabTextColorDark(boolean tabTextDark) {
        this.tabTextColorDark = tabTextDark;
    }

    public boolean isTabClickable() {
        return tabClickable;
    }

    public void setTabClickable(boolean tabClickable) {
        this.tabClickable = tabClickable;
    }
}
