package com.jd.saas.pdadelivery.search;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.jd.saas.pdacommon.search.SearchGoodsResultAdapter;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryPopupSearchLayoutBinding;

public class DeliverySearchSkuPopWindow {
    private final DeliverySearchSkuViewModel mViewModel;
    private final SearchGoodsResultAdapter adapter = new SearchGoodsResultAdapter();
    private final PopupWindow popupWindow;

    private final SearchGoodsResultAdapter.OnItemClick onItemClick;

    public DeliverySearchSkuPopWindow(Context context, DeliverySearchSkuViewModel mViewModel, SearchGoodsResultAdapter.OnItemClick onItemClick) {
        DeliveryPopupSearchLayoutBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.delivery_popup_search_layout, null, false);
        popupWindow = new PopupWindow(dataBinding.getRoot(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.update();
        this.onItemClick = onItemClick;
        adapter.setOnItemClickListener(onItemClick);
        dataBinding.searchResultRv.setAdapter(adapter);
        dataBinding.executePendingBindings();
        this.mViewModel = mViewModel;
    }


    public void show(View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        int popHeight = view.getContext().getResources().getDisplayMetrics().heightPixels - rect.bottom;//pop高度
        popupWindow.setHeight(popHeight);
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, rect.left, rect.bottom);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public void searchContent(String s) {
        mViewModel.searchContent(s);
    }

    public void observe(LifecycleOwner lifecycleOwner) {
        mViewModel.mSearchListLiveData.observe(lifecycleOwner, adapter::submitList);
        mViewModel.showToastEvent.observe(lifecycleOwner, msg -> MyToast.show(msg, true));
        mViewModel.onSearchResultIsBean.observe(lifecycleOwner, onItemClick::onItemClick);
    }
}
