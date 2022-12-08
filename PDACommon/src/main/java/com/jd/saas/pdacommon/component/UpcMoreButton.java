package com.jd.saas.pdacommon.component;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.screen.ScreenUtil;

/**
 * 多条码商品 upc条码的查看更多按钮
 * 点击时会打开查看upc条码的弹窗
 */
public class UpcMoreButton extends AppCompatTextView {
    private String[] upcCodes;

    public UpcMoreButton(@NonNull Context context) {
        super(context);
    }

    public UpcMoreButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UpcMoreButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUpcCodes(String[] upcCodes) {
        this.upcCodes = upcCodes;
        if (upcCodes == null || upcCodes.length <= 1) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundResource(R.drawable.shape_bg_copy);
        setEllipsize(TextUtils.TruncateAt.END);
        setMaxLines(1);
        setSingleLine();
        setText(R.string.common_upc_code_btn_text);
        setTextColor(0xFF4B5FE1);
        int paddingHorizontal = ScreenUtil.dp2px(getContext(), 12);
        int paddingVertical = ScreenUtil.dp2px(getContext(), 4);
        setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
        setOnClickListener(v -> UpcCodeDialog.open(v, upcCodes));
    }
}
