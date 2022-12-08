package com.jd.saas.pdacommon.toolbar;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.jd.saas.pdacommon.R;

/**
 * 自定义ToolBar
 *
 * @author majiheng
 */
public class NormalTitleBar extends FrameLayout {

    private AppCompatImageView mGoBack;
    private AppCompatImageView mShutdown;
    private AppCompatTextView mTitle;
    private AppCompatTextView mMenu;
    private View mDivider;

    private int mTitleBarHeight;

    public NormalTitleBar(@NonNull Context context) {
        super(context);
        getView(context);
    }

    public NormalTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getView(context);
    }

    public NormalTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getView(context);
    }

    public NormalTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mTitleBarHeight);
    }

    private void getView(final Context context) {
        View.inflate(context, R.layout.layout_toolbar, this);
        mGoBack = findViewById(R.id.iv_goback);
        mShutdown = findViewById(R.id.iv_shutdown);
        mTitle = findViewById(R.id.tv_title);
        mMenu = findViewById(R.id.iv_menu);
        mDivider = findViewById(R.id.v_divider);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        mTitleBarHeight = getResources().getDimensionPixelSize(typedValue.resourceId);
        setToolBarBackground(R.color.toolbar_bg);
    }

    public NormalTitleBar setBackButtonVisible(boolean visible) {
        mGoBack.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    public NormalTitleBar setOnBackClickListener(OnClickListener listener) {
        mGoBack.setOnClickListener(listener);
        return this;
    }

    public NormalTitleBar setOnShutdownClickListener(OnClickListener listener) {
        mShutdown.setOnClickListener(listener);
        return this;
    }

    public NormalTitleBar setTitle(int title) {
        if (title == 0) return this;
        mTitle.setText(title);
        return this;
    }

    public NormalTitleBar setTitle(String title) {
        if(TextUtils.isEmpty(title)) return this;
        mTitle.setText(title);
        return this;
    }

    public NormalTitleBar setToolBarVisible(boolean visible) {
        setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    public NormalTitleBar setMenuText(int text) {
        if(text >= 0) {
            mMenu.setText(text);
        }
        return this;
    }
    public NormalTitleBar setMenuColor(int color) {
        mMenu.setTextColor(getContext().getResources().getColor(color));
        return this;
    }
    public NormalTitleBar setMenuClickListener(OnClickListener listener) {
        if(null != listener) {
            mMenu.setOnClickListener(listener);
        }
        return this;
    }

    public NormalTitleBar setMenuIcon(int icon) {
        mMenu.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(icon), null, null, null);
        return this;
    }

    public NormalTitleBar hideMenuText() {
        mMenu.setVisibility(View.GONE);
        return this;
    }

    public NormalTitleBar showShutdownBtn() {
        mShutdown.setVisibility(View.VISIBLE);
        return this;
    }

    public NormalTitleBar setToolBarBackground(int background) {
        setBackgroundResource(background);
        return this;
    }

    public NormalTitleBar setBackButtonImg(int res) {
        mGoBack.setImageResource(res);
        return this;
    }

    public NormalTitleBar setDividerVisible(boolean visible) {
        mDivider.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    public NormalTitleBar setDividerColor(int colorRes) {
        mDivider.setBackgroundResource(colorRes);
        return this;
    }

    public NormalTitleBar setTitleColor(int color) {
        mTitle.setTextColor(getContext().getResources().getColor(color));
        return this;
    }

    public NormalTitleBar setMenuText(String s) {
        if (!TextUtils.isEmpty(s)){
            mMenu.setText(s);
        }
        return this;
    }
}