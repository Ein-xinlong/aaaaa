package com.jd.saas.pdacheck.flow.step1.paging;

import com.jd.saas.pdacommon.net.NetError;

/**
 * 加载失败的回调
 *
 * @author gouhetong
 */
public interface CheckPagingErrorCallback {
    void onError(NetError error);
}
