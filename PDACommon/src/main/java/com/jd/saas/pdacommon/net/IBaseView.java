package com.jd.saas.pdacommon.net;

/**
 * 网络控制ui，加载中/重试
 *
 * @author majiheng
 */
public interface IBaseView {

    void loading(boolean show);

    void showNetError(NetError error);
}
