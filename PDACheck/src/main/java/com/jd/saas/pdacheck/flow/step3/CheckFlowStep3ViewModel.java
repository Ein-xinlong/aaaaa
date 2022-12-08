package com.jd.saas.pdacheck.flow.step3;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntBean;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuBean;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewQtyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortKeyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortOption;
import com.jd.saas.pdacheck.flow.step3.paging.CheckReviewSkuListDataSourceFactory;
import com.jd.saas.pdacheck.flow.step3.paging.CheckReviewSkuListParamBuilder;
import com.jd.saas.pdacheck.flow.step3.repo.CheckReviewRepository;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;

import org.jetbrains.annotations.NotNull;

/**
 * 盘点第三步：复盘修改VM
 *
 * @author majiheng
 */
public class CheckFlowStep3ViewModel extends NetViewModel {
    private CheckListBean mBean;
    // 「复盘修改」按钮是否显示，列表条目是否可点击
    public ObservableField<Boolean> mContentClickable = new ObservableField<>(true);
    private final CheckReviewRepository repository;
    public final CheckReviewSkuListDataSourceFactory factory;
    private static final int PAGE_SIZE = 20;
    /** 数量差异的类型 */
    private final MutableLiveData<CheckReviewQtyType> filterType = new MutableLiveData<>();
    public LiveData<CheckReviewQtyType> changedFilterType = Transformations.distinctUntilChanged(filterType);
    public LiveData<Boolean> isFilterLoss = Transformations.map(changedFilterType, input -> input == CheckReviewQtyType.LOSS);
    public LiveData<Boolean> isFilterOver = Transformations.map(changedFilterType, input -> input == CheckReviewQtyType.OVER);
    public LiveData<Boolean> isFilterEqual = Transformations.map(changedFilterType, input -> input == CheckReviewQtyType.EQUAL);
    /** 排序的类型 */
    @NonNull
    private Pair<CheckReviewSortKeyType, CheckReviewSortOption> sortType = new Pair<>(null, null);
    private final MutableLiveData<CheckReviewQtyTypedCntBean> qtyTypeCnt = new MutableLiveData<>();
    public LiveData<Integer> lossSkuCnt = Transformations.map(qtyTypeCnt, input -> input != null ? input.getLossCnt() : null);
    public LiveData<Integer> overSkuCnt = Transformations.map(qtyTypeCnt, input -> input != null ? input.getOverCnt() : null);
    public LiveData<Integer> equalSkuCnt = Transformations.map(qtyTypeCnt, input -> input != null ? input.getEqualCnt() : null);
    public final MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    public LiveData<PagedList<CheckReviewSkuBean>> skuList;
    public final MutableLiveData<Boolean> isRefresh = new MutableLiveData<>(true);
    public final MutableLiveData<String> showToastEvent = new MutableLiveData<>();
    public final MutableLiveData<Boolean> showSortTypeDialogEvent = new MutableLiveData<>();
    public final MutableLiveData<Boolean> goNextEvent = new MutableLiveData<>();

    /**
     * 待编辑的商品 供收货弹窗使用
     */
    public MutableLiveData<CheckReviewSkuBean> editSku = new MutableLiveData<>();


    public void changeFilterType(CheckReviewQtyType filterEnum) {
        filterType.setValue(filterEnum);
    }

    public CheckFlowStep3ViewModel(CheckReviewRepository repository) {
        this.repository = repository;
        this.factory = new CheckReviewSkuListDataSourceFactory(this.repository, makeParamBuilder(), error -> {
            if (error != null) {
                showToastEvent.postValue(error.getMsg());
            }
            isRefresh.postValue(false);
        });
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE).setInitialLoadSizeHint(PAGE_SIZE).build();
        skuList = new LivePagedListBuilder<>(factory.map(CheckReviewConvertor::convert), config)
                .setBoundaryCallback(new PagedList.BoundaryCallback<CheckReviewSkuBean>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        isEmpty.postValue(true);
                        isRefresh.postValue(false);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull @NotNull CheckReviewSkuBean itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        isEmpty.postValue(false);
                        isRefresh.postValue(false);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull @NotNull CheckReviewSkuBean itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                    }
                })
                .build();
    }

    public CheckReviewSkuListParamBuilder makeParamBuilder() {
        CheckReviewQtyType value = filterType.getValue();
        if (value == null) {
            value = CheckReviewQtyType.LOSS;
        }
        CheckReviewSkuListParamBuilder builder = new CheckReviewSkuListParamBuilder(mBean != null ? mBean.getTaskNo() : "", value);
        builder.setSortKeyType(sortType.first);
        builder.setSortOption(sortType.second);
        return builder;
    }

    public void init(CheckListBean bean) {
        this.mBean = bean;
        // 判断当前节点是否显示「提交初盘」按钮，是否列表条目可点击
        mContentClickable.set(mBean.getCurrentNode() < CheckTaskNodeEnum.PROFIT.getValue());
        editSku.postValue(null);
        filterType.setValue(CheckReviewQtyType.LOSS);
        refresh();
    }

    public void refresh() {
        isRefresh.setValue(true);
        isEmpty.setValue(false);
        repository.getQtyTypedCnt(new CheckReviewQtyTypedCntParam(mBean.getTaskNo()), new BaseObserver<CheckReviewQtyTypedCntResult>() {
            @Override
            protected void onSuccess(CheckReviewQtyTypedCntResult checkReviewQtyTypedCntResult) {
                qtyTypeCnt.postValue(CheckReviewConvertor.convert(checkReviewQtyTypedCntResult));
            }

            @Override
            protected void onError(NetError error) {

            }
        });
        factory.refresh(makeParamBuilder());
    }

    public String getTaskNo() {
        return mBean.getTaskNo();
    }

    @NonNull
    public Pair<CheckReviewSortKeyType, CheckReviewSortOption> getSortType() {
        return sortType;
    }

    public void setSortType(@NonNull Pair<CheckReviewSortKeyType, CheckReviewSortOption> sortType) {
        this.sortType = sortType;
    }

    /**
     * 打开筛选dialog
     */
    public void showSortTypeDialog() {
        showSortTypeDialogEvent.setValue(true);
    }

    public void goNext() {
        goNextEvent.postValue(true);
    }
}
