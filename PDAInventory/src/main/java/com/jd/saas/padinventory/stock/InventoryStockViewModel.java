package com.jd.saas.padinventory.stock;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.net.AdjustmentRepository;
import com.jd.saas.padinventory.net.InventoryPdaTwoBean;
import com.jd.saas.padinventory.net.StockRequestBean;
import com.jd.saas.padinventory.stock.adapter.InventoryStatusAdapter;
import com.jd.saas.padinventory.stock.adapter.InventoryStockAdapter;
import com.jd.saas.padinventory.stock.router.InventoryStockRouterPath;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.calendar.CalendarBottomSheetDialog;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.zxing.router.ZxingRouterPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存调整vm
 *
 * @author majiheng
 */
public class InventoryStockViewModel extends NetViewModel {

    // 网路
    private final AdjustmentRepository mAdjustmentRepository = new AdjustmentRepository();

    private InventoryStockAdapter mAdapter;
    private final int PAGE_SIZE = 10;

    // 筛选状态string
    public ObservableField<String> mStatus = new ObservableField<>(MyApplication.getInstance().getApplicationContext().getString(R.string.inventory_stock_all_status));
    // 日期string
    public ObservableField<String> mDate = new ObservableField<>(MyApplication.getInstance().getApplicationContext().getString(R.string.inventory_stock_data_selected));
    // 有无数据显示
    public ObservableField<Boolean> mData = new ObservableField<>(true);
    // 是否loading
    public MutableLiveData<Boolean> mLoading = new MutableLiveData<>(true);

    // 模糊搜索相关
    private final SearchResultListFactory mFactory = new SearchResultListFactory();//全部展示分页查询
    //记录审核状态-默认「全部状态」0
    private int mStatusInt = 0;
    // 搜索内容
    public ObservableField<String> mSearchContent = new ObservableField<>("");
    public MutableLiveData<Boolean> mHideKeyBoard = new MutableLiveData<>(false);// 隐藏软键盘
    public LiveData<PagedList<InventoryStockRepostBean.ItemListBean>> mSearchListLiveData = new LivePagedListBuilder<>(mFactory,
            new PagedList.Config
                    .Builder().setPageSize(PAGE_SIZE)
                    .setInitialLoadSizeHint(PAGE_SIZE)
                    .build())
            .build();

    // 开始时间
    private String mStartTime = null;
    // 结束时间
    private String mEndTime = null;
    private final int[] statusList = new int[]{40, 50, 60};

    /**
     * 跳转到二维码扫描模块
     */
    public void screenGo(Context context) {
        RouterClient.getInstance().forward(context, ZxingRouterPath.PATH_ZXING, new Bundle(), InventoryStockRouterPath.REQUEST_CODE_SCAN);
    }

    /**
     * 跳转新建页面
     */
    public void onCreateStock(Context context) {
        RouterClient.getInstance().forward(context, "pda://native.CreateModule/InventoryCreatePage");
    }

    /**
     * 日期选择
     */
    public void dateSelect(View view) {
        CalendarBottomSheetDialog bottomSheetDialog = new CalendarBottomSheetDialog(view.getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setOnDateSelected((startDate, endDate) -> {
            if (TextUtils.isEmpty(startDate)) {
                mDate.set(MyApplication.getInstance().getString(R.string.inventory_stock_data_selected));
                mStartTime = null;
                mEndTime = null;
            } else {
                mStartTime = startDate + "T00:00:00.422+0800";
                mEndTime = endDate + "T23:59:59.422+0800";
                mDate.set(startDate + ":" + endDate);
            }
            refreshList();
        });
        bottomSheetDialog.show();
    }


    /**
     * et每次输入字符都调用该方法
     */
    public void searchContent(Editable editable) {
        mSearchContent.set(editable.toString());
        if (TextUtils.isEmpty(mSearchContent.get())) {
            // 体验-如果用户清空搜索框，重新加载列表
            refreshList();
        }
    }

    /**
     * 状态选择
     */
    public void statusSelect(Context context) {
        View dialog = LayoutInflater.from(context).inflate(R.layout.inventory_stock_status_dialog, null);
        RecyclerView recyclerView = dialog.findViewById(R.id.status_recyclerview);
        ImageView imageView = dialog.findViewById(R.id.iv_close);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        InventoryStatusAdapter adapter = new InventoryStatusAdapter();
        List<InventoryStatusBean> list = addList();
        adapter.refreshList(list);
        recyclerView.setAdapter(adapter);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialog);
        BottomSheetBehavior dialogBehavior = BottomSheetBehavior.from((View) dialog.getParent());
        dialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
        adapter.setOnCheckedChange(new InventoryStatusAdapter.OnCheckedChange() {
            @Override
            public void checkedChange(InventoryStatusBean bean) {
                // 设置ui
                mStatus.set(bean.getName());
                // 设置选中状态
                mStatusInt = bean.getStatus();
                // 刷新列表
                refreshList();
                // dialog消失
                bottomSheetDialog.dismiss();
            }
        });
        imageView.setOnClickListener(v -> bottomSheetDialog.dismiss());
    }

    /**
     * 状态dialog默认值
     */
    public List<InventoryStatusBean> addList() {
        List<InventoryStatusBean> list = new ArrayList<>();
        InventoryStatusBean bean = new InventoryStatusBean();
        bean.setName(MyApplication.getInstance().getString(R.string.inventory_create_Approved));
        bean.setStatus(40);
        list.add(bean);
        InventoryStatusBean bean1 = new InventoryStatusBean();
        bean1.setName(MyApplication.getInstance().getString(R.string.inventory_create_under_review));
        bean1.setStatus(50);
        list.add(bean1);
        InventoryStatusBean bean2 = new InventoryStatusBean();
        bean2.setName(MyApplication.getInstance().getString(R.string.inventory_create_approval_failed));
        bean2.setStatus(60);
        list.add(bean2);
        InventoryStatusBean bean3 = new InventoryStatusBean();
        bean3.setName(MyApplication.getInstance().getString(R.string.inventory_stock_all_status));
        bean3.setStatus(0);
        list.add(bean3);
        return list;
    }


    /**
     * 获取列表adapter
     */
    public InventoryStockAdapter getAdapter() {
        if (null == mAdapter) {
            mAdapter = new InventoryStockAdapter();
        }
        return mAdapter;
    }

    /**
     * 精确查找损益单
     */
    public void search(String searchContent) {
        if (TextUtils.isEmpty(searchContent)) {
            MyToast.show(R.string.inventory_stock_search_empty, false);
        } else {
            mHideKeyBoard.setValue(true);
            mSearchContent.set(searchContent);
            refreshList();
        }
    }

    /**
     * 下拉刷新列表
     */
    public void refreshList() {
        mLoading.setValue(true);
        mFactory.refresh();
    }


    private class SearchResultListFactory extends DataSource.Factory<Integer, InventoryStockRepostBean.ItemListBean> {

        private final MutableLiveData<SearchResultListDataSource> mDataSource = new MutableLiveData<>();

        void refresh() {
            mDataSource.getValue().invalidate();
        }

        @NonNull
        @Override
        public DataSource<Integer, InventoryStockRepostBean.ItemListBean> create() {
            SearchResultListFactory.SearchResultListDataSource addressListDataSource = new SearchResultListFactory.SearchResultListDataSource();
            mDataSource.postValue(addressListDataSource);
            return addressListDataSource;
        }

        /**
         * 分页查询所有损益单
         */
        private class SearchResultListDataSource extends PageKeyedDataSource<Integer, InventoryStockRepostBean.ItemListBean> {


            @Override
            public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, InventoryStockRepostBean.ItemListBean> callback) {
                HashMap<String, Object> hashMap = new HashMap<>();
                InventoryPdaTwoBean inventoryPdaTwoBean = new InventoryPdaTwoBean();
                inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("clientInfo", inventoryPdaTwoBean);
                StockRequestBean bean = new StockRequestBean();
                bean.setTenantId(UserManager.getInstance().getUserData().getTenantId());
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setWarehouseId(UserManager.getInstance().getUserData().getShopId());
                bean.setPage("1");
                bean.setGalBizType("3");
                bean.setPageSize(params.requestedLoadSize + "");

                if (TextUtils.isEmpty(mSearchContent.get())) {//如果搜索框里面有内容则搜索
                    bean.setCreateStartTime(mStartTime);
                    bean.setCreateEndTime(mEndTime);
                    if (mStatusInt == 0) {
                        bean.setStatusList(statusList);
                    } else {
                        bean.setStatus(mStatusInt + "");
                    }
                } else {
                    bean.setGalNo(mSearchContent.get());
                }
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
                hashMap.put("data", bean);
                mAdjustmentRepository.stock(hashMap, new NetObserver<InventoryStockRepostBean>(InventoryStockViewModel.this) {
                    @Override
                    protected void onComplete(boolean error) {
                        mLoading.setValue(false);
                    }

                    @Override
                    protected void onSuccess(InventoryStockRepostBean bean) {
                        if (bean != null && bean.getItemList() != null && bean.getItemList().size() != 0) {
                            callback.onResult(bean.getItemList(), null, bean.getTotalPage() <= 1 ? null : 2);
                            mData.set(bean.getItemList().size() <= 0);
                        } else {
                            mData.set(true);
                        }
                    }
                });
            }

            @Override
            public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, InventoryStockRepostBean.ItemListBean> callback) {

            }

            @Override
            public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, InventoryStockRepostBean.ItemListBean> callback) {
                HashMap<String, Object> hashMap = returnbean(params);
                mAdjustmentRepository.stock(hashMap, new NetObserver<InventoryStockRepostBean>(InventoryStockViewModel.this) {
                    @Override
                    protected void onSuccess(InventoryStockRepostBean bean) {
                        if (bean != null && bean.getItemList() != null && bean.getItemList().size() != 0) {
                            callback.onResult(bean.getItemList(), bean.getTotalPage() <= params.key ? null : params.key + 1);
                        }
                    }
                });
            }
        }
        //组装入参返回
        public HashMap returnbean(PageKeyedDataSource.LoadParams<Integer> params){
            HashMap<String, Object> hashMap = new HashMap<>();
            InventoryPdaTwoBean inventoryPdaTwoBean = new InventoryPdaTwoBean();
            inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
            hashMap.put("clientInfo", inventoryPdaTwoBean);
            StockRequestBean bean = new StockRequestBean();
            bean.setTenantId(UserManager.getInstance().getUserData().getTenantId());
            bean.setPin(UserManager.getInstance().getUserData().getUserPin());
            bean.setWarehouseId(UserManager.getInstance().getUserData().getShopId());
            bean.setPage(params.key + "");
            bean.setPageSize(params.requestedLoadSize + "");
            bean.setCreateStartTime(mStartTime);
            bean.setCreateEndTime(mEndTime);
            bean.setGalBizType("3");
            if (mStatusInt == 0) {
                bean.setStatusList(statusList);
            } else {
                bean.setStatus(mStatusInt + "");
            }
            hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
            hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
            hashMap.put("data", bean);
            return hashMap;
        }
    }
}
