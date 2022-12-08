package com.jd.saas.pdadelivery.manage.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdadelivery.net.DeliveryRepository;
import com.jd.saas.pdadelivery.net.param.AsnListDataParam;
import com.jd.saas.pdadelivery.net.param.PageQueryParam;
import com.jd.saas.pdadelivery.net.param.Param;
import com.jd.saas.pdadelivery.net.result.PagedResult;
import com.jd.saas.pdadelivery.net.result.StockItemResult;

import org.jetbrains.annotations.NotNull;

public class StockListDataSource extends PageKeyedDataSource<Integer, StockItemResult> {
    private final DeliveryRepository repository;
    private final AsnListDataParamBuilder paramBuilder;
    private final OnLoadErrorCallback onLoadErrorCallback;

    public StockListDataSource(DeliveryRepository repository, AsnListDataParamBuilder paramBuilder, OnLoadErrorCallback onLoadErrorCallback) {
        this.repository = repository;
        this.paramBuilder = paramBuilder;
        this.onLoadErrorCallback = onLoadErrorCallback;
    }

    @Override
    public void loadInitial(@NonNull @NotNull LoadInitialParams<Integer> params, @NonNull @NotNull LoadInitialCallback<Integer, StockItemResult> callback) {
        AsnListDataParam asnListDataParam = paramBuilder.build();
        asnListDataParam.setPageQuery(new PageQueryParam(1, params.requestedLoadSize));
        repository.asnList(new Param<>(asnListDataParam), new BaseObserver<PagedResult<StockItemResult>>() {
            @Override
            protected void onSuccess(PagedResult<StockItemResult> result) {
                callback.onResult(result.getItemList(), null, result.getTotalPage() <= 1 ? null : 2);
            }

            @Override
            protected void onError(NetError error) {
                if (onLoadErrorCallback != null) {
                    onLoadErrorCallback.onError(error);
                }
            }
        });
    }

    @Override
    public void loadBefore(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, StockItemResult> callback) {
    }

    @Override
    public void loadAfter(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, StockItemResult> callback) {
        AsnListDataParam asnListDataParam = paramBuilder.build();
        asnListDataParam.setPageQuery(new PageQueryParam(params.key, params.requestedLoadSize));
        repository.asnList(new Param<>(asnListDataParam), new BaseObserver<PagedResult<StockItemResult>>() {
            @Override
            protected void onSuccess(PagedResult<StockItemResult> result) {
                callback.onResult(result.getItemList(), result.getTotalPage() <= params.key ? null : params.key + 1);
            }

            @Override
            protected void onError(NetError error) {
                if (onLoadErrorCallback != null) {
                    onLoadErrorCallback.onError(error);
                }
            }
        });
    }
}
