package com.jd.saas.pdacheck.flow.step3.paging;

import com.jd.saas.pdacommon.net.NetError;

public interface CheckOnReviewSkuListLoadErrorCallback {
    void onError(NetError error);
}
