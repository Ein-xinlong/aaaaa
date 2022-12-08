package com.jd.saas.pdacheck.flow.step3.dialog;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortKeyType;
import com.jd.saas.pdacheck.flow.step3.enums.CheckReviewSortOption;

public class CheckReviewSortTypeViewModel extends ViewModel {
    public MutableLiveData<Pair<CheckReviewSortKeyType, CheckReviewSortOption>> sortType = new MutableLiveData<>();


    public LiveData<Boolean> amountTop50 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_AMOUNT && pair.second == CheckReviewSortOption.TOP_50;
        }
        return false;
    });
    public LiveData<Boolean> amountTop100 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_AMOUNT && pair.second == CheckReviewSortOption.TOP_100;
        }
        return false;
    });
    public LiveData<Boolean> amountLast50 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_AMOUNT && pair.second == CheckReviewSortOption.LAST_50;
        }
        return false;
    });
    public LiveData<Boolean> amountLast100 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_AMOUNT && pair.second == CheckReviewSortOption.LAST_100;
        }
        return false;
    });
    public LiveData<Boolean> qtyTop50 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_QTY && pair.second == CheckReviewSortOption.TOP_50;
        }
        return false;
    });
    public LiveData<Boolean> qtyTop100 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_QTY && pair.second == CheckReviewSortOption.TOP_100;
        }
        return false;
    });
    public LiveData<Boolean> qtyLast50 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_QTY && pair.second == CheckReviewSortOption.LAST_50;
        }
        return false;
    });
    public LiveData<Boolean> qtyLast100 = Transformations.map(sortType, pair -> {
        if (pair != null) {
            return pair.first == CheckReviewSortKeyType.DIFF_QTY && pair.second == CheckReviewSortOption.LAST_100;
        }
        return false;
    });

    public void changeSelect(CheckReviewSortKeyType key, CheckReviewSortOption sortOption) {
        sortType.setValue(new Pair<>(key, sortOption));
    }
}
