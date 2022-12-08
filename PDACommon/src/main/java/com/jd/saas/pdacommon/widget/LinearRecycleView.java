package com.jd.saas.pdacommon.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义recycler view，可以设置无数据ui
 *
 * @author majiheng
 */
public class LinearRecycleView extends RecyclerView {


    public LinearRecycleView(@NonNull Context context) {
        super(context);
    }

    public LinearRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOrientation(int orientation){
        setOrientation(orientation,0);
    }

    public void setOrientation(int orientation, int divRes) {
        WrapLinearLayoutManager linearLayoutManager = new WrapLinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(orientation);
        setLayoutManager(linearLayoutManager);
        if (divRes == 0){
            return;
        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), orientation);
        Drawable drawable = getResources().getDrawable(divRes);
        dividerItemDecoration.setDrawable(drawable);
        addItemDecoration(dividerItemDecoration);
    }

    private View mEmptyView;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    private void checkIfEmpty() {
        if (mEmptyView == null || getAdapter() == null) {
            return;
        }
        final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
        mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
        setVisibility(emptyViewVisible ? GONE : VISIBLE);
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        checkIfEmpty();
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

}
