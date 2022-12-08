package com.jd.saas.pdacheck.flow.step3.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuResult;
import com.jd.saas.pdacheck.flow.step3.repo.CheckReviewRepository;
import com.jd.saas.pdacheck.net.CheckPagedResp;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;

import org.jetbrains.annotations.NotNull;

public class CheckReviewSkuListDataSource extends PageKeyedDataSource<Integer, CheckReviewSkuResult> {
    private final CheckReviewRepository repository;
    private final CheckReviewSkuListParamBuilder paramBuilder;
    private final CheckOnReviewSkuListLoadErrorCallback onError;

    public CheckReviewSkuListDataSource(CheckReviewRepository repository, CheckReviewSkuListParamBuilder paramBuilder, CheckOnReviewSkuListLoadErrorCallback onError) {
        this.repository = repository;
        this.paramBuilder = paramBuilder;
        this.onError = onError;
    }

    @Override
    public void loadInitial(@NonNull @NotNull LoadInitialParams<Integer> params, @NonNull @NotNull LoadInitialCallback<Integer, CheckReviewSkuResult> callback) {
        int pageSize = params.requestedLoadSize;
        final Integer limit = paramBuilder.getLimit();
        if (limit != null) {
            pageSize = limit;
        }
        final CheckReviewSkuListParam reqParam = paramBuilder.build(1, pageSize);
        if (reqParam.getTaskNo() == null) {
            return;
        }
        repository.getSkuList(reqParam, new BaseObserver<CheckPagedResp<CheckReviewSkuResult>>() {
            @Override
            protected void onSuccess(CheckPagedResp<CheckReviewSkuResult> result) {
                if (limit != null) {
                    callback.onResult(result.getItemList(), null, null);
                } else {
                    callback.onResult(result.getItemList(), null, result.getTotalPage() <= 1 ? null : 2);
                }

            }

            @Override
            protected void onError(NetError error) {
                if (onError != null) {
                    onError.onError(error);
                }
            }
        });
    }

    @Override
    public void loadBefore(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, CheckReviewSkuResult> callback) {
    }

    @Override
    public void loadAfter(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, CheckReviewSkuResult> callback) {
        CheckReviewSkuListParam asnListDataParam = paramBuilder.build(params.key, params.requestedLoadSize);
        repository.getSkuList(asnListDataParam, new BaseObserver<CheckPagedResp<CheckReviewSkuResult>>() {
            @Override
            protected void onSuccess(CheckPagedResp<CheckReviewSkuResult> result) {
                callback.onResult(result.getItemList(), result.getTotalPage() <= params.key ? null : params.key + 1);
            }

            @Override
            protected void onError(NetError error) {
                if (onError != null) {
                    onError.onError(error);
                }
            }
        });
    }
}
