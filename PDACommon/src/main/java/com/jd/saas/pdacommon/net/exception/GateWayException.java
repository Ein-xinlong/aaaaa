package com.jd.saas.pdacommon.net.exception;

import androidx.annotation.NonNull;

import com.jd.saas.pdacommon.net.BaseResponse;

public class GateWayException extends Exception {
    private final int code;

    public GateWayException(int code, String message) {
        super(message);
        this.code = code;
    }

    public <T> GateWayException(@NonNull BaseResponse<T> response) {
        this(response.getCode(), response.getMsg());
    }

}
