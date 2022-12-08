package com.jd.saas.pdacheck.flow.step3.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuListParam;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewQtyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortKeyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortOption;

public class CheckReviewSkuListParamBuilder {
    /**
     * 盘点任务ID
     */
    @NonNull
    private final String taskNo;
    @NonNull
    private final CheckReviewQtyType qtyType;
    @Nullable
    private CheckReviewSortKeyType sortKeyType;
    @Nullable
    private CheckReviewSortOption sortOption;


    public CheckReviewSkuListParamBuilder(@NonNull String taskNo, @NonNull CheckReviewQtyType qtyType) {
        this.taskNo = taskNo;
        this.qtyType = qtyType;
    }

    public void setSortOption(@Nullable CheckReviewSortOption sortOption) {
        this.sortOption = sortOption;
    }

    public void setSortKeyType(@Nullable CheckReviewSortKeyType sortKeyType) {
        this.sortKeyType = sortKeyType;
    }

    public Integer getLimit() {
        if (sortOption != null) {
            return CheckReviewSortOption.getLimit(sortOption);
        }
        return null;
    }

    public CheckReviewSkuListParam build(int page, int pageSize) {
        Integer sortKeyTypeValue = null;
        if (sortKeyType != null) {
            sortKeyTypeValue = sortKeyType.getValue();
        }
        Integer sortFlag = null;
        if (sortOption != null) {
            sortFlag = CheckReviewSortOption.getFlag(sortOption);
        }
        return new CheckReviewSkuListParam(taskNo, qtyType.getValue(), sortKeyTypeValue, sortFlag, page, pageSize);
    }
}
