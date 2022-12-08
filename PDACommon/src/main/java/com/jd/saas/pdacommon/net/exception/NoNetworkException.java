package com.jd.saas.pdacommon.net.exception;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.application.MyApplication;

import java.io.IOException;

/**
 * 无网络错误
 *
 * @author majiheng
 */
public class NoNetworkException extends IOException {

    public NoNetworkException() {
        super(MyApplication.getInstance().getApplicationContext().getString(R.string.net_disconnect));
    }
}
