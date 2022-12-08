package com.jd.saas.pdadelivery.util;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdadelivery.R;

public class SpanUtils {
    public static CharSequence getInputQtySpan(String input, String combinedInput) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        SpannableString attrAdditional = new SpannableString(input);
        final int color = MyApplication.getInstance().getResources().getColor(R.color.delivery_detail_red);
        attrAdditional.setSpan(
                new ForegroundColorSpan(color), 0, input.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append(attrAdditional);
        if (!combinedInput.equals("0")) {
            sb.append("+");
            sb.append(combinedInput);
        }
        return sb;
    }
}
