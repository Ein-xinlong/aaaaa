package com.jd.saas.pdacommon.webview;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 通用WebView打开时的配置信息
 *
 * @author majiheng
 */
public class PDAWebViewConfigBean implements Serializable {

    // 是否显示h5标题栏
    private boolean showTitle;
    // h5页面标题
    private String title;
    // h5页面初始地址
    private String url;

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public String getTitle() {
        if(TextUtils.isEmpty(title)) {
            title = "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        if(TextUtils.isEmpty(url)) {
            url = "";
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
