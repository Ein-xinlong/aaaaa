package com.jd.saas.pdadelivery.net.result;

import java.util.List;

public class DiffListAndDiffReasonResult {
    private PagedResult<QueryRcvDiffListResult> result;
    private List<QueryDiffReasonResult> lessReasonResult;
    private List<QueryDiffReasonResult> overReasonResult;

    public DiffListAndDiffReasonResult(PagedResult<QueryRcvDiffListResult> result, List<QueryDiffReasonResult> lessReasonResult, List<QueryDiffReasonResult> overReasonResult) {
        this.result = result;
        this.lessReasonResult = lessReasonResult;
        this.overReasonResult = overReasonResult;
    }

    public PagedResult<QueryRcvDiffListResult> getResult() {
        return result;
    }

    public void setResult(PagedResult<QueryRcvDiffListResult> result) {
        this.result = result;
    }

    public List<QueryDiffReasonResult> getLessReasonResult() {
        return lessReasonResult;
    }

    public void setLessReasonResult(List<QueryDiffReasonResult> lessReasonResult) {
        this.lessReasonResult = lessReasonResult;
    }

    public List<QueryDiffReasonResult> getOverReasonResult() {
        return overReasonResult;
    }

    public void setOverReasonResult(List<QueryDiffReasonResult> overReasonResult) {
        this.overReasonResult = overReasonResult;
    }
}
