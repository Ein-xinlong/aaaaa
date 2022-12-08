package com.jd.saas.pdacommon.net;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * 请求基础回调
 *
 * @author majiheng
 */
public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {

    /**
     * 请求完毕
     */
    protected void onComplete(boolean error) {

    }

    /**
     * 请求成功
     */
    protected abstract void onSuccess(T t);

    /**
     * 请求失败
     */
    protected abstract void onError(NetError error);

    @Override
    public void onNext(BaseResponse<T> response) {
        if(response.getCode() == NetCodeConstant.OK_200) {
            onSuccess(response.getData());
        }else {
            onError(new NetError(response.getCode(),response.getMsg()));
        }
    }

    @Override
    public void onError(@NonNull Throwable t) {
        onComplete(true);
        onError(new NetError(NetCodeConstant.ERROR_404,t.getMessage()));
    }

    @Override
    public void onComplete() {
        onComplete(false);
    }
}
