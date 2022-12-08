package com.jd.saas.pdadelivery.manage.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.jd.saas.pdadelivery.net.DeliveryRepository;
import com.jd.saas.pdadelivery.net.result.StockItemResult;

import org.jetbrains.annotations.NotNull;

public class StockListDataSourceFactory
        extends DataSource.Factory<Integer, StockItemResult> {
    private final DeliveryRepository repository;
    private final OnLoadErrorCallback onLoadErrorCallback;
    private StockListDataSource curDataSource;
    private AsnListDataParamBuilder paramBuilder;


    public StockListDataSourceFactory(DeliveryRepository repository, AsnListDataParamBuilder paramBuilder, OnLoadErrorCallback onLoadErrorCallback) {
        this.repository = repository;
        this.paramBuilder = paramBuilder;
        this.onLoadErrorCallback = onLoadErrorCallback;
    }

    public void refresh(AsnListDataParamBuilder paramBuilder) {
        this.paramBuilder = paramBuilder;
        if (curDataSource != null) {
            curDataSource.invalidate();
        }
    }


    @NonNull
    @NotNull
    @Override
    public DataSource<Integer, StockItemResult> create() {
        curDataSource = new StockListDataSource(repository, paramBuilder, onLoadErrorCallback);
        return curDataSource;
    }
}