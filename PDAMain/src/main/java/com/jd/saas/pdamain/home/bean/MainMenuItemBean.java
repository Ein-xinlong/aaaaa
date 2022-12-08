package com.jd.saas.pdamain.home.bean;

import java.util.List;

/**
 * 首页菜单条目bean
 *
 * @author majiheng
 */
public class MainMenuItemBean {

    // 菜单item类型
    private String resourceCode;
    // 菜单item名称
    private String resourceName;
    // 菜单里的menu网格列表
    List<MainMenuItemBean> children;

    public List<MainMenuItemBean> getChildren() {
        return children;
    }

    public void setChildren(List<MainMenuItemBean> children) {
        this.children = children;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
