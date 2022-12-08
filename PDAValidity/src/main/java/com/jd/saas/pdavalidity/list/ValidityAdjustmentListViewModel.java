package com.jd.saas.pdavalidity.list;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchGoodsResultAdapter;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.zxing.router.ZxingRouterPath;
import com.jd.saas.pdavalidity.R;
import com.jd.saas.pdavalidity.list.adapter.ValidityAdjustmentListAdapter;
import com.jd.saas.pdavalidity.net.ValidityRepository;

import java.util.HashMap;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/2
 */
public class ValidityAdjustmentListViewModel extends NetViewModel {

    // 网络请求
    private final ValidityRepository mValidityRepository = new ValidityRepository();
    // 一页分页数
    private final int PAGE_SIZE = 10;

    private ValidityAdjustmentListAdapter mValidityAdjustmentListAdapter;
    private SearchGoodsResultAdapter mSearchGoodsResultAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mSearchLinearLayoutManager;

    // 搜索内容
    private String mSearchContent;
    public ObservableField<String> mSearchScreenResult = new ObservableField<>("");
    public ObservableField<Boolean> mSearchContentVisible = new ObservableField<>(true);
    public MutableLiveData<Boolean> mHideKeyBoard = new MutableLiveData<>(false);// 隐藏软键盘

    // 商品详情内容
    public ObservableField<String> mIcon = new ObservableField<>();
    public ObservableField<String> mTitle = new ObservableField<>();//标题
    public ObservableField<String> mBarcodeNumber = new ObservableField<>();//商品条码
    public ObservableField<String> mUnit = new ObservableField<>();//单位
    public ObservableField<String> mTermOfValidity = new ObservableField<>();//有效期
    public ObservableField<String> mStock = new ObservableField<>();//可用库存
    private String mSkuId;// 当前商品的skuId

    // 好坏品，0：好 1：坏 默认0
    private int mStorageType = 0;
    public ObservableField<String> mStorageName = new ObservableField<>("");
    private String[] storageName;// 库位名称数组
    private Integer[] storageId;// 库位类型数组

    // 扫描code
    public final int SCREEN_REQUEST_CODE = 233;

    // 是否刷新的标示
    public MutableLiveData<Boolean> mRefresh = new MutableLiveData<>(false);

    /**
     * 清除商品信息
     */
    private void clearDetail() {
        mIcon.set("");
        mTitle.set("");
        mBarcodeNumber.set("");
        mUnit.set("");
        mTermOfValidity.set("");
        mStock.set("");
    }

    /**
     * 扫描
     */
    public void screen(View view) {
        RouterClient.getInstance().forward(view.getContext(), ZxingRouterPath.PATH_ZXING, new Bundle(), SCREEN_REQUEST_CODE);
    }

    /**
     * 扫描结果-搜索
     */
    public void setScanResult(String screenResult) {
        // 设置搜索内容，刷新列表
        mSearchContent = screenResult;
        mSearchScreenResult.set(screenResult);
    }

    /**
     * 获取搜索列表adapter
     */
    public SearchGoodsResultAdapter getSearchResultAdapter() {
        if(null == mSearchGoodsResultAdapter) {
            mSearchGoodsResultAdapter = new SearchGoodsResultAdapter();
            mSearchGoodsResultAdapter.setOnItemClickListener(new SearchGoodsResultAdapter.OnItemClick() {
                @Override
                public void onItemClick(SearchGoodBean searchGoodBean) {
                    if(null != searchGoodBean) {
                        if(searchGoodBean.isShelfLifeSup()) {
                            // 隐藏搜索ui
                            mSearchContentVisible.set(false);
                            // 隐藏键盘
                            mHideKeyBoard.postValue(true);
                            // 请求效期详情
                            mSkuId = searchGoodBean.getSkuID();
                            refreshGoodDetailList();
                        }else {
                            MyToast.show(R.string.validity_adjustment_not,false);
                        }
                    }
                }
            });
        }
        return mSearchGoodsResultAdapter;
    }

    /**
     * 创建搜索列表布局管理器
     */
    public LinearLayoutManager getSearchLinearLayoutManager(Context context) {
        if (null == mSearchLinearLayoutManager) {
            mSearchLinearLayoutManager = new LinearLayoutManager(context);
            mSearchLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return mSearchLinearLayoutManager;
    }

    /**
     * et每次输入字符都调用该方法
     */
    public void searching(Editable editable) {
        mSearchContent = editable.toString();
        // 搜索就显示搜索ui，隐藏商品详情的ui
        mSearchContentVisible.set(true);
        // 开始搜索
        refreshSearchList();
    }

    // 模糊搜索相关
    private final SearchResultListFactory mFactory = new SearchResultListFactory();
    public LiveData<PagedList<SearchGoodBean>> mSearchListLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();

    /**
     * 重新刷新搜索结果-下拉刷新-商品搜索界面暂时用不到，此为示例
     */
    public void refreshSearchList() {
        // 刷新前清空本地数据
        clearDetail();
        mFactory.refresh();
    }

    /**
     * 搜素结果list factory
     */
    private class SearchResultListFactory extends DataSource.Factory<Integer, SearchGoodBean> {

        private SearchResultListDataSource mDataSource;

        void refresh() {
            mDataSource.invalidate();
        }

        @NonNull
        @Override
        public DataSource<Integer, SearchGoodBean> create() {
            mDataSource = new SearchResultListDataSource();
            return mDataSource;
        }

        /**
         * 搜索结果list data source
         */
        private class SearchResultListDataSource extends PositionalDataSource<SearchGoodBean> {

            private int mPage;

            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<SearchGoodBean> callback) {
                if(!TextUtils.isEmpty(mSearchContent)) {
                    mPage = 1;
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                    hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
                    SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                    bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                    bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                    bean.setPage(mPage + "");
                    bean.setPageSize(PAGE_SIZE + "");
                    bean.setCondition(mSearchContent);
                    hashMap.put("data",bean);
                    mValidityRepository.getGoodsList(hashMap, new NetObserver<SearchResultBean>(ValidityAdjustmentListViewModel.this) {

                        @Override
                        protected void onSuccess(SearchResultBean bean) {
                            if(null != bean) {
                                if(null == bean.getData()) {
                                    if(bean.isShelfLifeSup()) {
                                        // 返回的是商品详情
                                        mSearchContentVisible.set(false);
                                        // 刷新部分ui
                                        mIcon.set(bean.getLogo());
                                        mTitle.set(bean.getSkuName());
                                        mBarcodeNumber.set(bean.getUpcCode());
                                        mUnit.set(bean.getSaleUnitName());
                                        mTermOfValidity.set(bean.getShelfLife() + bean.getShelfLifeUnitDesc());
                                        mSkuId = bean.getSkuId();
                                        // 同时搜索效期详情
                                        refreshGoodDetailList();
                                    }else {
                                        MyToast.show(R.string.validity_adjustment_not,false);
                                    }
                                }else {
                                    // 返回的是模糊搜索的列表
                                    mSearchContentVisible.set(true);
                                    callback.onResult(bean.getData(), 0, bean.getTotal());
                                }
                            }
                        }

                        @Override
                        protected void onError(NetError error) {
                            mSearchContentVisible.set(true);
                        }
                    });
                }
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<SearchGoodBean> callback) {
                if(!TextUtils.isEmpty(mSearchContent)) {
                    mPage = mPage + 1;
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                    hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
                    SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                    bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                    bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                    bean.setPage(mPage + "");
                    bean.setPageSize(PAGE_SIZE + "");
                    bean.setCondition(mSearchContent);
                    hashMap.put("data",bean);
                    mValidityRepository.getGoodsList(hashMap, new NetObserver<SearchResultBean>(ValidityAdjustmentListViewModel.this) {

                        @Override
                        protected void onSuccess(SearchResultBean bean) {
                            if (null != bean && null != bean.getData() && bean.getData().size() != 0) {
                                callback.onResult(bean.getData());
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 库位点击
     */
    public void storageClick(View view) {
        Context context = view.getContext();
        AlertDialog.Builder storageDialog = new AlertDialog.Builder(context);
        storageDialog.setItems(storageName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                // 刷新「库位」列表
                mStorageType = storageId[position];
                mStorageName.set(storageName[position]);
                refreshGoodDetailList();
            }
        });
        storageDialog.show();
    }

    /**
     * 创建详情列表布局管理器
     */
    public LinearLayoutManager getLinearLayoutManager(Context context) {
        if (null == mLinearLayoutManager) {
            mLinearLayoutManager = new LinearLayoutManager(context);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return mLinearLayoutManager;
    }

    /**
     * 创建详情列表适配器
     */
    public ValidityAdjustmentListAdapter getInventoryAdjustmentAdapter() {
        if (null == mValidityAdjustmentListAdapter) {
            mValidityAdjustmentListAdapter = new ValidityAdjustmentListAdapter();
        }
        return mValidityAdjustmentListAdapter;
    }

    // 模糊搜索相关
    private final GoodDetailFactory mGoodDetailFactory = new GoodDetailFactory();
    public LiveData<PagedList<SearchGoodBean>> mGoodDetailLiveData = new LivePagedListBuilder<>(mGoodDetailFactory, PAGE_SIZE).build();

    /**
     * 重新刷新搜索结果-下拉刷新-商品搜索界面暂时用不到，此为示例
     */
    public void refreshGoodDetailList() {
        // 刷新前清空本地数据
        clearDetail();
        mGoodDetailFactory.refresh();
    }

    /**
     * 商品详情中的列表factory
     */
    private class GoodDetailFactory extends DataSource.Factory<Integer, SearchGoodBean> {

        private GoodDetailDataSource mDataSource;

        void refresh() {
            mDataSource.invalidate();
        }

        @NonNull
        @Override
        public DataSource<Integer, SearchGoodBean> create() {
            mDataSource = new GoodDetailDataSource();
            return mDataSource;
        }

        private class GoodDetailDataSource extends PositionalDataSource<SearchGoodBean> {

            private int mPage;

            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<SearchGoodBean> callback) {
                if(!TextUtils.isEmpty(mSkuId)) {
                    mPage = 1;
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                    hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
                    SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                    bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                    bean.setPage(mPage + "");
                    bean.setPageSize(PAGE_SIZE + "");
                    bean.setSkuId(mSkuId);
                    bean.setSkuNature(mStorageType + "");
                    hashMap.put("data",bean);
                    // 下拉刷新开始
                    mRefresh.postValue(true);
                    mValidityRepository.getDetail(hashMap, new NetObserver<SearchResultBean>(ValidityAdjustmentListViewModel.this) {

                        @Override
                        protected void onComplete(boolean error) {
                            // 下拉刷新结束
                            mRefresh.postValue(false);
                        }

                        @Override
                        protected void onSuccess(SearchResultBean bean) {
                            if (null != bean) {
                                // 设置库位信息
                                if(null != bean.getDistinctLocNameLinkedList() && bean.getDistinctLocNameLinkedList().size() != 0) {
                                    // 有库位信息
                                    storageName = new String[bean.getDistinctLocNameLinkedList().size()];
                                    storageId = new Integer[bean.getDistinctLocNameLinkedList().size()];
                                    if(TextUtils.isEmpty(mStorageName.get())) {
                                        mStorageName.set(bean.getDistinctLocNameLinkedList().get(0).getName());
                                    }
                                    for(int i=0;i<bean.getDistinctLocNameLinkedList().size();i++) {
                                        storageName[i] = bean.getDistinctLocNameLinkedList().get(i).getName();
                                        storageId[i] = bean.getDistinctLocNameLinkedList().get(i).getSkuNature();
                                    }
                                    // 刷新详情ui
                                    mSearchContentVisible.set(false);
                                    mIcon.set(bean.getLogo());
                                    mTitle.set(bean.getSkuName());
                                    mBarcodeNumber.set(bean.getUpcCode());
                                    mUnit.set(bean.getSaleUnitName());
                                    mTermOfValidity.set(bean.getShelfLife() + bean.getShelfLifeUnitDesc());
                                    mStock.set(bean.getAvailableStockCount() + "");
                                    // 设置库存列表
                                    if(null != bean.getData()) {
                                        callback.onResult(bean.getData(), 0, bean.getTotal());
                                    }
                                }else {
                                    // 无库位信息
                                    mSearchContentVisible.set(true);
                                    MyToast.show(R.string.validity_adjustment_storage_no_err,false);
                                }
                            }
                        }

                        @Override
                        protected void onError(NetError error) {
                            mSearchContentVisible.set(true);
                            MyToast.show(error.getMsg(),false);
                        }
                    });
                }
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<SearchGoodBean> callback) {
                if(!TextUtils.isEmpty(mSkuId)) {
                    mPage = mPage + 1;
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                    hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
                    SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                    bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                    bean.setPage(mPage + "");
                    bean.setPageSize(PAGE_SIZE + "");
                    bean.setSkuId(mSkuId);
                    bean.setSkuNature(mStorageType + "");
                    hashMap.put("data",bean);
                    mValidityRepository.getDetail(hashMap, new NetObserver<SearchResultBean>(ValidityAdjustmentListViewModel.this) {

                        @Override
                        protected void onSuccess(SearchResultBean bean) {
                            if (null != bean && null != bean.getData()) {
                                callback.onResult(bean.getData());
                            }
                        }
                    });
                }
            }
        }
    }
}
