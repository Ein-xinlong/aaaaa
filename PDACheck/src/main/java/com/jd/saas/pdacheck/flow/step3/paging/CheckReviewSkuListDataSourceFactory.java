package com.jd.saas.pdacheck.flow.step3.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuResult;
import com.jd.saas.pdacheck.flow.step3.repo.CheckReviewRepository;

import org.jetbrains.annotations.NotNull;

public class CheckReviewSkuListDataSourceFactory
        extends DataSource.Factory<Integer, CheckReviewSkuResult> {
    private final CheckReviewRepository repository;
    private final CheckOnReviewSkuListLoadErrorCallback checkOnMissedSkuListLoadErrorCallback;
    private CheckReviewSkuListDataSource curDataSource;
    private CheckReviewSkuListParamBuilder paramBuilder;


    public CheckReviewSkuListDataSourceFactory(CheckReviewRepository repository, CheckReviewSkuListParamBuilder paramBuilder, CheckOnReviewSkuListLoadErrorCallback checkOnMissedSkuListLoadErrorCallback) {
        this.repository = repository;
        this.paramBuilder = paramBuilder;
        this.checkOnMissedSkuListLoadErrorCallback = checkOnMissedSkuListLoadErrorCallback;
    }

    public void refresh(CheckReviewSkuListParamBuilder paramBuilder) {
        this.paramBuilder = paramBuilder;
        if (curDataSource != null) {
            curDataSource.invalidate();
        }
    }


    @NonNull
    @NotNull
    @Override
    public DataSource<Integer, CheckReviewSkuResult> create() {
        curDataSource = new CheckReviewSkuListDataSource(repository, paramBuilder, checkOnMissedSkuListLoadErrorCallback);
        return curDataSource;
    }
}