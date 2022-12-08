package com.jd.saas.pdacheck.flow.step1.bean;

/**
 * 页面实际使用的预盘单信息
 *
 * @author gouhetong
 */
public class CheckPreOrderBean {
    private String couNo;
    private String createTime;
    private String createUser;

    public String getCouNo() {
        return couNo;
    }

    public void setCouNo(String couNo) {
        this.couNo = couNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckPreOrderBean that = (CheckPreOrderBean) o;

        if (couNo != null ? !couNo.equals(that.couNo) : that.couNo != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;
        return createUser != null ? createUser.equals(that.createUser) : that.createUser == null;
    }

    @Override
    public int hashCode() {
        int result = couNo != null ? couNo.hashCode() : 0;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        return result;
    }
}
