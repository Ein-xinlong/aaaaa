package com.jd.saas.pdacheck.flow.step3.enums;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * 页面用 显示排序筛选的选项
 */
public enum CheckReviewSortOption implements Serializable {
    TOP_50,
    TOP_100,
    LAST_50,
    LAST_100;

    public static int getLimit(@NonNull CheckReviewSortOption sortOption) {
        switch (sortOption) {
            case TOP_100:
            case LAST_100:
                return 100;
            case TOP_50:
            case LAST_50:
            default:
                return 50;
        }
    }

    public static int getFlag(@NonNull CheckReviewSortOption sortOption) {
        switch (sortOption) {
            case LAST_50:
            case LAST_100:
                return CheckReviewSortFlagType.ASC.getValue();
            case TOP_50:
            case TOP_100:
            default:
                return CheckReviewSortFlagType.DESC.getValue();
        }
    }
}
