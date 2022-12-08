package com.jd.saas.pdacommon.net;

/**
 * 网络错误，msg/code
 *
 * @author majiheng
 */
public class NetError {

    private String msg;
    private int code;

    public NetError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
