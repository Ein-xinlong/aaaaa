package com.jd.saas.padinventory.create;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.adjustment.detail.InventoryAdjustmentDetailItemBean;
import com.jd.saas.padinventory.net.AdjustmentRepository;
import com.jd.saas.padinventory.net.InventoryPdaTwoBean;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.search.InventoryCreateDialogBean;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchGoodsResultAdapter;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.zxing.router.ZxingRouterPath;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 新建调整单ViewModel
 *
 * @author ext.mengmeng
 */
public class InventoryCreateViewModel extends NetViewModel {

    // 请求网络初始化
    private final AdjustmentRepository mAdjustmentRepository = new AdjustmentRepository();

    public ObservableField<String> mInputBox = new ObservableField<>("0");//个数或称重
    public ObservableField<String> number = new ObservableField<>("1234567890");//只能输入数字
    public ObservableField<String> point = new ObservableField<>("1234567890.");//可以输入小数点

    private String mReasonCode;//報溢原因
    private String mStorage = "9999";//库位   默认好品库位
    private String mReasonCodeName;//原因名字
    private String mProfitOrLossCode;//报损类型

    private LinearLayoutManager mSearchLinearLayoutManager;
    private final SearchResultListFactory mFactory = new SearchResultListFactory();
    private final int PAGE_SIZE = 10; // 一页分页数
    public LiveData<PagedList<SearchGoodBean>> mSearchListLiveData = new LivePagedListBuilder<>(mFactory, PAGE_SIZE).build();

    public ObservableField<String> mTitle = new ObservableField<>();//标题
    public ObservableField<String> mImport = new ObservableField<>("");//输入框
    public ObservableField<String> mBarcodeNumber = new ObservableField<>();//商品条码
    public ObservableField<String> mUnit = new ObservableField<>();//规格
    public ObservableField<String> mImageUrl = new ObservableField<>();//logo
    public ObservableField<String> mCause = new ObservableField<>(MyApplication.getInstance().getString(R.string.inventory_create_place_cause));//有效期
    public ObservableField<String> mGood = new ObservableField<>(MyApplication.getInstance().getString(R.string.inventory_create_good_quality));//库位名称
    public ObservableField<String> mNumberGoodQty = new ObservableField<>();//好品库位number
    public ObservableField<Boolean> mVis = new ObservableField<>(true);//判断是否隐藏加减
    public ObservableField<Boolean> mSearchContentVisible = new ObservableField<>(true);
    public MutableLiveData<Boolean> mHideKeyBoard = new MutableLiveData<>(false);// 隐藏软键盘

    private String mSearchContent = "";// 搜索内容
    private SearchGoodsResultAdapter mSearchGoodsResultAdapter;
    // 全局待提交列表
    public static ObservableField<List<InventoryAdjustmentDetailItemBean>> mAdjustmentList = new ObservableField<>(new ArrayList<>());

    // 商品编号
    private String mSkuId;
    private String NewCase;
    private String mQytAvailable = "0";// 库存
    private List<InventoryCreateDialogBean> mLocQtyList;
    //判断库位是否能展开
    private boolean isTrue = false;

    // 数量格式化
    private final DecimalFormat mDecimalFormat = new DecimalFormat("######0.000");

    /**
     * 点击复制按钮
     */
    public void copy(View view) {
        ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        String number = mBarcodeNumber.get();
        ClipData mClipData = ClipData.newPlainText("Label", number);
        cm.setPrimaryClip(mClipData);
        MyToast.show(R.string.inventory_create_copy_success, false);
    }

    /**
     * 二维码扫描方法
     */
    public void screenGo(Context context) {
        RouterClient.getInstance().forward(context, ZxingRouterPath.PATH_ZXING, new Bundle(), InventoryCreateRouterPath.REQUEST_CODE_SCAN);
    }

    /**
     * 点击加调用
     */
    public void add() {
        if (!mVis.get()) {
            double v = Double.parseDouble(mInputBox.get());
            v = v + 1;
            String format = mDecimalFormat.format(v);
            mInputBox.set(format);
        } else {
            if (TextUtils.isEmpty(mInputBox.get())) {
                mInputBox.set("0");
            }
            int i = Integer.parseInt(mInputBox.get());
            i = i + 1;
            mInputBox.set(i + "");
        }
    }

    /**
     * 点击减调用
     */
    public void minus() {
        if (!mVis.get()) {
            double v = Double.parseDouble(mInputBox.get());
            double num = v - 1;
            if (num < 0) {
                return;
            }
            String format = mDecimalFormat.format(num);
            mInputBox.set(format);
        } else {
            int i = Integer.parseInt(mInputBox.get());
            if (i < 1) {
                return;
            }
            i = i - 1;
            mInputBox.set(i + "");
        }
    }

    /**
     * 将损益单保存到本地
     */
    public void create() {
        if (TextUtils.isEmpty(mProfitOrLossCode)) {
            MyToast.show(R.string.inventory_create_breakage_or_overflow, false);
        } else if (TextUtils.isEmpty(mStorage)) {
            MyToast.show(R.string.inventory_create_location_reason, false);
        } else if (mInputBox.get().equalsIgnoreCase("0") || TextUtils.isEmpty(mInputBox.get())) {
            MyToast.show(R.string.inventory_create_number_reason, false);
        } else if (TextUtils.isEmpty(mReasonCode)) {
            MyToast.show(R.string.inventory_create_overflow_reason, false);
        } else if (mVis.get()) {
            if (Integer.parseInt(mNumberGoodQty.get()) <= 0 && Integer.parseInt(mProfitOrLossCode) == 0) {
                MyToast.show(R.string.inventory_create_null_good, false);
            } else if (Integer.parseInt(mNumberGoodQty.get()) < Integer.parseInt(mInputBox.get()) && Integer.parseInt(mProfitOrLossCode) == 0) {
                MyToast.show(R.string.inventory_create_Bigger_than, false);
            } else {
                for (int i = 0; i < mAdjustmentList.get().size(); i++) {
                    if (mAdjustmentList.get().get(i).getSkuID().equals(mSkuId) && !mProfitOrLossCode.equals(mAdjustmentList.get().get(i).getProfitOrLossCode()) && mProfitOrLossCode.equals("1") && mAdjustmentList.get().get(i).getStorage_location_name().equals(mGood.get())) {
                        MyToast.show(R.string.inventory_create_no_overflow, false);
                        return;
                    } else if (mAdjustmentList.get().get(i).getSkuID().equals(mSkuId) && mProfitOrLossCode.equals("0") && !mProfitOrLossCode.equals(mAdjustmentList.get().get(i).getProfitOrLossCode()) && mAdjustmentList.get().get(i).getStorage_location_name().equals(mGood.get())) {
                        MyToast.show(R.string.inventory_create_no_breakage, false);
                        return;
                    } else if (mAdjustmentList.get().get(i).getSkuID().equals(mSkuId) && mAdjustmentList.get().get(i).getStorage_location_name().equals(mGood.get())) {
                        MyToast.show(R.string.inventory_create_already_location, false);
                        return;
                    }
                }
                addData();
            }

        } else if (!mVis.get()) {
            if (Double.parseDouble(mNumberGoodQty.get()) <= 0 && Integer.parseInt(mProfitOrLossCode) == 0) {
                MyToast.show(R.string.inventory_create_null_good, false);
            }else if (Double.parseDouble(mNumberGoodQty.get()) < Double.parseDouble(mInputBox.get()) && Integer.parseInt(mProfitOrLossCode) == 0) {
                MyToast.show(R.string.inventory_create_Bigger_than, false);
                return;
            } else {
                for (int i = 0; i < mAdjustmentList.get().size(); i++) {
                    if (mAdjustmentList.get().get(i).getSkuID().equals(mSkuId) && !mProfitOrLossCode.equals(mAdjustmentList.get().get(i).getProfitOrLossCode()) && mProfitOrLossCode.equals("1") && mAdjustmentList.get().get(i).getStorage_location_name().equals(mGood.get())) {
                        MyToast.show(R.string.inventory_create_no_overflow, false);
                        return;
                    } else if (mAdjustmentList.get().get(i).getSkuID().equals(mSkuId) && mProfitOrLossCode.equals("0") && !mProfitOrLossCode.equals(mAdjustmentList.get().get(i).getProfitOrLossCode()) && mAdjustmentList.get().get(i).getStorage_location_name().equals(mGood.get())) {
                        MyToast.show(R.string.inventory_create_no_breakage, false);
                        return;
                    } else if (mAdjustmentList.get().get(i).getSkuID().equals(mSkuId) && mAdjustmentList.get().get(i).getStorage_location_name().equals(mGood.get())) {
                        MyToast.show(R.string.inventory_create_already_location, false);
                        return;
                    }
                }
                addData();
            }
        }
    }

    /**
     * 添加到本地
     */
    public void addData() {
        if (mAdjustmentList.get().size() < 50) {
            InventoryAdjustmentDetailItemBean bean = new InventoryAdjustmentDetailItemBean();
            bean.setTitle(mTitle.get());
            bean.setNumber(mBarcodeNumber.get());
            bean.setSpecification(mUnit.get());
            bean.setStorage_location(mStorage);
            bean.setStorage_location_name(mGood.get());
            bean.setStorage_location_cause(mReasonCode);
            bean.setStorage_location_cause_name(mReasonCodeName);
            bean.setProfitOrLossCode(mProfitOrLossCode);
            bean.setStorage_location_number(mInputBox.get());
            bean.setImageUrl(mImageUrl.get());
            bean.setSkuID(mSkuId);
            bean.setQytAvailable(mQytAvailable);
            mAdjustmentList.get().add(bean);
            mAdjustmentList.notifyChange();
            MyToast.show(MyApplication.getInstance().getString(R.string.inventory_create_success), false);
            // 清空本次商品数据
            removeCurrentGoods();
        } else {
            MyToast.show(R.string.inventory_create_most, false);
        }
    }

    /**
     * 清空当前商品数据信息，初始化
     */
    private void removeCurrentGoods() {
        mInputBox.set("0");
        mTitle.set("");
        mImport.set("");
        mImport.notifyChange();
        mBarcodeNumber.set("");
        mUnit.set("");
        mImageUrl.set("");
        mCause.set(MyApplication.getInstance().getString(R.string.inventory_create_place_cause));
        mReasonCode=null;
        mGood.set("");
        mNumberGoodQty.set("0");
        mVis.set(true);
        mSearchContentVisible.set(true);
        mHideKeyBoard.setValue(true);
        isTrue = false;
    }

    /**
     * 数量、称重输入转为字符
     */
    public void setReceiverNameTwo(Editable editable) {
        if(!TextUtils.isEmpty(editable.toString())) {
            mInputBox.set(editable.toString());
        }else {
            // 如若用户手动删除数字，默认归零并通知UI
            mInputBox.set("0");
            mInputBox.notifyChange();
        }
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
     * 获取搜索列表adapter
     */
    public SearchGoodsResultAdapter getSearchResultAdapter() {

        if (null == mSearchGoodsResultAdapter) {
            mSearchGoodsResultAdapter = new SearchGoodsResultAdapter();
            mSearchGoodsResultAdapter.setOnItemClickListener(searchGoodBean -> {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                InventoryPdaTwoBean inventoryPdaTwoBean = new InventoryPdaTwoBean();
                inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("clientInfo", inventoryPdaTwoBean);
                InventoryCreateQueryBean bean = new InventoryCreateQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setCondition(searchGoodBean.getSkuID());
                hashMap.put("data", bean);
                showProgress.set(true);
                mAdjustmentRepository.Goods(hashMap, new NetObserver<SearchResultBean>(this) {
                    @Override
                    protected void onSuccess(SearchResultBean inventoryGoodsBean) {
                        if (inventoryGoodsBean != null) {
                            String upcCode = inventoryGoodsBean.getUpcCode();//商品条码
                            String skuName = inventoryGoodsBean.getSkuName();//商品名称
                            String saleUnit = inventoryGoodsBean.getSaleUnitName();//规格单位
                            int saleMode = inventoryGoodsBean.getSaleMode();

                            mLocQtyList = inventoryGoodsBean.getLocQtyList();
                            if (inventoryGoodsBean.getLocQtyList() != null) {
                                if (inventoryGoodsBean.getLocQtyList().size() == 0) {
                                    mNumberGoodQty.set("0");
                                    mStorage = null;
                                } else {
                                    if (inventoryGoodsBean.getLocQtyList().size() > 1) {
                                        isTrue = true;
                                    }
                                    mNumberGoodQty.set(inventoryGoodsBean.getLocQtyList().get(0).getQtyAvailable() + "");
                                    // 区分门店/仓
                                    mGood.set(AppTypeUtil.getAppType() == 1 ? inventoryGoodsBean.getLocQtyList().get(0).getLocName() : inventoryGoodsBean.getLocQtyList().get(0).getLocCode());
                                    mStorage = inventoryGoodsBean.getLocQtyList().get(0).getLocId() + "";
                                    //无用代码，暂时屏蔽
//                                    for (int i = 0; i < inventoryGoodsBean.getLocQtyList().size(); i++) {
//                                        mNumberGoodQty.set(inventoryGoodsBean.getLocQtyList().get(i).getQtyAvailable() + "");
//                                        // 区分门店/仓
//                                        mGood.set(AppTypeUtil.getAppType() == 1 ? inventoryGoodsBean.getLocQtyList().get(0).getLocName() : inventoryGoodsBean.getLocQtyList().get(0).getLocCode());
//                                        mStorage = inventoryGoodsBean.getLocQtyList().get(i).getLocId() + "";
//                                    }
                                    mSearchContentVisible.set(false);
                                }
                            } else {
                                MyToast.show(R.string.inventory_create_no_stock, false);
                                mNumberGoodQty.set("0");
                                mStorage = null;
                                mGood.set(MyApplication.getInstance().getString(R.string.inventory_create_dialog_title));
                            }
                            if (saleMode == 1) {
                                mVis.set(true);
                            } else {
                                mVis.set(false);
                            }
                            String logo = inventoryGoodsBean.getLogo();
                            mSkuId = inventoryGoodsBean.getSkuId();
                            String qytAvailable = inventoryGoodsBean.getSkuQty();
                            mImageUrl.set(logo);
                            mTitle.set(skuName);
                            mBarcodeNumber.set(upcCode);
                            mUnit.set(saleUnit);
                            if (TextUtils.isEmpty(qytAvailable)) {
                                mQytAvailable = "0";
                            } else {
                                mQytAvailable = qytAvailable;
                            }
                            mHideKeyBoard.setValue(true);
                        } else {
                            MyToast.show(R.string.inventory_create_no_shop, false);
                        }
                    }

                    @Override
                    protected void onComplete(boolean error) {
                        showProgress.set(false);
                    }
                });

            });
        }
        return mSearchGoodsResultAdapter;
    }

    /**
     * et每次输入字符都调用该方法
     */
    public void searchContent(Editable editable) {
        // 搜索ui显/隐
        mSearchContent = editable.toString();
        mSearchContentVisible.set(true);
        refreshSearchList();
    }

    /**
     * 重新刷新搜索结果-下拉刷新-商品搜索界面暂时用不到，此为示例
     */
    public void refreshSearchList() {
        mFactory.refresh();
    }

    /**
     * 库位dialog
     */
    public void storageLocation(View view) {
        if (!isTrue) {
            return;
        }
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.inventory_adjustment_create_storage, null);
        ImageView close = dialogView.findViewById(R.id.image);
        RecyclerView recyclerView = dialogView.findViewById(R.id.diarecy);
        InventoryCreateStorageAdapter adapter = new InventoryCreateStorageAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.refreshList(mLocQtyList);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialogView);
        BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) dialogView.getParent());
        mDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
        close.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });
        adapter.setOnCheckedChange(new InventoryCreateStorageAdapter.OnCheckedChange() {
            @Override
            public void checkedChange(InventoryCreateDialogBean bean) {
                mGood.set(AppTypeUtil.getAppType() == 1 ? bean.getLocName() : bean.getLocCode());
                mStorage = bean.getLocId() + "";
                mNumberGoodQty.set(bean.getQtyAvailable() + "");
                bottomSheetDialog.dismiss();
            }
        });
    }


    /**
     * 原因dialog
     */
    public void cause(View view) {
        if (TextUtils.isEmpty(NewCase)) {
            MyToast.show(R.string.inventory_Check_type, false);
            return;
        }
        // 初始化列表view
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.inventory_adjustment_create_cause, null);
        RecyclerView listView = dialogView.findViewById(R.id.cause_recyclerview);
        ImageView closeBtn = dialogView.findViewById(R.id.image);
        InventoryCreateStorageCauseAdapter mAdapter = new InventoryCreateStorageCauseAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(mAdapter);
        // 初始化dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialogView);
        BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) dialogView.getParent());
        mDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        closeBtn.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });
        mAdapter.setOnCheckedChange(pos -> {
            String dictCode = returnLossCode();
            String dictName = mAdapter.getDictName(pos);
            mCause.set(dictName);
            mReasonCodeName = dictName;
            mReasonCode = dictCode;
            bottomSheetDialog.dismiss();
        });
        // 请求网络
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        CreateCauseRepostBean bean = new CreateCauseRepostBean();
        bean.setTenantId( UserManager.getInstance().getUserData().getTenantId());
        bean.setReasonCode(returnLossCode());
        hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
        InventoryPdaTwoBean inventoryPdaTwoBean = new InventoryPdaTwoBean();
        inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", inventoryPdaTwoBean);
        hashMap.put("data", bean);
        mAdjustmentRepository.cause(hashMap, new NetObserver<List<String>>(this) {
            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(List<String> bean) {
                if (bean != null) {
                    mAdapter.refreshList(bean);
                    bottomSheetDialog.show();
                }
            }


        });
    }

    /**
     * 原因接口入参
     * */
    String returnLossCode(){
        if(NewCase=="LOSS"){
            return "2";
        }else{
            return "3";
        }
    }
    /**
     * 搜素结果list factory
     */
    private class SearchResultListFactory extends DataSource.Factory<Integer, SearchGoodBean> {

        private SearchResultListDataSource mDataSource;

        void refresh() {
            if(null != mDataSource) {
                mDataSource.invalidate();
            }
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
                if ("".equals(mSearchContent) || null == mSearchContent) {
                    return;
                }
                mPage = 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                InventoryPdaTwoBean inventoryPdaTwoBean = new InventoryPdaTwoBean();
                inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("clientInfo", inventoryPdaTwoBean);
                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                bean.setCondition(mSearchContent);
                hashMap.put("data", bean);
                mAdjustmentRepository.getGoodsList(hashMap, new NetObserver<SearchResultBean>(InventoryCreateViewModel.this) {

                    @Override
                    protected void onSuccess(SearchResultBean bean) {
                        if (null == bean) {
                            return;
                        }
                        if (bean.getData() == null) {
                            String upcCode = bean.getUpcCode();//商品条码
                            String skuName = bean.getSkuName();//商品名称
                            String saleUnit = bean.getSaleUnitName();//规格单位

                            int saleMode = bean.getSaleMode();
                            if (saleMode == 1) {
                                mVis.set(true);
                            } else {
                                mVis.set(false);
                            }
                            mLocQtyList = bean.getLocQtyList();
                            if (bean.getLocQtyList() != null) {
                                if (bean.getLocQtyList().size() == 0) {
                                    mNumberGoodQty.set("0");
                                    mStorage = null;
                                } else {
                                    if (bean.getLocQtyList().size() > 1) {
                                        isTrue = true;
                                    }
                                    mNumberGoodQty.set(bean.getLocQtyList().get(0).getQtyAvailable() + "");
                                    // 区分门店/仓
                                    mGood.set(AppTypeUtil.getAppType() == 1 ? bean.getLocQtyList().get(0).getLocName() : bean.getLocQtyList().get(0).getLocCode());
                                    mStorage = bean.getLocQtyList().get(0).getLocId() + "";
                                    //无用代码，暂时屏蔽
//                                    for (int i = 0; i < bean.getLocQtyList().size(); i++) {
//                                        if (bean.getLocQtyList().get(i).getLocName().equals(R.string.inventory_create_good_quality)) {
//                                            mNumberGoodQty.set(bean.getLocQtyList().get(i).getQtyAvailable() + "");
//                                            // 区分门店/仓
//                                            mGood.set(AppTypeUtil.getAppType() == 1 ? bean.getLocQtyList().get(0).getLocName() : bean.getLocQtyList().get(0).getLocCode());
//                                            mStorage = bean.getLocQtyList().get(i).getLocId() + "";
//                                        }
//                                    }
                                    mSearchContentVisible.set(false);
                                }
                            } else {
                                MyToast.show(R.string.inventory_create_no_stock, false);
                                mNumberGoodQty.set("0");
                                mStorage = null;
                                mGood.set(MyApplication.getInstance().getString(R.string.inventory_create_dialog_title));
                            }
                            String logo = bean.getLogo();
                            mSkuId = bean.getSkuId();
                            String qytAvailable = bean.getSkuQty();
                            mImageUrl.set(logo);
                            mTitle.set(skuName);
                            mBarcodeNumber.set(upcCode);
                            mUnit.set(saleUnit);
                            if (TextUtils.isEmpty(qytAvailable)) {
                                mQytAvailable = "0";
                            } else {
                                mQytAvailable = qytAvailable;
                            }
                        }
                        if (null != bean.getData()) {
                            callback.onResult(bean.getData(), 0, bean.getTotal());
                        }
                    }

                    @Override
                    protected void onError(NetError error) {
//                        super.onError(error);
                    }
                });
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<SearchGoodBean> callback) {
                mPage = mPage + 1;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                InventoryPdaTwoBean inventoryPdaTwoBean = new InventoryPdaTwoBean();
                inventoryPdaTwoBean.setBizCode(AppTypeUtil.getAppType());
                hashMap.put("clientInfo", inventoryPdaTwoBean);
                SearchGoodsQueryBean bean = new SearchGoodsQueryBean();
                bean.setPin(UserManager.getInstance().getUserData().getUserPin());
                bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                bean.setPage(mPage + "");
                bean.setPageSize(PAGE_SIZE + "");
                bean.setCondition(mSearchContent);
                hashMap.put("data", bean);
                mAdjustmentRepository.getGoodsList(hashMap, new NetObserver<SearchResultBean>(InventoryCreateViewModel.this) {

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

    /**
     * 报损类型选择
     */
    public void lossReport() {
        mProfitOrLossCode = "0";
        NewCase = "LOSS";
        mReasonCode = null;
        mCause.set(MyApplication.getInstance().getString(R.string.inventory_create_place_cause));
        mInputBox.set("0");
    }

    /**
     * 报溢类型选择
     */
    public void reportOverflow() {
        mProfitOrLossCode = "1";
        NewCase = "GAIN";
        mReasonCode = null;
        mCause.set(MyApplication.getInstance().getString(R.string.inventory_create_place_cause));
        mInputBox.set("0");
    }


    /**
     * 退出弹窗
     */
    public void showExitDialog(Context context) {
        if (null != mAdjustmentList && mAdjustmentList.get().size() != 0) {
            new SimpleAlertDialog.Builder(context, R.string.inventory_create_finish)
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 清空全局调整单明细数据
                            mAdjustmentList.get().clear();
                            AppManager.getInstance().finishActivity();
                        }
                    }).build().show();
        } else {
            AppManager.getInstance().finishActivity();
        }
    }
}
