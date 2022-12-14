package com.jd.saas.pdadelivery.detail;


import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.printer_ble.router.BlueToothPrintRouterPath;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.utils.ListUtils;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.base.DeliveryBaseViewModel;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDetailBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDiffReasonBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDiffSkuBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryPrintParam;
import com.jd.saas.pdadelivery.detail.bean.DeliveryShelfLifeInfoBean;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryStockTypeBean;
import com.jd.saas.pdadelivery.manage.bean.DeliveryBean;
import com.jd.saas.pdadelivery.net.DeliveryConvertor;
import com.jd.saas.pdadelivery.net.DeliveryRepository;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.net.enums.DiffTypeEnum;
import com.jd.saas.pdadelivery.net.param.PageQueryParam;
import com.jd.saas.pdadelivery.net.param.Param;
import com.jd.saas.pdadelivery.net.param.QueryExpiryDateGoodsStatusDataParam;
import com.jd.saas.pdadelivery.net.param.QueryLocationsDataParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvDiffListParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvSkuStockDataParam;
import com.jd.saas.pdadelivery.net.param.ReceiveAsnFinishDataParam;
import com.jd.saas.pdadelivery.net.param.StockExchangeParam;
import com.jd.saas.pdadelivery.net.param.SubmitDataParam;
import com.jd.saas.pdadelivery.net.param.SubmitRcvSkuParam;
import com.jd.saas.pdadelivery.net.result.DiffListAndDiffReasonResult;
import com.jd.saas.pdadelivery.net.result.PagedResult;
import com.jd.saas.pdadelivery.net.result.QueryLocationsResult;
import com.jd.saas.pdadelivery.net.result.QueryRcvDiffListResult;
import com.jd.saas.pdadelivery.net.result.StockDetailResult;
import com.jd.saas.pdadelivery.net.result.StockExchangeResult;
import com.jd.saas.pdadelivery.util.Formatter;
import com.jd.saas.pdadelivery.util.LocTypeUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * ?????????????????????????????????????????????
 * <p>
 * ?????????????????????????????????
 * ?????????????????????????????????????????????????????????????????????????????????
 * ????????????????????????????????????????????????????????????????????????Tab??????????????????????????????combinedSkuList
 * ????????????????????????????????????skuList????????????????????????????????????????????????????????????combinedInputQty???????????????????????????????????????????????????????????????????????????????????????
 * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 * ??????????????????????????????????????????????????????????????????????????????+??????+?????????????????????????????????0????????????
 * ??????????????????????????????tab,????????????????????????????????????????????????????????????
 */
public class DeliveryDetailViewModel extends DeliveryBaseViewModel {

    private final DeliveryRepository repository;

    public DeliveryDetailViewModel(DeliveryRepository repository) {
        this.repository = repository;
        submitList.setValue(new ArrayList<>());
    }

    /**
     * ?????????????????????
     */
    public MutableLiveData<DeliveryDetailBean> bean = new MutableLiveData<>();
    /**
     * ??????????????????????????????
     */
    public LiveData<Boolean> isFinish = Transformations.map(bean, input ->
            input.getStatus() == AsnStatusEnum.RECEIVED.getValue()
                    || input.getStatus() == AsnStatusEnum.DIFF_AUDIT.getValue());
    /**
     * ????????????????????????
     */
    public LiveData<Integer> searchBarVisibility = Transformations.map(isFinish, input -> input ? View.GONE : View.VISIBLE);
    /**
     * ?????????????????????????????????
     */
    public LiveData<Integer> bottomBtnViewVisibility = Transformations.map(isFinish, input -> input ? View.GONE : View.VISIBLE);
    /**
     * ???????????????????????????
     */
    public LiveData<Integer> closeTimeViewVisibility = Transformations.map(isFinish, input -> input ? View.VISIBLE : View.GONE);
    /**
     * ??????????????????tab??????????????????
     */
    public final MutableLiveData<Boolean> needShowCombinedSkuTab = new MutableLiveData<>(false);
    /**
     * ???????????????????????????????????????????????????
     */
    public LiveData<Integer> cntViewVisibility = Transformations.map(isFinish, input -> input ? View.GONE : View.VISIBLE);
    /**
     * ????????????????????????????????????????????????
     */
    public LiveData<Integer> cntView2Visibility = Transformations.map(isFinish, input -> input ? View.VISIBLE : View.GONE);

    /**
     * ???????????????tab??????
     * ????????????/????????????
     */
    public MutableLiveData<Integer> selectTabType = new MutableLiveData<>(0);

    /**
     * ???????????????????????????
     */
    public LiveData<ArrayList<DeliverySkuBean>> skuList = Transformations.map(bean, DeliveryDetailBean::getSkuList);
    /**
     * ???????????????????????????
     */
    public final ArrayList<DeliverySkuBean> combinedSkuList = new ArrayList<>();
    /**
     * ?????????????????????????????????
     */
    public MutableLiveData<List<DeliverySkuBean>> submitList = new MutableLiveData<>();
    /**
     * ?????????????????????????????????
     */
    public LiveData<String> editSkuIdCnt = Transformations.map(submitList, input -> String.valueOf(input.size()));
    /**
     * ???????????????????????????
     */
    public LiveData<String> editSkuSumCnt = Transformations.map(submitList, input -> {
        BigDecimal sum = BigDecimal.ZERO;
        for (DeliverySkuBean skuBean : input) {
            sum = sum.add(skuBean.getInputQty()).add(skuBean.getCombinedInputQty());
        }
        return Formatter.format(sum);
    });

    /**
     * ?????????????????? ?????????????????????
     */
    public MutableLiveData<DeliverySkuBean> editSku = new MutableLiveData<>();

    /**
     * ?????????????????????
     */
    private List<DeliveryStockTypeBean> stockTypeList = null;
    /**
     * ??????????????????????????????????????????????????????????????? ??????UI??????
     */
    public MutableLiveData<Pair<String, Integer>> logsBeanEvent = new MutableLiveData<>();
    /**
     * ???????????????????????????
     */
    public MutableLiveData<Boolean> submitSuccessEvent = new MutableLiveData<>();
    /**
     * ?????????????????????
     */
    public MutableLiveData<Boolean> finishAsnSuccessEvent = new MutableLiveData<>();
    /**
     * ?????????????????????????????????????????????????????? ??????????????????????????????
     */
    public MutableLiveData<Pair<Long, Integer>> expiryDateGoodsStatusEvent = new MutableLiveData<>();
    /**
     * ??????????????????????????????????????????
     */
    public MutableLiveData<Boolean> oneKeyFillNotifyEvent = new MutableLiveData<>(false);

    /**
     * ??????????????????????????????
     */
    public MutableLiveData<List<DeliveryDiffSkuBean>> deliveryDiffSkuList = new MutableLiveData<>();
    /**
     * ??????????????????
     */
    public List<DeliveryDiffReasonBean> lessReasons = new ArrayList<>();
    /**
     * ??????????????????
     */
    public List<DeliveryDiffReasonBean> overReasons = new ArrayList<>();

    /**
     * ???????????????
     */
    public MutableLiveData<Boolean> showLoadingDialog = new MutableLiveData<>(false);


    /**
     * ??????????????????????????????
     *
     * @param deliveryBean ???????????????????????????
     */
    public void initData(DeliveryBean deliveryBean) {
        showProgress.set(true);
        repository.queryLocationsByPage(new Param<>(new QueryLocationsDataParam()), new NetObserver<PagedResult<QueryLocationsResult>>(this) {
            @Override
            protected void onSuccess(PagedResult<QueryLocationsResult> queryLocationsResultPagedResult) {
                if (queryLocationsResultPagedResult == null) {
                    showProgress.set(false);
                    netError.set(new NetError(1, MyApplication.getInstance().getString(R.string.delivery_query_loc_error_tips)));
                    return;
                }
                List<QueryLocationsResult> itemList = queryLocationsResultPagedResult.getItemList();
                if (itemList == null || itemList.isEmpty()) {
                    showProgress.set(false);
                    netError.set(new NetError(1, MyApplication.getInstance().getString(R.string.delivery_query_loc_error_tips)));
                    return;
                }
                stockTypeList = ListUtils.map(itemList, DeliveryConvertor::convert);
                getDetailByNo(deliveryBean);
            }

            @Override
            protected void onError(NetError error) {
                super.onError(error);
                showProgress.set(false);
            }
        });
    }

    /**
     * ????????????po
     * */
    public boolean isContainsPo(){
        return bean.getValue().getAsnRefNo().contains("PO");
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param deliveryBean ?????????????????????
     */
    public void getDetailByNo(DeliveryBean deliveryBean) {
        showProgress.set(true);
        QueryRcvSkuStockDataParam param = new QueryRcvSkuStockDataParam(deliveryBean.getAsnNo());
        repository.queryRcvSkuStock(new Param<>(param), new NetObserver<StockDetailResult>(this) {
            @Override
            protected void onSuccess(StockDetailResult result) {
                if (result == null) {
                    return;
                }
                DeliveryDetailBean deliveryDetailBean = DeliveryConvertor.convert(result);
                deliveryDetailBean.setSupplierName(deliveryBean.getSupplierName());
                deliveryDetailBean.setAsnRefNo(deliveryBean.getAsnRefNo());
                ArrayList<DeliverySkuBean> skuList = deliveryDetailBean.getSkuList();
                if (skuList != null) {
                    for (DeliverySkuBean skuBean : skuList) {
                        applyDefaultStockType(skuBean);
                    }
                }
                bean.postValue(deliveryDetailBean);
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                showProgress.set(false);
            }
        });
    }

    private void applyDefaultStockType(DeliverySkuBean skuBean) {
        String good = LocTypeUtils.getDefaultStockType(skuBean.getSkuType());
        for (DeliveryStockTypeBean stockTypeBean : stockTypeList) {
            if (good.equals(stockTypeBean.getLocType())) {
                skuBean.setLocName(stockTypeBean.getLocName());
                skuBean.setLocType(stockTypeBean.getLocType());
                skuBean.setLocId(stockTypeBean.getLocId());
            }
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param skuType ???????????? {@link com.jd.saas.pdadelivery.net.enums.SkuTypeEnum}
     * @return ??????????????????
     */
    public List<DeliveryStockTypeBean> getStockTypeList(int skuType) {
        return ListUtils.filter(stockTypeList, item -> LocTypeUtils.isMatch(skuType, item.getLocType()));
    }

    /**
     * ??????????????????????????? ????????????????????????????????? ?????????????????????
     *
     * @param lotType ???????????? {@link com.jd.saas.pdadelivery.net.enums.LocTypeEnum}
     * @return ????????????????????????????????????
     */
    @Nullable
    public DeliveryStockTypeBean getStockTypeByLotType(@NonNull String lotType) {
        for (DeliveryStockTypeBean deliveryStockTypeBean : stockTypeList) {
            if (lotType.equals(deliveryStockTypeBean.getLocType())) {
                return deliveryStockTypeBean;
            }
        }
        return null;
    }

    /**
     * ??????
     *
     * @param asnNo ????????????
     */
    public void finishAsn(String asnNo) {
        showLoadingDialog.setValue(true);
        repository.receiveAsnFinish(new Param<>(new ReceiveAsnFinishDataParam(asnNo)), new BaseObserver<String>() {
            @Override
            protected void onSuccess(String s) {
                finishAsnSuccessEvent.postValue(true);
            }

            @Override
            protected void onError(NetError error) {
                showToastEvent.postValue(error.getMsg());
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                showLoadingDialog.setValue(false);
            }
        });
    }

    /**
     * ?????? ????????????????????????
     *
     * @param asnNo               ????????????
     * @param deliveryDiffSkuList ??????????????????
     */
    public void receiveAsnFinishByDiff(String asnNo, List<DeliveryDiffSkuBean> deliveryDiffSkuList) {
        showLoadingDialog.setValue(true);
        ReceiveAsnFinishDataParam asnFinishDataParam = new ReceiveAsnFinishDataParam(asnNo);
        asnFinishDataParam.setDiffResultDTOS(ListUtils.map(deliveryDiffSkuList, DeliveryConvertor::convert));
        repository.receiveAsnFinishByDiff(new Param<>(asnFinishDataParam), new BaseObserver<String>() {
            @Override
            protected void onSuccess(String s) {
                //????????????????????????????????? ??????2?????????????????????????????????
                Single.just(true).delay(2, TimeUnit.SECONDS).subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showLoadingDialog.postValue(true);
                    }

                    @Override
                    public void onSuccess(Boolean s) {
                        finishAsnSuccessEvent.postValue(true);
                        showLoadingDialog.postValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showLoadingDialog.postValue(false);
                    }
                });

            }

            @Override
            protected void onError(NetError error) {
                showToastEvent.postValue(error.getMsg());
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                showLoadingDialog.setValue(false);
            }
        });
    }

    public void changeTab(int select) {
        Integer oldType = selectTabType.getValue();
        if (oldType == null) {
            return;
        }
        if (oldType == select) {
            return;
        }
        selectTabType.setValue(select);
    }

    public void oneKeyFill() {
        ArrayList<DeliverySkuBean> deliverySkuBeans = skuList.getValue();
        if (deliverySkuBeans == null) {
            return;
        }
        ArrayList<DeliverySkuBean> nullableList = ListUtils.map(deliverySkuBeans, from -> {
            if (from.isShelfLifeSup()) {
                return null;
            }
            applyDefaultStockType(from);
            from.setCombinedInputQty(BigDecimal.ZERO);
            from.setInputQty(from.getExpectedQty().subtract(from.getActualQty()));
            return from;
        });
        List<DeliverySkuBean> notNullList = ListUtils.filter(nullableList, item -> item != null);
        if (nullableList.size() != notNullList.size()) {
            showToastEvent.postValue(MyApplication.getInstance().getString(R.string.delivery_detail_shelf_life_one_key_fill_tips));
        }
        List<DeliverySkuBean> noZeroList = ListUtils.filter(nullableList, item -> item != null && item.getInputQty().compareTo(BigDecimal.ZERO) > 0);
        if (noZeroList.isEmpty()) {
            return;
        }
        submitList.postValue(noZeroList);
        combinedSkuList.clear();
        needShowCombinedSkuTab.postValue(false);
        oneKeyFillNotifyEvent.postValue(true);
    }

    /**
     * ????????????
     * ??????submitList????????????
     */
    public void commit() {
        List<DeliverySkuBean> submitListValue = submitList.getValue();

        if (submitListValue == null || submitListValue.isEmpty()) {
            showToastEvent.postValue(MyApplication.getInstance().getString(R.string.delivery_submit_empty_tips));
            return;
        }
        if (bean.getValue() == null) {
            return;
        }
        showLoadingDialog.setValue(true);
        ArrayList<SubmitRcvSkuParam> submitRcvSkuParams = DeliveryConvertor.convert(submitListValue);
        DeliveryConvertor.mergeCombinedSkuList(submitRcvSkuParams, combinedSkuList);
        //??????????????????????????????0???
        submitRcvSkuParams = new ArrayList<>(ListUtils.filter(submitRcvSkuParams, item -> item.getQty().compareTo(BigDecimal.ZERO) > 0));
        SubmitDataParam submitDataParam = new SubmitDataParam();
        submitDataParam.setAsnNo(bean.getValue().getAsnNo());
        submitDataParam.setSubmitRcvSkuDtos(submitRcvSkuParams);
        repository.submitRcv(new Param<>(submitDataParam), new BaseObserver<String>() {
            @Override
            protected void onSuccess(String s) {
                submitSuccessEvent.postValue(true);
            }

            @Override
            protected void onError(NetError error) {
                showToastEvent.postValue(error.getMsg());
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                showLoadingDialog.setValue(false);
            }
        });
    }

    /**
     * ?????????????????????????????????
     *
     * @param skuId ??????ID
     */
    public void getSkuLogList(String skuId) {
        StockExchangeParam param = new StockExchangeParam();
        param.setSkuId(skuId);
        if (bean.getValue() != null) {
            param.setDocNo(bean.getValue().getAsnRefNo());
        }
        repository.pagingQueryStockExchangeByLot(new Param<>(param), new BaseObserver<PagedResult<StockExchangeResult>>() {
            @Override
            protected void onSuccess(PagedResult<StockExchangeResult> result) {
                ArrayList<DeliverySkuBean> list = skuList.getValue();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        DeliverySkuBean skuBean = list.get(i);
                        if (skuId.equals(skuBean.getSkuId())) {
                            if (result == null || result.getItemList() == null) {
                                skuBean.setLogBeans(new ArrayList<>());
                            } else {
                                skuBean.setLogBeans(ListUtils.map(result.getItemList(), DeliveryConvertor::convert));
                            }

                            logsBeanEvent.postValue(new Pair<>(skuId, i));
                            break;
                        }
                    }
                }

            }

            @Override
            protected void onError(NetError error) {
                showToastEvent.postValue(error.getMsg());
            }
        });
    }

    /**
     * ?????????????????????????????????
     *
     * @param skuId ??????ID
     * @param bean  ????????????
     */
    public void queryExpiryDateGoodsStatus(String skuId, DeliveryShelfLifeInfoBean bean) {
        QueryExpiryDateGoodsStatusDataParam param = new QueryExpiryDateGoodsStatusDataParam();
        param.setSkuId(skuId);
        if (bean.getCreateDate() != null) {
            param.setProduceDate(bean.getCreateDate().getTime());
        }
        repository.queryExpiryDateGoodsStatus(new Param<>(param), new BaseObserver<Integer>() {
            @Override
            protected void onSuccess(Integer integer) {
                if (integer == null) {
                    return;
                }
                expiryDateGoodsStatusEvent.postValue(new Pair<>(bean.getLocalId(), integer));
            }

            @Override
            protected void onError(NetError error) {
                if (error != null) {
                    showToastEvent.postValue(error.getMsg());
                }
            }
        });
    }

    /**
     * ????????????
     * */
    public void startPrint(){
        showLoadingDialog.setValue(true);
        DeliveryPrintParam deliveryPrintParam = new DeliveryPrintParam();
        deliveryPrintParam.setOrderNo(bean.getValue().getAsnRefNo());
        deliveryPrintParam.setPin(UserManager.getInstance().getUserData().getUserPin());
        deliveryPrintParam.setTenantId(UserManager.getInstance().getUserData().getTenantId());
        repository.queryPoPrintInfo(new Param<>(deliveryPrintParam), new BaseObserver<DirectDeliveryOrderBean>() {
            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                showLoadingDialog.setValue(false);
            }

            @Override
            protected void onSuccess(DirectDeliveryOrderBean bean) {
                if(bean.getDetailList().size()>100){
                    MyToast.show("?????????????????????????????????",false);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                bundle.putSerializable("bean",bean);
                RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), BlueToothPrintRouterPath.COMMON_PATH__BLUETOOTH,bundle);
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);

            }
        });
    }

    /**
     * ?????????????????????????????????
     * <p>
     * ????????????????????????????????????????????????????????? ?????????????????? ???????????????????????????????????????????????????????????????????????????
     *
     * @param asnNo ????????????
     */
    public void queryDiff(String asnNo) {
        showLoadingDialog.setValue(true);
        repository.queryRcvDiffList(new Param<>(new QueryRcvDiffListParam(asnNo, new PageQueryParam(1, 20))), new BaseObserver<DiffListAndDiffReasonResult>() {
            @Override
            protected void onSuccess(DiffListAndDiffReasonResult result) {
                List<QueryRcvDiffListResult> itemList = result.getResult().getItemList();
                lessReasons = ListUtils.map(result.getLessReasonResult(), DeliveryConvertor::convert);
                overReasons = ListUtils.map(result.getOverReasonResult(), DeliveryConvertor::convert);
                if (itemList == null) {
                    ArrayList<DeliveryDiffSkuBean> value = new ArrayList<>();
                    deliveryDiffSkuList.setValue(value);
                } else {
                    deliveryDiffSkuList.setValue(ListUtils.map(itemList, DeliveryConvertor::convert));
                }
            }

            @Override
            protected void onError(NetError error) {
                if (error != null) {
                    showToastEvent.postValue(error.getMsg());
                }
            }

            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                showLoadingDialog.setValue(false);
            }
        });
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param diffType ???????????? {@link DiffTypeEnum}
     * @return ???????????????????????????
     */
    public List<DeliveryDiffReasonBean> getDiffReasons(int diffType) {
        if (diffType == DiffTypeEnum.MORE.getValue()) {
            return overReasons;
        } else {
            return lessReasons;
        }
    }

    /**
     * ??????????????????
     * ?????????????????????????????????????????????????????????
     * ??????????????????????????????????????????
     */
    public void searchCombinedSku(String key) {
        SearchGoodsQueryBean body = new SearchGoodsQueryBean();
        body.setStoreId(UserManager.getInstance().getUserData().getShopId());
        body.setPin(UserManager.getInstance().getUserData().getUserPin());
        body.setPage("1");
        body.setPageSize("1");
        body.setCondition(key);
        repository.getGoodsList(new Param<>(body), new BaseObserver<SearchResultBean>() {
            @Override
            protected void onSuccess(SearchResultBean searchResultBean) {
                if (searchResultBean == null) {
                    searchLocalSku(key);
                    return;
                }
                List<SearchResultBean.BoxProducts> boxProducts = searchResultBean.getBoxProducts();
                if (stockTypeList == null) {
                    return;
                }
                ArrayList<DeliverySkuBean> skuListValue = skuList.getValue();
                if (skuListValue == null) {
                    return;
                }
                //?????????????????????
                if (boxProducts != null && !boxProducts.isEmpty()) {
                    //???????????????????????????????????? ???????????????
                    for (DeliverySkuBean deliverySkuBean : combinedSkuList) {
                        if (deliverySkuBean.getSkuId().equals(key) || deliverySkuBean.getUpcCode().equals(key)) {
                            editSku.postValue(deliverySkuBean);
                            return;
                        }
                    }

                    //?????????????????????????????????
                    boolean searchResultInDeliveryOrder = true;
                    //?????????????????????????????????
                    boolean childContainsShelfLifeSku = false;

                    for (SearchResultBean.BoxProducts boxProduct : boxProducts) {
                        //???????????????????????????
                        boolean boxProductInDeliveryOrder = false;
                        for (DeliverySkuBean skuBean : skuListValue) {
                            if (skuBean.getSkuId().equals(boxProduct.getSkuId())) {
                                if (skuBean.isShelfLifeSup()) {
                                    //??????????????????????????? ????????????????????????????????????????????????
                                    childContainsShelfLifeSku = true;
                                }
                                boxProductInDeliveryOrder = true;
                                break;
                            }
                        }
                        if (!boxProductInDeliveryOrder) {
                            searchResultInDeliveryOrder = false;
                            break;
                        }
                    }
                    if (!searchResultInDeliveryOrder) {
                        showToastEvent.postValue(MyApplication.getInstance().getResources().getString(R.string.delivery_detail_not_contain_combined_sku));
                        return;
                    }
                    if (childContainsShelfLifeSku) {
                        showToastEvent.postValue(MyApplication.getInstance().getResources().getString(R.string.delivery_detail_not_support_shelf_life_in_combined_sku));
                        return;
                    }
                    DeliverySkuBean searchSku = DeliveryConvertor.convert(bean.getValue().getStatus(), searchResultBean, from -> {
                        for (int i = 0; i < skuListValue.size(); i++) {
                            if (skuListValue.get(i).getSkuId().equals(from.getSkuId())) {
                                return DeliveryConvertor.convert(from, skuListValue.get(i), stockTypeList);
                            }
                        }
                        return null;
                    });
                    editSku.postValue(searchSku);
                } else {
                    searchLocalSku(key);
                }
            }

            @Override
            protected void onError(NetError error) {
                searchLocalSku(key);
            }
        });
    }

    private void searchLocalSku(String upcOrSkuId) {
        ArrayList<DeliverySkuBean> skuListValue = skuList.getValue();
        if (skuListValue == null) {
            showToastEvent.postValue(MyApplication.getInstance().getString(R.string.delivery_search_result_empty));
            return;
        }
        DeliverySkuBean localBean = null;
        for (DeliverySkuBean bean : skuListValue) {
            String[] upcCodes = bean.getUpcCodes();
            if (upcCodes != null) {
                for (String upcCode : upcCodes) {
                    if (upcOrSkuId.equals(upcCode)) {
                        localBean = bean;
                        break;
                    }
                }
            }
            if (upcOrSkuId.equals(bean.getSkuId())) {
                localBean = bean;
                break;
            }
        }
        if (localBean != null) {
            editSku.postValue(localBean);
        } else {
            showToastEvent.postValue(MyApplication.getInstance().getString(R.string.delivery_search_result_empty));
        }

    }

    /**
     * ?????????????????? ??????????????????
     *
     * @param bean ????????????
     */
    public void addSku2SubmitList(DeliverySkuBean bean) {
        List<DeliverySkuBean> submitListValue = submitList.getValue();
        if (submitListValue == null) {
            submitListValue = new ArrayList<>();
        }
        boolean needAdd = true;
        for (int i = 0; i < submitListValue.size(); i++) {
            DeliverySkuBean item = submitListValue.get(i);
            if (item.getSkuId().equals(bean.getSkuId())) {
                submitListValue.set(i, bean);
                needAdd = false;
                break;
            }
        }
        if (needAdd) {
            submitListValue.add(bean);
        }
        List<DeliverySkuBean> filterZero = ListUtils.filter(submitListValue, item ->
                item != null && (item.getInputQty().compareTo(BigDecimal.ZERO) > 0 || item.getCombinedInputQty().compareTo(BigDecimal.ZERO) > 0));
        submitList.postValue(filterZero);
    }

    /**
     * tab???????????? ??????????????????
     */
    public boolean isTabShowSkuList() {
        return selectTabType.getValue() == null || selectTabType.getValue() == 0;
    }

    /**
     * ???sku????????????????????????????????????
     *
     * @param bean ????????????SKU
     */
    public void move2Top(DeliverySkuBean bean) {
        if (bean.isCombined()) {
            move2Top(combinedSkuList, bean, true);
            needShowCombinedSkuTab.postValue(!combinedSkuList.isEmpty());
        } else {
            ArrayList<DeliverySkuBean> value = skuList.getValue();
            if (value != null) {
                move2Top(value, bean, false);
            }
        }
    }

    private void move2Top(List<DeliverySkuBean> skuBeans, DeliverySkuBean bean, boolean addIfNotExist) {
        int index = skuBeans.indexOf(bean);
        if (index > 0) {
            skuBeans.remove(index);
            skuBeans.add(0, bean);
        } else if (index < 0 && addIfNotExist) {
            skuBeans.add(0, bean);
        }
    }
}

