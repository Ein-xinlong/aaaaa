package com.jd.saas.pdadelivery.manage;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdadelivery.base.DeliveryBaseViewModel;
import com.jd.saas.pdadelivery.manage.bean.DeliveryBean;
import com.jd.saas.pdadelivery.manage.paging.AsnListDataParamBuilder;
import com.jd.saas.pdadelivery.manage.paging.StockListDataSourceFactory;
import com.jd.saas.pdadelivery.net.DeliveryConvertor;
import com.jd.saas.pdadelivery.net.DeliveryRepository;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.net.enums.AsnTypeEnum;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * 收货管理
 *
 * @author ext.mengmeng
 */
public class DeliveryManageViewModel extends DeliveryBaseViewModel {
    private static final int PAGE_SIZE = 20;
    public final LiveData<PagedList<DeliveryBean>> deliveryList;
    public final StockListDataSourceFactory factory;
    public final MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    public final MutableLiveData<String> searchStr = new MutableLiveData<>();
    public final MutableLiveData<AsnStatusEnum> asnStatusEnum = new MutableLiveData<>(AsnStatusEnum.INITIAL);
    public final MutableLiveData<Pair<Date, Date>> timeRange = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRefresh = new MutableLiveData<>(true);
    private AsnTypeEnum selectType = null;

    public DeliveryManageViewModel(DeliveryRepository repository) {
        this.factory = new StockListDataSourceFactory(repository, makeParamBuilder(), error -> {
            if (error != null) {
                showToastEvent.postValue(error.getMsg());
            }
            isRefresh.postValue(false);
        });
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(PAGE_SIZE).setInitialLoadSizeHint(PAGE_SIZE).build();
        deliveryList = new LivePagedListBuilder<>(factory.map(DeliveryConvertor::convert), config)
                .setBoundaryCallback(new PagedList.BoundaryCallback<DeliveryBean>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        isEmpty.postValue(true);
                        isRefresh.postValue(false);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull @NotNull DeliveryBean itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        isEmpty.postValue(false);
                        isRefresh.postValue(false);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull @NotNull DeliveryBean itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                    }
                })
                .build();
    }


    public void refresh() {
        isRefresh.setValue(true);
        isEmpty.setValue(false);
        factory.refresh(makeParamBuilder());
    }

    public void reset() {
        asnStatusEnum.setValue(AsnStatusEnum.INITIAL);
        searchStr.setValue(null);
        isEmpty.setValue(false);
        timeRange.setValue(null);
        selectType = null;
    }

    public void setSelectTypes(AsnTypeEnum selectType) {
        this.selectType = selectType;
    }

    public AsnListDataParamBuilder makeParamBuilder() {
        AsnStatusEnum statusEnumValue = asnStatusEnum.getValue();
        if (statusEnumValue == null) {
            statusEnumValue = AsnStatusEnum.INITIAL;
        }
        AsnListDataParamBuilder builder = new AsnListDataParamBuilder(statusEnumValue);
        builder.setTimeRange(timeRange.getValue());
        builder.setSelectTypes(selectType);
        builder.setDocNo(searchStr.getValue());
        return builder;
    }
}