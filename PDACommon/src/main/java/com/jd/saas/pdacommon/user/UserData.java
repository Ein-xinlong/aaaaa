package com.jd.saas.pdacommon.user;

import android.text.TextUtils;

/**
 * 用户数据
 *
 * @author majiheng
 */
public class UserData {

    // token
    private String token;
    // 用户pin
    private String userPin;
    // 用户手机号
    private String mobile;
    // 租户tenantId
    private String tenantId;
    // 门店id
    private String shopId;
    // 门店name
    private String shopName;
    // 用户名（员工名）
    private String userName;
    // 租户配置bean
    private UserTenantConfigBean userTenantConfigBean;

    public String getMobile() {
        if(TextUtils.isEmpty(mobile)) {
            mobile = "";
        }
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserTenantConfigBean getUserTenantConfigBean() {
        if(null == userTenantConfigBean) {
            return new UserTenantConfigBean();
        }
        return userTenantConfigBean;
    }

    public void setUserTenantConfigBean(UserTenantConfigBean userTenantConfigBean) {
        this.userTenantConfigBean = userTenantConfigBean;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
