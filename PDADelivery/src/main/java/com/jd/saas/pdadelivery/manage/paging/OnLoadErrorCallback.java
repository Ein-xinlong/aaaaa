package com.jd.saas.pdadelivery.manage.paging;

import com.jd.saas.pdacommon.net.NetError;

public interface OnLoadErrorCallback {
    void onError(NetError error);
}
