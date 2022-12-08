package com.jd.saas.pdadelivery.net;

import androidx.collection.SparseArrayCompat;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.net.NetCodeConstant;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.search.SearchGoodsQueryBean;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.detail.bean.DeliveryPrintParam;
import com.jd.saas.pdadelivery.net.enums.DiffTypeEnum;
import com.jd.saas.pdadelivery.net.enums.ParentCodeEnum;
import com.jd.saas.pdadelivery.net.param.AsnListDataParam;
import com.jd.saas.pdadelivery.net.param.PageQueryParam;
import com.jd.saas.pdadelivery.net.param.Param;
import com.jd.saas.pdadelivery.net.param.QueryDiffReasonParam;
import com.jd.saas.pdadelivery.net.param.QueryExpiryDateGoodsStatusDataParam;
import com.jd.saas.pdadelivery.net.param.QueryLocationsDataParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvDiffListParam;
import com.jd.saas.pdadelivery.net.param.QueryRcvSkuStockDataParam;
import com.jd.saas.pdadelivery.net.param.ReceiveAsnFinishDataParam;
import com.jd.saas.pdadelivery.net.param.StockExchangeParam;
import com.jd.saas.pdadelivery.net.param.SubmitDataParam;
import com.jd.saas.pdadelivery.net.result.DiffListAndDiffReasonResult;
import com.jd.saas.pdadelivery.net.result.PagedResult;
import com.jd.saas.pdadelivery.net.result.QueryDiffReasonResult;
import com.jd.saas.pdadelivery.net.result.QueryLocationsResult;
import com.jd.saas.pdadelivery.net.result.QueryRcvDiffListResult;
import com.jd.saas.pdadelivery.net.result.StockDetailResult;
import com.jd.saas.pdadelivery.net.result.StockExchangeResult;
import com.jd.saas.pdadelivery.net.result.StockItemResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RemoteDeliveryRepository extends BaseRepository implements DeliveryRepository {
    private final DeliveryApi api;
    private static volatile RemoteDeliveryRepository instance;

    public static RemoteDeliveryRepository getInstance(DeliveryApi api) {
        if (instance == null) {
            synchronized (RemoteDeliveryRepository.class) {
                if (instance == null) {
                    instance = new RemoteDeliveryRepository(api);
                }
            }
        }
        return instance;
    }

    private RemoteDeliveryRepository(DeliveryApi api) {
        this.api = api;
    }

    public void asnList(Param<AsnListDataParam> param, BaseObserver<PagedResult<StockItemResult>> observer) {
        request(api.asnList(param), observer);
    }

    @Override
    public void queryRcvSkuStock(Param<QueryRcvSkuStockDataParam> param, BaseObserver<StockDetailResult> observer) {
        request(api.queryRcvSkuStock(param), observer);
    }

    @Override
    public void submitRcv(Param<SubmitDataParam> param, BaseObserver<String> observer) {
        request(api.submitRcv(param), observer);
    }

    @Override
    public void receiveAsnFinish(Param<ReceiveAsnFinishDataParam> param, BaseObserver<String> observer) {
        request(api.receiveAsnFinish(param), observer);
    }

    @Override
    public void receiveAsnFinishByDiff(Param<ReceiveAsnFinishDataParam> param, BaseObserver<String> observer) {
        request(api.receiveAsnFinishByDiff(param), observer);
    }

    @Override
    public void queryLocationsByPage(Param<QueryLocationsDataParam> param, BaseObserver<PagedResult<QueryLocationsResult>> observer) {
        request(api.queryLocationsByPage(param), observer);
    }

    @Override
    public void pagingQueryStockExchangeByLot(Param<StockExchangeParam> param, BaseObserver<PagedResult<StockExchangeResult>> observer) {
        request(api.pagingQueryStockExchangeByLot(param), observer);
    }


    @Override
    public void queryExpiryDateGoodsStatus(Param<QueryExpiryDateGoodsStatusDataParam> param, BaseObserver<Integer> observer) {
        request(api.queryExpiryDateGoodsStatus(param), observer);
    }

    @Override
    public void queryRcvDiffList(Param<QueryRcvDiffListParam> param, BaseObserver<DiffListAndDiffReasonResult> observer) {
        Observable<BaseResponse<PagedResult<QueryRcvDiffListResult>>> queryPagedRcvDiffList = api.queryRcvDiffList(param);

        Observable<BaseResponse<PagedResult<QueryRcvDiffListResult>>> queryRcvDiffList =
                queryPagedRcvDiffList.flatMap((Function<BaseResponse<PagedResult<QueryRcvDiffListResult>>, ObservableSource<BaseResponse<PagedResult<QueryRcvDiffListResult>>>>) firstPagedListResponse -> {
                    //请求未成功的直接返回错误信息
                    if (firstPagedListResponse.getCode() != NetCodeConstant.OK_200
                            || firstPagedListResponse.getData() == null) {
                        return Observable.just(firstPagedListResponse);
                    }
                    PagedResult<QueryRcvDiffListResult> firstPagedListResponseData = firstPagedListResponse.getData();
                    int cnt = firstPagedListResponseData.getTotal();
                    if (cnt < firstPagedListResponseData.getPageSize()) {
                        //不足一页的数据 直接返回首页数据
                        return Observable.just(firstPagedListResponse);
                    }
                    final int totalPage = firstPagedListResponseData.getTotalPage();
                    //生成全部分页的请求
                    List<Observable<BaseResponse<PagedResult<QueryRcvDiffListResult>>>> requests = new ArrayList<>();
                    //填入首页数据
                    requests.add(Observable.just(firstPagedListResponse));
                    //生成分页请求
                    for (int i = 2; i <= totalPage; i++) {
                        QueryRcvDiffListParam listParam = new QueryRcvDiffListParam(param.getData().getAsnNo(),
                                new PageQueryParam(i, 20));
                        requests.add(api.queryRcvDiffList(new Param<>(listParam)));
                    }

                    return Observable.merge(requests)
                            .collect(
                                    (Callable<SparseArrayCompat<BaseResponse<PagedResult<QueryRcvDiffListResult>>>>) SparseArrayCompat::new,
                                    (sparseArray, pagedResultBaseResponse2) -> {
                                        if (pagedResultBaseResponse2.getCode() == NetCodeConstant.OK_200
                                                && pagedResultBaseResponse2.getData() != null) {
                                            sparseArray.put(pagedResultBaseResponse2.getData().getPageNo(), pagedResultBaseResponse2);
                                        } else {
                                            sparseArray.put(0, pagedResultBaseResponse2);
                                        }
                                    }).map(sparseArrayCompat -> {
                                //第0页数据是错误信息，包含错误信息表示加载失败
                                if (sparseArrayCompat.containsKey(0)) {
                                    return sparseArrayCompat.get(0);
                                }
                                BaseResponse<PagedResult<QueryRcvDiffListResult>> baseResponse = new BaseResponse<>();
                                baseResponse.setCode(NetCodeConstant.OK_200);
                                PagedResult<QueryRcvDiffListResult> data = new PagedResult<>();
                                data.setItemList(new ArrayList<>());
                                baseResponse.setData(data);
                                for (int i = 1; i <= totalPage; i++) {
                                    if (sparseArrayCompat.containsKey(i)) {
                                        //包含的请求结果一定是成功的，非成功的结果无法获取到页数
                                        BaseResponse<PagedResult<QueryRcvDiffListResult>> pagedResultBaseResponse = sparseArrayCompat.get(i);
                                        if (pagedResultBaseResponse == null) {
                                            baseResponse.setCode(-1);
                                            baseResponse.setMsg("未能获取到全部数据");
                                            return baseResponse;
                                        } else {
                                            //拼接后是全量数据 pageSize page 不再有意义
                                            baseResponse.getData().getItemList().addAll(pagedResultBaseResponse.getData().getItemList());
                                        }
                                    } else {
                                        //不包含某页到请求结果说明信息不全
                                        //如果失败则不再进行其他请求的拼接
                                        baseResponse.setCode(-1);
                                        baseResponse.setMsg("未能获取到全部数据");
                                        return baseResponse;
                                    }

                                }
                                return baseResponse;
                            }).toObservable();
                });
        QueryDiffReasonParam lessReasonParam = new QueryDiffReasonParam();
        lessReasonParam.setParentCode(ParentCodeEnum.INBOUND_RECEIVE_LACK.name());
        Observable<BaseResponse<List<QueryDiffReasonResult>>> queryLessReason = api.queryDiffReason(new Param<>(lessReasonParam));
        QueryDiffReasonParam overReasonParam = new QueryDiffReasonParam();
        overReasonParam.setParentCode(ParentCodeEnum.INBOUND_RECEIVE_OVERCHARGE.name());
        Observable<BaseResponse<List<QueryDiffReasonResult>>> queryOverReason = api.queryDiffReason(new Param<>(overReasonParam));
        request(Observable.zip(queryRcvDiffList, queryLessReason, queryOverReason, (diffListResponse, lessReasonResponse, overReasonResponse) -> {
            BaseResponse<DiffListAndDiffReasonResult> response = new BaseResponse<>();
            //请求未成功的直接返回错误信息
            if (diffListResponse.getCode() != NetCodeConstant.OK_200) {
                response.setCode(diffListResponse.getCode());
                response.setMsg(diffListResponse.getMsg());
                return response;
            }
            if (lessReasonResponse.getCode() != NetCodeConstant.OK_200) {
                response.setCode(lessReasonResponse.getCode());
                response.setMsg(lessReasonResponse.getMsg());
                return response;
            }
            if (overReasonResponse.getCode() != NetCodeConstant.OK_200) {
                response.setCode(overReasonResponse.getCode());
                response.setMsg(overReasonResponse.getMsg());
                return response;
            }
            if (lessReasonResponse.getData() == null || lessReasonResponse.getData().isEmpty()) {
                throw new Exception(MyApplication.getInstance().getString(R.string.delivery_query_selectable_diff_unreceived_reason_error_tips));
            }
            QueryDiffReasonResult defaultLessReason = lessReasonResponse.getData().get(0);
            if (overReasonResponse.getData() == null || overReasonResponse.getData().isEmpty()) {
                throw new Exception(MyApplication.getInstance().getString(R.string.delivery_query_selectable_diff_over_received_reason_error_tips));
            }
            QueryDiffReasonResult defaultOverReason = overReasonResponse.getData().get(0);
            if (diffListResponse.getData() != null) {
                List<QueryRcvDiffListResult> itemList = diffListResponse.getData().getItemList();
                if (itemList != null) {
                    for (int i = 0; i < itemList.size(); i++) {
                        QueryRcvDiffListResult queryRcvDiffListResult = itemList.get(i);
                        if (queryRcvDiffListResult.getDiffType() == DiffTypeEnum.LESS.getValue()) {
                            queryRcvDiffListResult.setReasonCode(defaultLessReason.getDictCode());
                            queryRcvDiffListResult.setReasonDesc(defaultLessReason.getDictName());
                        } else {
                            queryRcvDiffListResult.setReasonCode(defaultOverReason.getDictCode());
                            queryRcvDiffListResult.setReasonDesc(defaultOverReason.getDictName());
                        }
                    }
                }
            }
            response.setCode(NetCodeConstant.OK_200);
            response.setData(new DiffListAndDiffReasonResult(diffListResponse.getData(),
                    lessReasonResponse.getData(), overReasonResponse.getData()));
            return response;
        }), observer);
    }

    public void getGoodsList(Param<SearchGoodsQueryBean> body, BaseObserver<SearchResultBean> observer) {
        request(api.getGoodsList(body), observer);
    }

    @Override
    public void queryPoPrintInfo(Param<DeliveryPrintParam> body, BaseObserver<DirectDeliveryOrderBean> observer) {
        request(api.queryPoPrintInfo(body), observer);
    }
}


