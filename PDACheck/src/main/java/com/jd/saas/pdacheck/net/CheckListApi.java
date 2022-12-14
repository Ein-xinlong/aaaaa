package com.jd.saas.pdacheck.net;

import com.jd.saas.pdacheck.category.bean.CheckCategoryRespItem;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouDeleteParam;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouListParam;
import com.jd.saas.pdacheck.flow.step1.bean.CheckCouResult;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedCreateCouNoParam;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuListReqParam;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuResp;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSubmitParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuListParam;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSubmitOrderParam;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitHeaderResultBean;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitListResultBean;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.newtask.bean.CheckNewTaskDetailBean;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.search.SearchResultBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CheckListApi {

    @POST("/app/api/Inventory/queryTaskList")
    Observable<BaseResponse<List<CheckListBean>>> getTaskList(@Body HashMap<String, Object> body);

    @POST("/app/api/bpm/repeal")
    Observable<BaseResponse<Boolean>> rollback(@Body HashMap<String, Object> body);

    @POST("/app/api/stockCou/createCou")
    Observable<BaseResponse<Object>> commit(@Body HashMap<String, Object> body);

    @POST("/app/api/stockCou/createCouNo")
    Observable<BaseResponse<String>> getCouNo(@Body HashMap<String, Object> body);

    @POST("/app/api/sku/queryByCondition")
    Observable<BaseResponse<SearchResultBean>> getGoodsDetail(@Body HashMap<String, Object> body);

    @POST("/app/api/Inventory/checkBatchSkuForLoc")
    Observable<BaseResponse<Map<String, String>>> getCheckResult(@Body HashMap<String, Object> body);

    @POST("/app/api/stockCou/updateTaskNode")
    Observable<BaseResponse<Boolean>> updateTaskNode(@Body HashMap<String, Object> body);

    /**
     * ??????????????????
     */
    @POST("/app/api/category/getCategoryList")
    Observable<BaseResponse<List<CheckCategoryRespItem>>> queryCategoryList(@Body CheckCommonParamWrapper<String> body);

    /**
     * ??????????????????
     */
    @POST("/app/api/stockCou/createTask")
    Observable<BaseResponse<Boolean>> newTask(@Body HashMap<String, Object> body);

    /**
     * ??????????????????????????????
     */
    @POST("/app/api/stockCou/getTaskDetailByNo")
    Observable<BaseResponse<CheckNewTaskDetailBean>> getTaskDetail(@Body HashMap<String, Object> body);

    /**
     * ????????????
     */
    @POST("/app/api/stockCou/deleteTask")
    Observable<BaseResponse<Boolean>> getDeleteTask(@Body HashMap<String, Object> body);

    /**
     * ????????????
     */
    @POST("/app/api/stockCou/updateTask")
    Observable<BaseResponse<Boolean>> getUpdateTask(@Body HashMap<String, Object> body);

    /**
     * ????????????
     */
    @POST("/app/api/stockCou/cancelGal")
    Observable<BaseResponse<Boolean>> cancelProfit(@Body HashMap<String, Object> body);

    /**
     * ????????????
     */
    @POST("/app/api/stockCou/submitGalExamine")
    Observable<BaseResponse<Boolean>> submitProfit(@Body HashMap<String, Object> body);

    /**
     * ????????????????????????
     */
    @POST("/app/api/stockCou/selectDifferentTypeCou")
    Observable<BaseResponse<CheckProfitHeaderResultBean>> getProfitHeader(@Body HashMap<String, Object> body);

    /**
     * ????????????????????????
     */
    @POST("/app/api/stockCou/queryDetailsByPage")
    Observable<BaseResponse<CheckProfitListResultBean>> getProfitList(@Body HashMap<String, Object> body);

    /**
     * ???????????????
     */
    @POST("/app/api/stockCou/createCouGal")
    Observable<BaseResponse<Boolean>> createCouGal(@Body HashMap<String, Object> body);

    /**
     * ???????????????????????????????????????
     */
    @POST("/app/api/stockCou/selectGalStatusForPda")
    Observable<BaseResponse<Integer>> getCouGalStatus(@Body HashMap<String, Object> body);

    /**
     * ????????????????????????
     */
    @POST("/app/api/stockCou/closeStockTask")
    Observable<BaseResponse<Boolean>> closeStockTask(@Body HashMap<String, Object> body);

    /**
     * ????????????-??????????????????????????????
     */
    @POST("/app/api/stockCou/selectModifyCouByTaskNo")
    Observable<BaseResponse<CheckPagedResp<CheckReviewSkuResult>>> getReviewSkuList(@Body CheckCommonParamWrapper<CheckReviewSkuListParam> param);

    /**
     * ????????????-????????????????????????????????????
     */
    @POST("/app/api/stockCou/selectModifySkuDetail")
    Observable<BaseResponse<List<CheckReviewPreOrderResult>>> getReviewSkuPreCheckOrderList(@Body CheckCommonParamWrapper<CheckReviewPreOrderListParam> param);

    /**
     * ????????????-?????????????????????????????????
     */
    @POST("/app/api/stockCou/updateCouDetailBySku")
    Observable<BaseResponse<Boolean>> submitReviewSkuPreOrderList(@Body CheckCommonParamWrapper<List<CheckReviewSubmitOrderParam>> param);


    /**
     * ????????????-????????????????????????????????????????????????
     */
    @POST("/app/api/stockCou/selectDifferentTypeCou")
    Observable<BaseResponse<CheckReviewQtyTypedCntResult>> getQtyTypedCnt(@Body CheckCommonParamWrapper<CheckReviewQtyTypedCntParam> param);

    /**
     * ?????????????????????
     */
    @POST("/app/api/stockCou/queryCouHeaderByPage")
    Observable<BaseResponse<CheckPagedResp<CheckCouResult>>> getQueryCouHeaderByPage(@Body CheckCommonParamWrapper<CheckCouListParam> param);

    /**
     * ???????????????
     */
    @POST("/app/api/stockCou/delCouHeader")
    Observable<BaseResponse<Boolean>> delCouHeader(@Body CheckCommonParamWrapper<CheckCouDeleteParam> param);

    /**
     * ????????????-????????????????????????
     */
    @POST("/app/api/stockCou/queryLossCouDetail")
    Observable<BaseResponse<List<CheckMissedSkuResp>>> getMissedSkuList(@Body CheckCommonParamWrapper<CheckMissedSkuListReqParam> param);


    /**
     * ????????????-?????????????????????????????????
     */
    @POST("/app/api/stockCou/createCouNo")
    Observable<BaseResponse<String>> createSubmittedCouNo(@Body CheckCommonParamWrapper<CheckMissedCreateCouNoParam> param);

    /**
     * ????????????-??????????????????(???????????????)
     */
    @POST("/app/api/stockCou/createCou")
    Observable<BaseResponse<Boolean>> submitMissedSkuList(@Body CheckCommonParamWrapper<CheckMissedSubmitParam> param);

}
