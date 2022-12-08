package com.jd.saas.pdainventorycheck.details;

import android.content.DialogInterface;
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

import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.calendar.CalendarBottomSheetDialog;
import com.jd.saas.pdacommon.dialog.linkage.StockTransTypeSelectDialog;
import com.jd.saas.pdacommon.dialog.linkage.model.ChildTansType;
import com.jd.saas.pdacommon.dialog.linkage.model.ParentTansType;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckFlowBean;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckFlowRequest;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckFlowResponse;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckLocationBean;
import com.jd.saas.pdainventorycheck.details.model.InventoryCheckLocationResponse;
import com.jd.saas.pdainventorycheck.details.net.InventoryCheckDetailsRepository;
import com.jd.saas.pdainventorycheck.details.ui.InventoryCheckBarcodeDialog;
import com.jd.saas.pdainventorycheck.net.InventoryCheckInquireClientInfoBean;

import java.util.HashMap;

/**
 * 库存详情
 *
 * @author ext.mengmeng
 */
public class InventoryCheckDetailsViewModel extends NetViewModel {

    private String mEndTime = "";
    private String mStartTime = "";
    private String mTransType;
    private int mPage;
    private final int PAGE_SIZE = 10;// 一页分页数
    private final InventoryCheckDetailsRepository mRepository = new InventoryCheckDetailsRepository();
    public MutableLiveData<Boolean> mFlowRefresh = new MutableLiveData<>();
    public MutableLiveData<Boolean> mLocationRefresh = new MutableLiveData<>();
    //时间选择器，时间显示
    public ObservableField<String> mSelectTime = new ObservableField<>();
    //库位分页;
    private final LocationListFactory mLocationFactory = new LocationListFactory();
    public LiveData<PagedList<InventoryCheckLocationBean>> mLocationLiveData;
    //流水;
    private final FlowListFactory mFlowFactory = new FlowListFactory();
    public LiveData<PagedList<InventoryCheckFlowBean>> mFlowLiveData;
    //商品信息
    public ObservableField<SearchResultBean> mInfoData = new ObservableField<>();

    public ParentTansType mParentType;
    public ChildTansType mChildType;
    public ObservableField<String> mSelectedType = new ObservableField<>();
    //库位|库存流水显隐
    public ObservableField<Boolean> showFlowOrLocation = new ObservableField<>();

    /**
     * 初始化vm
     */
    public void init(SearchResultBean searchResultBean) {
        mInfoData.set(searchResultBean);
        mLocationLiveData = new LivePagedListBuilder<>(mLocationFactory, PAGE_SIZE).build();
        mFlowLiveData = new LivePagedListBuilder<>(mFlowFactory, PAGE_SIZE).build();
    }

    /**
     * 流水列表刷新
     */
    public void refreshFlowList() {
        mFlowFactory.refresh();
    }

    /**
     * 库位列表刷新
     */
    public void refreshLocationList() {
        mLocationFactory.refresh();
    }

    /**
     * 条码-查看更多按钮
     * */
    public void more(View view) {

        InventoryCheckBarcodeDialog dialog = new InventoryCheckBarcodeDialog
                .Builder(view.getContext(), mInfoData.get().getUpcCode())
                .setCloseListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();

    }

    /**
     * 日期选择
     */
    public void dataSelect(View view) {

        CalendarBottomSheetDialog bottomSheetDialog = new CalendarBottomSheetDialog(view.getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setOnDateSelected(new CalendarBottomSheetDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(String startDate, String endDate) {
                if (TextUtils.isEmpty(startDate)) {
                    mSelectTime.set(null);
                    mStartTime = "";
                    mEndTime = "";
                } else {
                    mSelectTime.set(startDate + ":" + endDate);
                    mStartTime = startDate;
                    mEndTime = endDate;
                    mFlowRefresh.setValue(true);
                }
                refreshFlowList();

            }
        });
        bottomSheetDialog.show();
        ;
    }

    /*
     * 状态选择
     *
     REC_NORM("REC_NORM", "收货"),
     ALLOC("ALLOC", "分配"),
     OUT("OUT", "出库"),
     MV("MV", "移库"),
     ADJUST("ADJUST", "调整"),
     CHECK("CHECK", "盘点"),
     LOT_ADJUST("LOT_ADJUST", "批次效期调整");
     */

    /**
     * 状态选择
     */
    public void statusSelect(View view) {
        StockTransTypeSelectDialog dialog = new StockTransTypeSelectDialog(view.getContext());
        dialog.setOnSelectListener((parentTansType, childTansType) -> {
            mChildType = childTansType;
            mParentType = parentTansType;
            //选中 默认库位石，传入父类型名称
            if (mChildType.getName().equals(view.getResources().getString(R.string.stock_trans_type_default))) {
                mSelectedType.set(parentTansType.getName());
            } else {
                mSelectedType.set(mChildType.getName());
            }
            mFlowRefresh.postValue(true);
            refreshFlowList();
        });
        dialog.show();
    }

    /**
     * 库位信息list factory
     */
    private class LocationListFactory extends DataSource.Factory<Integer, InventoryCheckLocationBean> {

        private LocationListDataSource dataSource;

        void refresh() {
            dataSource.invalidate();
        }

        @NonNull
        @Override
        public DataSource<Integer, InventoryCheckLocationBean> create() {
            dataSource = new LocationListFactory.LocationListDataSource();
            return dataSource;
        }

        /**
         * 搜索结果list data source
         */
        private class LocationListDataSource extends PositionalDataSource<InventoryCheckLocationBean> {

            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<InventoryCheckLocationBean> callback) {
                mPage = 1;
                initLocationInfo(callback);
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<InventoryCheckLocationBean> callback) {
                mPage = mPage + 1;
                rangeLocationInfo(callback);
            }
        }
    }

    private HashMap questParameter() {
        HashMap<String, Object> hashMap = new HashMap<>();
        InventoryCheckInquireClientInfoBean inventoryPdaTwoBean = new InventoryCheckInquireClientInfoBean();
        inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", inventoryPdaTwoBean);
        hashMap.put("page", mPage);
        hashMap.put("pageSize", PAGE_SIZE);
        hashMap.put("pageNo", 1);
        hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("warehouseId", UserManager.getInstance().getUserData().getShopId());
        hashMap.put("skuId", mInfoData.get().getSkuId());
        return hashMap;
    }

    private void initLocationInfo(PositionalDataSource.LoadInitialCallback<InventoryCheckLocationBean> callback) {
        HashMap hashMap = questParameter();
        mRepository.getLocation(hashMap, new NetObserver<InventoryCheckLocationResponse>(InventoryCheckDetailsViewModel.this) {
            @Override
            protected void onSuccess(InventoryCheckLocationResponse response) {
                callback.onResult(response.getItemList(), 0, response.getTotal());
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                mLocationRefresh.setValue(false);

            }
        });

    }

    private void rangeLocationInfo(PositionalDataSource.LoadRangeCallback<InventoryCheckLocationBean> callback) {
        HashMap hashMap = questParameter();
        mRepository.getLocation(hashMap, new NetObserver<InventoryCheckLocationResponse>(InventoryCheckDetailsViewModel.this) {
            @Override
            protected void onSuccess(InventoryCheckLocationResponse response) {
                callback.onResult(response.getItemList());
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);

            }
        });

    }

    /**
     * 搜素结果list factory
     */
    private class FlowListFactory extends DataSource.Factory<Integer, InventoryCheckFlowBean> {

        private FlowListDataSource dataSource;

        void refresh() {
            dataSource.invalidate();
        }

        @NonNull
        @Override
        public DataSource<Integer, InventoryCheckFlowBean> create() {
            dataSource = new FlowListFactory.FlowListDataSource();
            return dataSource;
        }

        /**
         * 搜索结果list data source
         */
        private class FlowListDataSource extends PositionalDataSource<InventoryCheckFlowBean> {

            private int mPage;

            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<InventoryCheckFlowBean> callback) {
                mPage = 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                InventoryCheckInquireClientInfoBean inventoryCheckInquireClientInfoBean = new InventoryCheckInquireClientInfoBean();
                inventoryCheckInquireClientInfoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("clientInfo", inventoryCheckInquireClientInfoBean);
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                InventoryCheckFlowRequest data = new InventoryCheckFlowRequest();
                data.setSkuId(mInfoData.get().getSkuId());
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

                mRepository.getQueryFlow(hashMap, new NetObserver<InventoryCheckFlowResponse>(InventoryCheckDetailsViewModel.this) {
                    @Override
                    protected void onError(NetError error) {
                        super.onError(error);

                    }

                    @Override
                    protected void onComplete(boolean error) {
                        super.onComplete(error);
                        mFlowRefresh.setValue(false);
                    }

                    @Override
                    protected void onSuccess(InventoryCheckFlowResponse bean) {

                        if (null != bean && null != bean.getItemList()) {
                            callback.onResult(bean.getItemList(), 0, bean.getTotal());

                        }


                    }
                });

            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<InventoryCheckFlowBean> callback) {
                mPage = mPage + 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());

                InventoryCheckFlowRequest data = new InventoryCheckFlowRequest();
                data.setSkuId(mInfoData.get().getSkuId());
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

                mRepository.getQueryFlow(hashMap, new NetObserver<InventoryCheckFlowResponse>(InventoryCheckDetailsViewModel.this) {
                    @Override
                    protected void onError(NetError error) {
                        super.onError(error);
                    }

                    @Override
                    protected void onComplete(boolean error) {
                        super.onComplete(error);
                        mFlowRefresh.postValue(false);
                    }

                    @Override
                    protected void onSuccess(InventoryCheckFlowResponse bean) {
                        if (bean == null || bean.getItemList() == null) return;
                        callback.onResult(bean.getItemList());
                    }
                });

            }
        }
    }
}
