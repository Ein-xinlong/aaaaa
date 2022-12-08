package com.example.pdalogin.bean;

/**
 * 根据jnos token获取用户信息出参
 *
 * @author majiheng
 */
public class LoginJnosTokenResponseUserAccountBean {

    private String userName;
    private String userPin;
    private String mobile;

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
