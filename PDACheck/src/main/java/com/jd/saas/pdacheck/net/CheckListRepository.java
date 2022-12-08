package com.jd.saas.pdacheck.net;

import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitHeaderResultBean;
import com.jd.saas.pdacheck.flow.step4.bean.CheckProfitListResultBean;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.newtask.bean.CheckNewTaskDetailBean;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.search.SearchResultBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盘点任务列表
 *
 * @author ext.mengmeng
 */
public class CheckListRepository extends BaseRepository {

    private final CheckListApi mApi = ApiMgr.getApi(CheckListApi.class);

    /**
     * 获取盘点列表
     */
    public void getTaskList(HashMap<String, Object> body, BaseObserver<List<CheckListBean>> observer) {
        request(mApi.getTaskList(body), observer);
    }

    /**
     * 撤回任务
     */
    public void rollback(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.rollback(body), observer);
    }

    /**
     * 提交盘点记录
     */
    public void commit(HashMap<String, Object> body, BaseObserver<Object> observer) {
        request(mApi.commit(body), observer);
    }

    /**
     * 获取预盘点单号
     */
    public void getCouNo(HashMap<String, Object> body, BaseObserver<String> observer) {
        request(mApi.getCouNo(body), observer);
    }

    /**
     * 获取商品详情
     */
    public void getGoodsDetail(HashMap<String, Object> body, BaseObserver<SearchResultBean> observer) {
        request(mApi.getGoodsDetail(body), observer);
    }

    /**
     * 预盘商品校验
     */
    public void getCheckResult(HashMap<String, Object> body, BaseObserver<Map<String, String>> observer) {
        request(mApi.getCheckResult(body), observer);
    }

    /**
     * 更新盘点任务节点
     */
    public void updateTaskNode(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.updateTaskNode(body), observer);
    }

    /**
     * 新建盘点任务
     */
    public void getNewTask(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.newTask(body), observer);
    }

    /**
     * 查询任务信息以及详情
     */
    public void getTaskDetail(HashMap<String, Object> body, BaseObserver<CheckNewTaskDetailBean> observer) {
        request(mApi.getTaskDetail(body), observer);
    }

    /**
     * 查询任务信息以及详情
     */
    public void getDeleteTask(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.getDeleteTask(body), observer);
    }

    /**
     * 查询任务信息以及详情
     */
    public void getUpdateTask(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.getUpdateTask(body), observer);
    }

    /**
     * 撤销损益
     */
    public void cancelProfit(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.cancelProfit(body), observer);
    }

    /**
     * 提交损益
     */
    public void submitProfit(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.submitProfit(body), observer);
    }

    /**
     * 获取损益明细单头
     */
    public void getProfitHeader(HashMap<String, Object> body, BaseObserver<CheckProfitHeaderResultBean> observer) {
        request(mApi.getProfitHeader(body), observer);
    }

    /**
     * 获取损益明细列表
     */
    public void getProfitList(HashMap<String, Object> body, BaseObserver<CheckProfitListResultBean> observer) {
        request(mApi.getProfitList(body), observer);
    }

    /**
     * 创建损益单
     * */
    public void createCouGal(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.createCouGal(body), observer);
    }

    /**
     * 查询当前盘点任务损益单状态
     * */
    public void getCouGalStatus(HashMap<String, Object> body, BaseObserver<Integer> observer) {
        request(mApi.getCouGalStatus(body), observer);
    }

    /**
     * 结束当前盘点任务
     * */
    public void closeStockTask(HashMap<String, Object> body, BaseObserver<Boolean> observer) {
        request(mApi.closeStockTask(body), observer);
    }
}
