package com.jd.saas.pdadelivery.search;

import android.text.Editable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdadelivery.net.param.Param;

import java.util.ArrayList;

public class DeliverySearchSkuViewModel extends ViewModel {
    private final int PAGE_SIZE = 10;
    private final DeliverySearchSkuRepository repository;
    private final SearchResultListFactory mFactory = new SearchResultListFactory();
    public LiveData<PagedList<SearchGoodBean>> mSearchListLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();
    // 搜索内容
    private String mSearchContent;
    public MutableLiveData<Boolean> mSearchContentVisible = new MutableLiveData<>();
    public MutableLiveData<String> showToastEvent = new MutableLiveData<>();
    public MutableLiveData<SearchGoodBean> onSearchResultIsBean = new MutableLiveData<>();

    public DeliverySearchSkuViewModel(DeliverySearchSkuRepository repository) {
        this.repository = repository;
    }


    /**
     * et每次输入字符都调用该方法
     */
    public void searchContent(String str) {
        mSearchContent = str;
        // 搜索ui显/隐
        mSearchContentVisible.postValue(!TextUtils.isEmpty(mSearchContent));
        // 每次edit text内容变更，就调用搜索
        refreshSearchList();
    }

    /**
     * 重新刷新搜索结果-下拉刷新-商品搜索界面暂时用不到，此为示例
     */
    public void refreshSearchList() {
        mFactory.refresh();
    }

    /**
     * 搜素结果list factory
     */
    private class SearchResultListFactory extends DataSource.Factory<Integer, SearchGoodBean> {

        private final MutableLiveData<SearchResultListDataSource> mData = new MutableLiveData<>();

        void refresh() {
            mData.getValue().invalidate();
        }

        @NonNull
        @Override
        public DataSource<Integer, SearchGoodBean> create() {
            SearchResultListFactory.SearchResultListDataSource addressListDataSource = new SearchResultListFactory.SearchResultListDataSource();
            mData.postValue(addressListDataSource);
            return addressListDataSource;
        }

        /**
         * 搜索结果list data source
         */
        private class SearchResultListDataSource extends PositionalDataSource<SearchGoodBean> {

            private int mPage;

            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<SearchGoodBean> callback) {
                if (TextUtils.isEmpty(mSearchContent)) {
                    callback.onResult(new ArrayList<>(), 0, 0);
                    return;
                }
                mPage = 1;
                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                bean.setCondition(mSearchContent);
                repository.getGoodsList(new Param<>(bean), new BaseObserver<SearchResultBean>() {

                    @Override
                    protected void onSuccess(SearchResultBean bean) {
                        if (null != bean && null != bean.getData()) {
                            callback.onResult(bean.getData(), 0, bean.getTotal());
                        }
                        if (bean != null && !TextUtils.isEmpty(bean.getSkuId())) {
                            SearchGoodBean goodBean = new SearchGoodBean();
                            goodBean.setSkuId(bean.getSkuId());
                            onSearchResultIsBean.postValue(goodBean);
                        }
                    }

                    @Override
                    protected void onError(NetError error) {

                    }
                });
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<SearchGoodBean> callback) {
                mPage = mPage + 1;

                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                bean.setCondition(mSearchContent);

                repository.getGoodsList(new Param<>(bean), new BaseObserver<SearchResultBean>() {

                    @Override
                    protected void onSuccess(SearchResultBean bean) {
                        if (bean == null || bean.getData() == null) return;
                        callback.onResult(bean.getData());
                    }

                    @Override
                    protected void onError(NetError error) {

                    }
                });
            }
        }
    }
}
