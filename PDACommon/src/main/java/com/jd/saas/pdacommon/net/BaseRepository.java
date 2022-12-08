package com.jd.saas.pdacommon.net;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求入口
 *
 * @author majiheng
 */
public abstract class BaseRepository {

    protected <T> void request(Observable<BaseResponse<T>> observable, BaseObserver<T> baseObserver) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }
}
