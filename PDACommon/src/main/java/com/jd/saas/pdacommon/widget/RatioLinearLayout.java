package com.jd.saas.pdacommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.jd.saas.pdacommon.R;

/**
 * 一个可以定义长宽比的LinearLayout
 *
 * @author majiheng
 */
public class RatioLinearLayout extends LinearLayout {

    private float mRatio = 0f;

    public RatioLinearLayout(Context context) {
        super(context);
    }

    public RatioLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLinearLayout);
        mRatio = typedArray.getFloat(R.styleable.RatioLinearLayout_ratio_linear_layout,mRatio);
        typedArray.recycle();
    }

    public RatioLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(mRatio != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * mRatio),MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
