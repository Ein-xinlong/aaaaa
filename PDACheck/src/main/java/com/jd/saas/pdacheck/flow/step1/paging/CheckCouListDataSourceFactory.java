package com.jd.saas.pdacheck.flow.step1.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.jd.saas.pdacheck.flow.step1.bean.CheckCouResult;
import com.jd.saas.pdacheck.flow.step1.repo.CheckCouRepository;

import org.jetbrains.annotations.NotNull;

/**
 * 预盘单列表数据源工厂
 *
 * @author gouhetong
 */
public class CheckCouListDataSourceFactory
        extends DataSource.Factory<Integer, CheckCouResult> {
    private final CheckCouRepository repository;
    private final CheckPagingErrorCallback checkPagingErrorCallback;
    private CheckCouListDataSource curDataSource;
    private CheckCouListParamBuilder paramBuilder;


    public CheckCouListDataSourceFactory(CheckCouRepository repository, CheckCouListParamBuilder paramBuilder, CheckPagingErrorCallback checkPagingErrorCallback) {
        this.repository = repository;
        this.paramBuilder = paramBuilder;
        this.checkPagingErrorCallback = checkPagingErrorCallback;
    }

    public void refresh(CheckCouListParamBuilder paramBuilder) {
        this.paramBuilder = paramBuilder;
        if (curDataSource != null) {
            curDataSource.invalidate();
        }
    }


    @NonNull
    @NotNull
    @Override
    public DataSource<Integer, CheckCouResult> create() {
        curDataSource = new CheckCouListDataSource(repository, paramBuilder, checkPagingErrorCallback);
        return curDataSource;
    }
}