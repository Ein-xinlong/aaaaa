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
     * 获取全部类目
     */
    @POST("/app/api/category/getCategoryList")
    Observable<BaseResponse<List<CheckCategoryRespItem>>> queryCategoryList(@Body CheckCommonParamWrapper<String> body);

    /**
     * 新建盘点任务
     */
    @POST("/app/api/stockCou/createTask")
    Observable<BaseResponse<Boolean>> newTask(@Body HashMap<String, Object> body);

    /**
     * 查询任务信息以及详情
     */
    @POST("/app/api/stockCou/getTaskDetailByNo")
    Observable<BaseResponse<CheckNewTaskDetailBean>> getTaskDetail(@Body HashMap<String, Object> body);

    /**
     * 作废单据
     */
    @POST("/app/api/stockCou/deleteTask")
    Observable<BaseResponse<Boolean>> getDeleteTask(@Body HashMap<String, Object> body);

    /**
     * 作废单据
     */
    @POST("/app/api/stockCou/updateTask")
    Observable<BaseResponse<Boolean>> getUpdateTask(@Body HashMap<String, Object> body);

    /**
     * 撤销损益
     */
    @POST("/app/api/stockCou/cancelGal")
    Observable<BaseResponse<Boolean>> cancelProfit(@Body HashMap<String, Object> body);

    /**
     * 提交损益
     */
    @POST("/app/api/stockCou/submitGalExamine")
    Observable<BaseResponse<Boolean>> submitProfit(@Body HashMap<String, Object> body);

    /**
     * 获取损益明细单头
     */
    @POST("/app/api/stockCou/selectDifferentTypeCou")
    Observable<BaseResponse<CheckProfitHeaderResultBean>> getProfitHeader(@Body HashMap<String, Object> body);

    /**
     * 获取损益明细列表
     */
    @POST("/app/api/stockCou/queryDetailsByPage")
    Observable<BaseResponse<CheckProfitListResultBean>> getProfitList(@Body HashMap<String, Object> body);

    /**
     * 创建损益单
     */
    @POST("/app/api/stockCou/createCouGal")
    Observable<BaseResponse<Boolean>> createCouGal(@Body HashMap<String, Object> body);

    /**
     * 查询当前盘点任务损益单状态
     */
    @POST("/app/api/stockCou/selectGalStatusForPda")
    Observable<BaseResponse<Integer>> getCouGalStatus(@Body HashMap<String, Object> body);

    /**
     * 结束当前盘点任务
     */
    @POST("/app/api/stockCou/closeStockTask")
    Observable<BaseResponse<Boolean>> closeStockTask(@Body HashMap<String, Object> body);

    /**
     * 复盘核对-查询复盘商品明细列表
     */
    @POST("/app/api/stockCou/selectModifyCouByTaskNo")
    Observable<BaseResponse<CheckPagedResp<CheckReviewSkuResult>>> getReviewSkuList(@Body CheckCommonParamWrapper<CheckReviewSkuListParam> param);

    /**
     * 复盘核对-查询复盘商品的预盘单列表
     */
    @POST("/app/api/stockCou/selectModifySkuDetail")
    Observable<BaseResponse<List<CheckReviewPreOrderResult>>> getReviewSkuPreCheckOrderList(@Body CheckCommonParamWrapper<CheckReviewPreOrderListParam> param);

    /**
     * 复盘核对-复盘商品预盘单修改数量
     */
    @POST("/app/api/stockCou/updateCouDetailBySku")
    Observable<BaseResponse<Boolean>> submitReviewSkuPreOrderList(@Body CheckCommonParamWrapper<List<CheckReviewSubmitOrderParam>> param);


    /**
     * 复盘核对-复盘商品各个按数量分类下的商品数
     */
    @POST("/app/api/stockCou/selectDifferentTypeCou")
    Observable<BaseResponse<CheckReviewQtyTypedCntResult>> getQtyTypedCnt(@Body CheckCommonParamWrapper<CheckReviewQtyTypedCntParam> param);

    /**
     * 获取预盘单列表
     */
    @POST("/app/api/stockCou/queryCouHeaderByPage")
    Observable<BaseResponse<CheckPagedResp<CheckCouResult>>> getQueryCouHeaderByPage(@Body CheckCommonParamWrapper<CheckCouListParam> param);

    /**
     * 删除预盘单
     */
    @POST("/app/api/stockCou/delCouHeader")
    Observable<BaseResponse<Boolean>> delCouHeader(@Body CheckCommonParamWrapper<CheckCouDeleteParam> param);

    /**
     * 初盘核对-获取漏盘商品列表
     */
    @POST("/app/api/stockCou/queryLossCouDetail")
    Observable<BaseResponse<List<CheckMissedSkuResp>>> getMissedSkuList(@Body CheckCommonParamWrapper<CheckMissedSkuListReqParam> param);


    /**
     * 初盘核对-提交初盘结果前创建单号
     */
    @POST("/app/api/stockCou/createCouNo")
    Observable<BaseResponse<String>> createSubmittedCouNo(@Body CheckCommonParamWrapper<CheckMissedCreateCouNoParam> param);

    /**
     * 初盘核对-提交初盘结果(创建预盘单)
     */
    @POST("/app/api/stockCou/createCou")
    Observable<BaseResponse<Boolean>> submitMissedSkuList(@Body CheckCommonParamWrapper<CheckMissedSubmitParam> param);

}
