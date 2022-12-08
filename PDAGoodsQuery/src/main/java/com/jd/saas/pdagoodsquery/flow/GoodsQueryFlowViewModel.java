package com.jd.saas.pdagoodsquery.flow;

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

import com.jd.saas.pdacommon.calendar.CalendarBottomSheetDialog;
import com.jd.saas.pdacommon.dialog.linkage.StockTransTypeSelectDialog;
import com.jd.saas.pdacommon.dialog.linkage.model.ChildTansType;
import com.jd.saas.pdacommon.dialog.linkage.model.ParentTansType;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.flow.model.GoodsQueryFlowBean;
import com.jd.saas.pdagoodsquery.flow.model.GoodsQueryFlowRequest;
import com.jd.saas.pdagoodsquery.flow.model.GoodsQueryFlowResponse;
import com.jd.saas.pdagoodsquery.flow.net.QueryFlowRepository;

import java.util.HashMap;


/**
 * 库存流水vm
 *
 * @author majiheng
 */
public class GoodsQueryFlowViewModel extends NetViewModel {
    public SearchResultBean mDetailBean;//商品详情bean
    private final QueryFlowRepository repository = new QueryFlowRepository();
    private String mEndTime = "";
    private String mStartTime = "";
    public ParentTansType mParentType = null;
    public ChildTansType mChildType = null;
    // 一页分页数
    private final int PAGE_SIZE = 10;
    private final QueryFlowListFactory mFactory = new QueryFlowListFactory();
    public LiveData<PagedList<GoodsQueryFlowBean>> mFlowLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();
    //时间选择器，时间显示
    public ObservableField<String> mSelectTime = new ObservableField<>();
    //选择的类型
    public ObservableField<String> mSelectedType = new ObservableField<>();
    public MutableLiveData<Boolean> mRefresh = new MutableLiveData<>();


    /**
     * 日期选择
     */
    public void showDataSelectDialog(View view) {
        CalendarBottomSheetDialog bottomSheetDialog = new CalendarBottomSheetDialog(view.getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setOnDateSelected((startDate, endDate) -> {
            if (TextUtils.isEmpty(startDate)) {
                mSelectTime.set(null);
                mStartTime = "";
                mEndTime = "";
            } else {
                mRefresh.postValue(true);
                mSelectTime.set(startDate + ":" + endDate);
                mStartTime = startDate;
                mEndTime = endDate;
            }
            refreshSearchList();

        });
        bottomSheetDialog.show();
    }

    /**
     * 状态选择
     */
    public void showTypeSelectDialog(View view) {
        StockTransTypeSelectDialog dialog = new StockTransTypeSelectDialog(view.getContext());
        dialog.setOnSelectListener((parentTansType, childTansType) -> {
            mParentType = parentTansType;
            mChildType = childTansType;
            //选中 默认库位石，传入父类型名称
            if (childTansType.getName().equals(view.getContext().getString(R.string.stock_trans_type_default))) {
                mSelectedType.set(parentTansType.getName());
            } else {
                mSelectedType.set(childTansType.getName());
            }
            mRefresh.postValue(true);
            refreshSearchList();
        });
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
    private class QueryFlowListFactory extends DataSource.Factory<Integer, GoodsQueryFlowBean> {


        private final MutableLiveData<QueryFlowListDataSource> mData = new MutableLiveData<>();

        void refresh() {
            mData.getValue().invalidate();
        }

        @NonNull
        @Override
        public DataSource<Integer, GoodsQueryFlowBean> create() {
            QueryFlowListFactory.QueryFlowListDataSource flowListDataSource = new QueryFlowListFactory.QueryFlowListDataSource();
            mData.postValue(flowListDataSource);
            return flowListDataSource;
        }

        /**
         * 搜索结果list data source
         */
        private class QueryFlowListDataSource extends PositionalDataSource<GoodsQueryFlowBean> {

            private int mPage;

            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<GoodsQueryFlowBean> callback) {
                mPage = 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                GoodsQueryFlowRequest data = new GoodsQueryFlowRequest();
                data.setSkuId(mDetailBean.getSkuId());
                data.setPageNo(mPage + "");
                data.setPageSize(PAGE_SIZE + "");
                data.setStartTime(mStartTime);
                data.setEndTime(mEndTime);
                if (mParentType != null) {
                    data.setTransType(mParentType.getValue());
                }
                if (mChildType != null) {
                    data.setTransChildType(mChildType.getValue());
                }
                data.setWarehouseId(UserManager.getInstance().getUserData().getShopId());
                data.setTenantId(UserManager.getInstance().getUserData().getTenantId());
                hashMap.put("data", data);
                repository.getQueryFlow(hashMap, new NetObserver<GoodsQueryFlowResponse>(GoodsQueryFlowViewModel.this) {
                    @Override
                    protected void onError(NetError error) {
                        super.onError(error);

                    }

                    @Override
                    protected void onComplete(boolean error) {
                        super.onComplete(error);
                        mRefresh.postValue(false);
                    }

                    @Override
                    protected void onSuccess(GoodsQueryFlowResponse bean) {
                        if (null != bean && null != bean.getItemList()) {
                            callback.onResult(bean.getItemList(), 0, bean.getTotal());
                        }
                    }
                });

            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<GoodsQueryFlowBean> callback) {
                mPage = mPage + 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                GoodsQueryFlowRequest data = new GoodsQueryFlowRequest();
                data.setSkuId(mDetailBean.getSkuId());
                data.setPageNo(mPage + "");
                data.setPageSize(PAGE_SIZE + "");
                data.setStartTime(mStartTime);
                data.setEndTime(mEndTime);
                if (mParentType != null) {
                    data.setTransType(mParentType.getValue());
                }
                if (mChildType != null) {
                    data.setTransChildType(mChildType.getValue());
                }
                data.setWarehouseId(UserManager.getInstance().getUserData().getShopId());
                data.setTenantId(UserManager.getInstance().getUserData().getTenantId());
                hashMap.put("data", data);

                repository.getQueryFlow(hashMap, new NetObserver<GoodsQueryFlowResponse>(GoodsQueryFlowViewModel.this) {
                    @Override
                    protected void onError(NetError error) {
                        super.onError(error);
                    }

                    @Override
                    protected void onComplete(boolean error) {
                        super.onComplete(error);
                        mRefresh.postValue(false);
                    }

                    @Override
                    protected void onSuccess(GoodsQueryFlowResponse bean) {
                        if (bean == null || bean.getItemList() == null) return;
                        callback.onResult(bean.getItemList());
                    }
                });

            }
        }
    }
}
