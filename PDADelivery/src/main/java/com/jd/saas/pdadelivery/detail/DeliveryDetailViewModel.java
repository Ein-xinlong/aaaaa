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
 * 收货详情页，用于提交收货和关单
 * <p>
 * 特别说明：组合商品逻辑
 * 页面默认加载入库单商品信息子品维度，组合品对用户不可见
 * 当用户扫码搜索等方式主动添加组合品时，显示组合品Tab，记录组合品收货数据combinedSkuList
 * 组合品收货时，在商品列表skuList中会记录每种商品因组合品而添加的收货数量combinedInputQty，每次修改数量后重新计算，并调整组合品和单品的收货列表顺序
 * 单品收货列表只做更新和修改，组合品收货列表可以添加、删除和修改并将修改同步到单品的信息中
 * 提交时，将单品收货信息和组合收货信息合并为提交的商品+库位+数量的信息，过滤数量为0的占位项
 * 提交后，隐藏组合品的tab,恢复无组合品的状态，清空组合品的收货记录
 */
public class DeliveryDetailViewModel extends DeliveryBaseViewModel {

    private final DeliveryRepository repository;

    public DeliveryDetailViewModel(DeliveryRepository repository) {
        this.repository = repository;
        submitList.setValue(new ArrayList<>());
    }

    /**
     * 收货单详情信息
     */
    public MutableLiveData<DeliveryDetailBean> bean = new MutableLiveData<>();
    /**
     * 是否是已完成的收货单
     */
    public LiveData<Boolean> isFinish = Transformations.map(bean, input ->
            input.getStatus() == AsnStatusEnum.RECEIVED.getValue()
                    || input.getStatus() == AsnStatusEnum.DIFF_AUDIT.getValue());
    /**
     * 搜索框的显示状态
     */
    public LiveData<Integer> searchBarVisibility = Transformations.map(isFinish, input -> input ? View.GONE : View.VISIBLE);
    /**
     * 底部提交按钮的显示状态
     */
    public LiveData<Integer> bottomBtnViewVisibility = Transformations.map(isFinish, input -> input ? View.GONE : View.VISIBLE);
    /**
     * 结束时间的显示状态
     */
    public LiveData<Integer> closeTimeViewVisibility = Transformations.map(isFinish, input -> input ? View.VISIBLE : View.GONE);
    /**
     * 组合商品切换tab条的显示状态
     */
    public final MutableLiveData<Boolean> needShowCombinedSkuTab = new MutableLiveData<>(false);
    /**
     * 非完成的收货单的数量信息的显示状态
     */
    public LiveData<Integer> cntViewVisibility = Transformations.map(isFinish, input -> input ? View.GONE : View.VISIBLE);
    /**
     * 已完成的收货单数量信息的显示状态
     */
    public LiveData<Integer> cntView2Visibility = Transformations.map(isFinish, input -> input ? View.VISIBLE : View.GONE);

    /**
     * 当前切换的tab类型
     * 单个商品/组合商品
     */
    public MutableLiveData<Integer> selectTabType = new MutableLiveData<>(0);

    /**
     * 收货单中的商品列表
     */
    public LiveData<ArrayList<DeliverySkuBean>> skuList = Transformations.map(bean, DeliveryDetailBean::getSkuList);
    /**
     * 收货单中的商品列表
     */
    public final ArrayList<DeliverySkuBean> combinedSkuList = new ArrayList<>();
    /**
     * 准备提交收货的商品信息
     */
    public MutableLiveData<List<DeliverySkuBean>> submitList = new MutableLiveData<>();
    /**
     * 本次收货的商品种类数量
     */
    public LiveData<String> editSkuIdCnt = Transformations.map(submitList, input -> String.valueOf(input.size()));
    /**
     * 本次收货的商品件数
     */
    public LiveData<String> editSkuSumCnt = Transformations.map(submitList, input -> {
        BigDecimal sum = BigDecimal.ZERO;
        for (DeliverySkuBean skuBean : input) {
            sum = sum.add(skuBean.getInputQty()).add(skuBean.getCombinedInputQty());
        }
        return Formatter.format(sum);
    });

    /**
     * 待编辑的商品 供收货弹窗使用
     */
    public MutableLiveData<DeliverySkuBean> editSku = new MutableLiveData<>();

    /**
     * 库存类型的列表
     */
    private List<DeliveryStockTypeBean> stockTypeList = null;
    /**
     * 某个商品的收货明细日志列表信息加载完毕事件 通知UI更新
     */
    public MutableLiveData<Pair<String, Integer>> logsBeanEvent = new MutableLiveData<>();
    /**
     * 提交收货成功的事件
     */
    public MutableLiveData<Boolean> submitSuccessEvent = new MutableLiveData<>();
    /**
     * 关单成功的事件
     */
    public MutableLiveData<Boolean> finishAsnSuccessEvent = new MutableLiveData<>();
    /**
     * 获取效期商品的效期提示状态成功的事件 通知列表中的选项刷新
     */
    public MutableLiveData<Pair<Long, Integer>> expiryDateGoodsStatusEvent = new MutableLiveData<>();
    /**
     * 一键收货成功后更新页面的事件
     */
    public MutableLiveData<Boolean> oneKeyFillNotifyEvent = new MutableLiveData<>(false);

    /**
     * 关单前差异商品的列表
     */
    public MutableLiveData<List<DeliveryDiffSkuBean>> deliveryDiffSkuList = new MutableLiveData<>();
    /**
     * 可选缺收原因
     */
    public List<DeliveryDiffReasonBean> lessReasons = new ArrayList<>();
    /**
     * 可选超收原因
     */
    public List<DeliveryDiffReasonBean> overReasons = new ArrayList<>();

    /**
     * 显示加载框
     */
    public MutableLiveData<Boolean> showLoadingDialog = new MutableLiveData<>(false);


    /**
     * 初始化页面数据的方法
     *
     * @param deliveryBean 列表中的收货单信息
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
     * 是否包含po
     * */
    public boolean isContainsPo(){
        return bean.getValue().getAsnRefNo().contains("PO");
    }

    /**
     * 获取详情信息或者刷新页面的方法
     *
     * @param deliveryBean 收货单索引信息
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
     * 获取某类商品的可选库位
     *
     * @param skuType 商品类型 {@link com.jd.saas.pdadelivery.net.enums.SkuTypeEnum}
     * @return 可选库位列表
     */
    public List<DeliveryStockTypeBean> getStockTypeList(int skuType) {
        return ListUtils.filter(stockTypeList, item -> LocTypeUtils.isMatch(skuType, item.getLocType()));
    }

    /**
     * 获取某个库位的信息 如果是同种类型存在多个 则只获取第一个
     *
     * @param lotType 库位类型 {@link com.jd.saas.pdadelivery.net.enums.LocTypeEnum}
     * @return 对应类型的库位的详细信息
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
     * 关单
     *
     * @param asnNo 收货单号
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
     * 关单 同时提交差异原因
     *
     * @param asnNo               收货单号
     * @param deliveryDiffSkuList 商品差异原因
     */
    public void receiveAsnFinishByDiff(String asnNo, List<DeliveryDiffSkuBean> deliveryDiffSkuList) {
        showLoadingDialog.setValue(true);
        ReceiveAsnFinishDataParam asnFinishDataParam = new ReceiveAsnFinishDataParam(asnNo);
        asnFinishDataParam.setDiffResultDTOS(ListUtils.map(deliveryDiffSkuList, DeliveryConvertor::convert));
        repository.receiveAsnFinishByDiff(new Param<>(asnFinishDataParam), new BaseObserver<String>() {
            @Override
            protected void onSuccess(String s) {
                //关单可能不会立刻完成， 添加2秒左右的延迟后刷新状态
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
     * 提交收货
     * 提交submitList中的内容
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
        //过滤合并后数量仍旧为0的
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
     * 获取某个商品的收货明细
     *
     * @param skuId 商品ID
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
     * 获取某个商品的效期状态
     *
     * @param skuId 商品ID
     * @param bean  效期信息
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
     * 调取打印
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
                    MyToast.show("数据较多，暂时无法打印",false);
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
     * 关单前获取差异商品列表
     * <p>
     * 这个方法同时请求三个接口：收货差异列表 可选超收原因 可选缺收原因，三个信息都有的情况下才能显示差异弹窗
     *
     * @param asnNo 收货单号
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
     * 获取超收或缺收的可选原因列表
     *
     * @param diffType 差异类型 {@link DiffTypeEnum}
     * @return 缓存的可选原因列表
     */
    public List<DeliveryDiffReasonBean> getDiffReasons(int diffType) {
        if (diffType == DiffTypeEnum.MORE.getValue()) {
            return overReasons;
        } else {
            return lessReasons;
        }
    }

    /**
     * 搜索组合商品
     * 如果是组合商品则显示组合商品的编辑弹窗
     * 如果不是组合商品则使用旧逻辑
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
                //组合商品的逻辑
                if (boxProducts != null && !boxProducts.isEmpty()) {
                    //如果之前已经有过收货记录 则继续收货
                    for (DeliverySkuBean deliverySkuBean : combinedSkuList) {
                        if (deliverySkuBean.getSkuId().equals(key) || deliverySkuBean.getUpcCode().equals(key)) {
                            editSku.postValue(deliverySkuBean);
                            return;
                        }
                    }

                    //搜索结果是否在收货单中
                    boolean searchResultInDeliveryOrder = true;
                    //子品中是否包含效期商品
                    boolean childContainsShelfLifeSku = false;

                    for (SearchResultBean.BoxProducts boxProduct : boxProducts) {
                        //子品是否在收货单中
                        boolean boxProductInDeliveryOrder = false;
                        for (DeliverySkuBean skuBean : skuListValue) {
                            if (skuBean.getSkuId().equals(boxProduct.getSkuId())) {
                                if (skuBean.isShelfLifeSup()) {
                                    //子品中包含效期商品 但仍需确定收货单是否包含组合商品
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
     * 已存在则更新 不存在则添加
     *
     * @param bean 商品信息
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
     * tab正在展示 单个商品列表
     */
    public boolean isTabShowSkuList() {
        return selectTabType.getValue() == null || selectTabType.getValue() == 0;
    }

    /**
     * 将sku移动到对应的列表中的顶部
     *
     * @param bean 编辑后的SKU
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

