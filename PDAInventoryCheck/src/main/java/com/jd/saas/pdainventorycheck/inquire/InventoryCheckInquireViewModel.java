package com.jd.saas.pdainventorycheck.inquire;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
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
import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.inquire.bean.InventoryCheckQueryBean;
import com.jd.saas.pdainventorycheck.net.InventoryCheckInquireClientInfoBean;
import com.jd.saas.pdainventorycheck.net.InventoryCheckRepository;
import com.jd.saas.pdainventorycheck.router.InventoryCheckRouterPath;
import java.util.HashMap;

/**
 * 库存查询viewmodel
 *
 * @author: ext.anxinlong
 * @date: 2021/7/13
 */
public class InventoryCheckInquireViewModel extends NetViewModel {
    private String mSearchContent;// 搜索内容
    private final int PAGE_SIZE = 10; // 一页分页数
    private SearchGoodsResultAdapter mSearchGoodsResultAdapter;//模糊查询适配器
    private final InventoryCheckRepository mAdjustmentRepository = new InventoryCheckRepository();//请求网络初始化
    private final SearchResultListFactory mFactory = new SearchResultListFactory();
    public ObservableField<Boolean> isShowImage = new ObservableField<>(false);//列表图片是否显示隐藏
    public ObservableField<Boolean> mProgressBarHide = new ObservableField<>(false);//是否显示loading框
    public ObservableField<Boolean> showGoodsOrStock = new ObservableField<>(true);//列表图片是否显示隐藏
    public LiveData<PagedList<SearchGoodBean>> mSearchListLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();

    /**
     * et每次输入字符都调用该方法
     */
    public void searchContent(Editable editable) {
        mSearchContent = editable.toString();
        //由enter键控制    refreshSearchList();
    }

    /**
     * 创建搜索列表布局管理器
     */
    public LinearLayoutManager getSearchLinearLayoutManager(Context context) {
        LinearLayoutManager mSearchLinearLayoutManager = new LinearLayoutManager(context);
        mSearchLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return mSearchLinearLayoutManager;
    }

    /**
     * 获取搜索列表adapter
     */
    public SearchGoodsResultAdapter getSearchResultAdapter() {
        if (null == mSearchGoodsResultAdapter) {
            mSearchGoodsResultAdapter = new SearchGoodsResultAdapter();
            mSearchGoodsResultAdapter.setOnItemClickListener(searchGoodBean -> {
                mProgressBarHide.set(true);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                InventoryCheckInquireClientInfoBean inventoryPdaTwoBean = new InventoryCheckInquireClientInfoBean();
                inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("clientInfo", inventoryPdaTwoBean);
                InventoryCheckQueryBean bean = new InventoryCheckQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setCondition(searchGoodBean.getSkuID());
                hashMap.put("data", bean);
                mAdjustmentRepository.Goods(hashMap, new NetObserver<SearchResultBean>(this) {

                    @Override
                    public void onComplete() {
                        mProgressBarHide.set(false);
                    }

                    @Override
                    protected void onSuccess(SearchResultBean o) {
                        if (o != null) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("bean", o);
                            RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), InventoryCheckRouterPath.HOST_PATH_DETAILS, bundle);
                        } else {
                            MyToast.show(R.string.inventory_check_no_find_shop, false);
                        }

                    }
                });
            });
        }
        return mSearchGoodsResultAdapter;
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
            public void loadInitial(@NonNull PositionalDataSource.LoadInitialParams params, @NonNull LoadInitialCallback<SearchGoodBean> callback) {
                if (TextUtils.isEmpty(mSearchContent)) {
                    return;
                }
                mPage = 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                bean.setCondition(mSearchContent);
                hashMap.put("data", bean);
                InventoryCheckInquireClientInfoBean inventoryCheckInquireClientInfoBean = new InventoryCheckInquireClientInfoBean();
                inventoryCheckInquireClientInfoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("clientInfo", inventoryCheckInquireClientInfoBean);
                mProgressBarHide.set(true);
                mAdjustmentRepository.getGoodsList(hashMap, new NetObserver<SearchResultBean>(InventoryCheckInquireViewModel.this) {

                    @Override
                    protected void onComplete(boolean error) {
                        mProgressBarHide.set(false);
                    }

                    @Override
                    protected void onSuccess(SearchResultBean bean) {
                        if (null == bean) {
                            return;
                        }
                        //为null时，是返回的 商品详情
                        if (bean.getData() == null) {

                            if (bean.getTotalQty() == null) {
                                String noStock = MyApplication.getInstance().getResources().getString(R.string.inventory_check_no_stock);
                                isShowImage.set(true);
                                showGoodsOrStock.set(true);
                                MyToast.show(noStock, false);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("bean", bean);
                                RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(),
                                        InventoryCheckRouterPath.HOST_PATH_DETAILS, bundle);

                            }

                        } else if (bean.getData().size() == 0) {
                            String noStock1 = MyApplication.getInstance().getResources().getString(R.string.inventory_check_no_info);
                            MyToast.show(noStock1, false);
                            showGoodsOrStock.set(false);
                            isShowImage.set(true);
                        } else {
                            isShowImage.set(false);
                            callback.onResult(bean.getData(), 0, bean.getTotal());
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
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                bean.setCondition(mSearchContent);
                InventoryCheckInquireClientInfoBean inventoryCheckInquireClientInfoBean = new InventoryCheckInquireClientInfoBean();
                inventoryCheckInquireClientInfoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("data", bean);
                hashMap.put("clientInfo", inventoryCheckInquireClientInfoBean);
                mAdjustmentRepository.getGoodsList(hashMap, new NetObserver<SearchResultBean>(InventoryCheckInquireViewModel.this) {

                    @Override
                    protected void onSuccess(SearchResultBean bean) {
                        if (bean == null || bean.getData() == null) return;
                        callback.onResult(bean.getData());
                    }

                    @Override
                    protected void onError(NetError error) {
//                        super.onError(error);
                    }
                });
            }
        }
    }
}
