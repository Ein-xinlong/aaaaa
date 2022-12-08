package com.jd.saas.pdacommon.net;

/**
 * 网络回调
 *
 * @author majiheng
 */
public abstract class NetObserver<T> extends BaseObserver<T> {

    private INetErrorHandler mErrorHandler;

    public NetObserver(INetErrorHandler error) {
        mErrorHandler = error;
    }

    @Override
    protected void onError(NetError error) {
        mErrorHandler.handleNetError(error);
    }
}
