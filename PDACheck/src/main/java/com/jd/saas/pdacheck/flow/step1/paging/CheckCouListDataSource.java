package com.jd.saas.pdacheck.flow.step1.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.jd.saas.pdacheck.flow.step1.bean.CheckCouResult;
import com.jd.saas.pdacheck.flow.step1.repo.CheckCouRepository;
import com.jd.saas.pdacheck.net.CheckPagedResp;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouListParam;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;

import org.jetbrains.annotations.NotNull;

/**
 * 预盘单列表数据源
 *
 * @author gouhetong
 */
public class CheckCouListDataSource extends PageKeyedDataSource<Integer, CheckCouResult> {
    private final CheckCouRepository repository;
    private final CheckCouListParamBuilder paramBuilder;
    private final CheckPagingErrorCallback checkPagingErrorCallback;

    public CheckCouListDataSource(CheckCouRepository repository, CheckCouListParamBuilder paramBuilder, CheckPagingErrorCallback checkPagingErrorCallback) {
        this.repository = repository;
        this.paramBuilder = paramBuilder;
        this.checkPagingErrorCallback = checkPagingErrorCallback;
    }

    @Override
    public void loadInitial(@NonNull @NotNull LoadInitialParams<Integer> params, @NonNull @NotNull LoadInitialCallback<Integer, CheckCouResult> callback) {
        if (!paramBuilder.isInit()) {
            return;
        }
        final CheckCouListParam asnListDataParam = paramBuilder.build(1, params.requestedLoadSize);
        repository.getQueryCouHeaderByPage(asnListDataParam, new BaseObserver<CheckPagedResp<CheckCouResult>>() {
            @Override
            protected void onSuccess(CheckPagedResp<CheckCouResult> result) {
                callback.onResult(result.getItemList(), null, result.getTotalPage() <= 1 ? null : 2);
            }

            @Override
            protected void onError(NetError error) {
                if (checkPagingErrorCallback != null) {
                    checkPagingErrorCallback.onError(error);
                }
            }
        });
    }

    @Override
    public void loadBefore(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, CheckCouResult> callback) {
    }

    @Override
    public void loadAfter(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, CheckCouResult> callback) {
        CheckCouListParam asnListDataParam = paramBuilder.build(params.key, params.requestedLoadSize);
        repository.getQueryCouHeaderByPage(asnListDataParam, new BaseObserver<CheckPagedResp<CheckCouResult>>() {
            @Override
            protected void onSuccess(CheckPagedResp<CheckCouResult> result) {
                callback.onResult(result.getItemList(), result.getTotalPage() <= params.key ? null : params.key + 1);
            }

            @Override
            protected void onError(NetError error) {
                if (checkPagingErrorCallback != null) {
                    checkPagingErrorCallback.onError(error);
                }
            }
        });
    }
}
