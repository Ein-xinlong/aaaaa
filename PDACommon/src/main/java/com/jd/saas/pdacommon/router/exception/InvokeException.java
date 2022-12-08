package com.jd.saas.pdacommon.router.exception;

/**
 * @author majiheng
 * Description:自定义异常类型，用于区分已识别的异常，放入公共层
 */
public class InvokeException extends RuntimeException {
    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public InvokeException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public InvokeException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }

    public InvokeException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }
}