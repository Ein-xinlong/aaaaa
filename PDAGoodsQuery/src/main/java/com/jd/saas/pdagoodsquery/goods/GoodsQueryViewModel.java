package com.jd.saas.pdagoodsquery.goods;

import static com.jd.saas.pdagoodsquery.GoodsQueryRouterPath.REQUEST_CODE_SCAN;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchGoodsResultAdapter;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.zxing.router.ZxingRouterPath;
import com.jd.saas.pdagoodsquery.GoodsQueryRouterPath;
import com.jd.saas.pdagoodsquery.goods.adapter.GoodsQueryPromotionAdapter;
import com.jd.saas.pdagoodsquery.goods.net.GoodsQueryRepository;
import com.jd.saas.pdagoodsquery.goods.net.GoodsQueryRequestData;
import com.jd.saas.pdagoodsquery.goods.ui.GoodsQueryBarcodeDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 商品查询ViewModel
 *
 * @author: ext.anxinlong
 * @date: 2021/6/3
 */
public class GoodsQueryViewModel extends NetViewModel {
    private SearchGoodsResultAdapter mSearchGoodsResultAdapter;
    private GoodsQueryPromotionAdapter promotionAdapter;
    public ObservableField<List<SearchResultBean.Promotion>> promotions = new ObservableField<>();
    //商品名称列表 data
    public ObservableField<List<SearchGoodBean>> nameList = new ObservableField<>();
    // 搜索内容
    private String mSearchContent;
    //网络请求
    private final GoodsQueryRepository repository = new GoodsQueryRepository();
    // 一页分页数
    private final int PAGE_SIZE = 10;
    private final SearchResultListFactory mFactory = new SearchResultListFactory();
    public LiveData<PagedList<SearchGoodBean>> mSearchListLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();
    //是否搜索过
    public ObservableField<Boolean> isShowImage = new ObservableField<>(true);
    public ObservableField<Boolean> isShowInfo = new ObservableField<>(false);
    public ObservableField<Boolean> isShowList = new ObservableField<>(true);

    //商品查询 输入框内容
    public MutableLiveData<String> queryGoods = new MutableLiveData<>();

    public ObservableField<SearchResultBean> mInfoData = new ObservableField<>();

    //促销信息相关字段
    public ObservableField<Boolean> isShowLook = new ObservableField<>(true);
    public static final int PROMOTION_STATUS_WAIT = 4;
    public static final int PROMOTION_STATUS_ING = 5;
    public static final int SHOW_PROMOTION_NUM = 2;

    /*
    查看进销存
     */
    public void goStockSale() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", mInfoData.get());
        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), GoodsQueryRouterPath.HOST_PATH_GOODS_QUERY_SALE, bundle);
    }

    /*
    查看更多 -促销信息
     */
    public void onLookMore(View v) {

        List<SearchResultBean.Promotion> promotionList = promotions.get();
        int value = getPromotionAdapter().getItemCount();
        if (value == SHOW_PROMOTION_NUM) {
            getPromotionAdapter().refreshList(promotionList, promotionList.size());
            isShowLook.set(false);

        } else if (value == promotionList.size()) {
            getPromotionAdapter().refreshList(promotionList, SHOW_PROMOTION_NUM);
            isShowLook.set(true);

        }
    }

    /**
     * 跳转到库存流水page
     */
    public void onLookFlow() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", mInfoData.get());
        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), GoodsQueryRouterPath.HOST_PATH_GOODS_QUERY_FLOW, bundle);
    }

    /*
    editText监听
     */
    public void searchContent(Editable editable) {
        isShowLook.set(true);
        if (!TextUtils.isEmpty(editable.toString())) {
            getQueryGoodsName(editable.toString());
            //     mDataBinding.morePromotion.setText(getResources().getString(R.string.goods_query_promotion_more));//复位
        } else {
            isShowImage.set(true);//没有数据图片 隐藏
            isShowInfo.set(false);
            isShowList.set(false);
        }

    }

    /**
     * 扫描条码
     */
    public void screenClick(Context context) {
        RouterClient.getInstance().forward(context, ZxingRouterPath.PATH_ZXING, new Bundle(), REQUEST_CODE_SCAN);
    }

    /**
     * 根据编号，名称查询 商品
     */
    public void getQueryGoodsName(String dimName) {
        this.mSearchContent = dimName;
        // 每次edit text内容变更，就调用搜索
        refreshSearchList();
    }

    /**
     * 根据id获取商品信息
     */
    public void getQueryGoodsInfo(Context context, String skuId) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.show();
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        String pin = userData.getUserPin();
        String tenantId = userData.getTenantId();
        String shopId = userData.getShopId();
        hashMap.put("tenantId", tenantId);
        hashMap.put("pin", pin);
        GoodsQueryRequestData data = new GoodsQueryRequestData();
        data.setStoreId(shopId);
        data.setCondition(skuId);
        data.setPin(pin);
        hashMap.put("data", data);
        repository.getGoodsById(hashMap, new NetObserver<SearchResultBean>(this) {
            @Override
            protected void onError(NetError error) {
                super.onError(error);
            }

            @Override
            protected void onSuccess(SearchResultBean queryGoods) {
                if (null != queryGoods) {
                    isShowImage.set(false);
                    isShowInfo.set(true);
                    isShowList.set(false);
                    mInfoData.set(queryGoods);
                    promotions.set(filterStatus(queryGoods.getPromotions()));
                    getPromotionAdapter().refreshList(promotions.get(), SHOW_PROMOTION_NUM);
                    if (null != queryGoods.getPromotions() && queryGoods.getPromotions().size() != 0) {

                    }
                    //      MyToast.show(queryGoods.getSkuName()+"",false);
                }

            }

            @Override
            protected void onComplete(boolean error) {
                dialog.dismiss();
            }
        });


    }

    /*
    过滤促销信息
     */
    private List<SearchResultBean.Promotion> filterStatus(List<SearchResultBean.Promotion> promotionList) {
        List<SearchResultBean.Promotion> promotions = new ArrayList<>();
        for (SearchResultBean.Promotion promotion : promotionList) {
            if (promotion.getStatus() == PROMOTION_STATUS_WAIT || promotion.getStatus() == PROMOTION_STATUS_ING) {
                promotions.add(promotion);
            }
        }
        return promotions;
    }

    /**
     * 查看更多dialog
     */
    public void more(View view) {
        GoodsQueryBarcodeDialog dialog = new GoodsQueryBarcodeDialog.Builder(
                view.getContext(), mInfoData.get().getUpcCode()
        ).setCloseListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        dialog.show();
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

        private final MutableLiveData<SearchResultListFactory.SearchResultListDataSource> mData = new MutableLiveData<>();

        void refresh() {
            if (mData.getValue() != null) {
                mData.getValue().invalidate();
            }
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
                    return;
                }
                mPage = 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());

                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setCondition(mSearchContent);
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                hashMap.put("data", bean);
                repository.obtainQueryGoods(hashMap, new NetObserver<SearchResultBean>(GoodsQueryViewModel.this) {
                    @Override
                    protected void onSuccess(SearchResultBean bean) {
                        if (null == bean) {
                            return;
                        }
                        if (null == bean.getData()) {
                            isShowImage.set(false);
                            isShowList.set(false);
                            isShowInfo.set(true);
                            mInfoData.set(bean);
                            //   if (null != bean.getPromotions() && bean.getPromotions().size() != 0) {}
                            promotions.set(filterStatus(bean.getPromotions()));
                            getPromotionAdapter().refreshList(promotions.get(), SHOW_PROMOTION_NUM);

                        } else {
                            if (bean.getData().isEmpty()) {
                                isShowImage.set(true);//没有数据图片 隐藏
                                isShowInfo.set(false);
                                isShowList.set(false);
                            } else {
                                callback.onResult(bean.getData(), 0, bean.getData().size());
                                isShowImage.set(false);//没有数据图片 隐藏
                                isShowInfo.set(false);
                                isShowList.set(true);
                                nameList.set(bean.getData());
                            }
                        }

                    }
                });
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<SearchGoodBean> callback) {
                mPage = mPage + 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setCondition(mSearchContent);
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                hashMap.put("data", bean);
                repository.obtainQueryGoods(hashMap, new NetObserver<SearchResultBean>(GoodsQueryViewModel.this) {
                    @Override
                    protected void onSuccess(SearchResultBean bean) {
                        if (null != bean && null != bean.getData()) {
                            callback.onResult(bean.getData());
                            isShowImage.set(false);//没有数据图片 隐藏
                            isShowInfo.set(false);
                            isShowList.set(true);
                            nameList.set(bean.getData());
                        }
                    }
                });

            }
        }
    }

    /**
     * 获取搜索列表adapter
     */
    public SearchGoodsResultAdapter getSearchResultAdapter() {
        if (null == mSearchGoodsResultAdapter) {
            mSearchGoodsResultAdapter = new SearchGoodsResultAdapter();
            mSearchGoodsResultAdapter.setOnItemClickListener(new SearchGoodsResultAdapter.OnItemClick() {
                @Override
                public void onItemClick(SearchGoodBean searchGoodBean) {
                    // do something
                }
            });
        }
        return mSearchGoodsResultAdapter;
    }

    /**
     * 获取搜索列表adapter
     */
    public GoodsQueryPromotionAdapter getPromotionAdapter() {
        if (null == promotionAdapter) {
            promotionAdapter = new GoodsQueryPromotionAdapter();

        }
        return promotionAdapter;
    }


}
