package com.jd.saas.pdacheck.flow.step1.paging;

import androidx.annotation.Nullable;

import com.jd.saas.pdacheck.flow.step1.bean.CheckCouListParam;

/**
 * 预盘单列表请求参数构造器
 *
 * @author gouhetong
 */
public class CheckCouListParamBuilder {
    @Nullable
    private final String taskNo;

    /**
     * 当前的builder是否有效
     *
     * @return 当taskNo为空时是无效的入参构造方法
     */
    public boolean isInit() {
        return taskNo != null;
    }

    public CheckCouListParamBuilder(@Nullable String taskNo) {
        this.taskNo = taskNo;
    }


    public CheckCouListParam build(int page, int pageSize) {
        return new CheckCouListParam(taskNo, page, pageSize);
    }
}
